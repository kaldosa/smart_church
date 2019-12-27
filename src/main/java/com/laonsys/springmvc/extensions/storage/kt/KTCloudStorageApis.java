package com.laonsys.springmvc.extensions.storage.kt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.model.CloudItem;

/**
 * KT 클라우드 스토리지 서버에 대한 RESTful APIs
 * 
 * @author kaldosa
 * 
 */
@Slf4j
@Named
public class KTCloudStorageApis {

    private @Value("#{envvars['storage.token.url']}")
    String authUrl;

    private @Value("#{envvars['storage.user']}")
    String userId;

    private @Value("#{envvars['storage.key']}")
    String apiKey;

    private String baseUrl;

    private String token;

    private Date expired;

    private int retry;

    public static int LIMIT_RETRY = 2;

    @Inject
    private RestTemplate restTemplate;

    protected boolean isExpired() {
        Date currentDate = Calendar.getInstance().getTime();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API] check expiry time of access token.");
            log.debug("[KTCloud API] Expiry Date : {}", sf.format(expired));
            log.debug("[KTCloud API] Current Date : {}", sf.format(currentDate));
            log.debug("[KTCloud API] isBefore : {} ", expired.before(currentDate));
        }

        return expired.before(currentDate);
    }

    @PostConstruct
    protected void requestToken() throws RestClientException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("X-Storage-User", userId);
        headers.add("X-Storage-Pass", apiKey);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(authUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API requestToken] authentication successful.");
                }

                HttpHeaders responseHeaders = response.getHeaders();

                List<String> storageUrl = responseHeaders.get("X-Storage-Url");
                baseUrl = storageUrl.get(0);

                List<String> authToken = responseHeaders.get("X-Auth-Token");
                token = authToken.get(0);

                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date acquistionTime = new Date(responseHeaders.getDate());

                Calendar cal = Calendar.getInstance();
                cal.setTime(acquistionTime);
                cal.add(Calendar.DATE, 1);

                expired = cal.getTime();

                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API requestToken] X-Storage-Url : [{}]", baseUrl);
                    log.debug("[KTCloud API requestToken] X-Auth-Token : [{}]", token);
                    log.debug("[KTCloud API requestToken] Date : [{}]", sf.format(acquistionTime));
                    log.debug("[KTCloud API requestToken] Expiry Date: [{}]", sf.format(expired));
                }
            }
            else {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API requestToken] failed to authentication. [{} : {}]", userId, apiKey);
                }
                throw new RestClientException("[KTCloud API requestToken] failed to authentication.");
            }
        }
        catch (HttpClientErrorException httpError) {
            if (HttpStatus.UNAUTHORIZED == httpError.getStatusCode()) {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API requestToken] 401 Unauthorized [{} : {}]", userId, apiKey);
                }
            }
            throw new RestClientException(httpError.getMessage());
        }

        log.info("[KTCloud API requestToken] method has executed successfully. [{}]", retry);
    }

    /**
     * API를 위한 Request Header를 생성한다. KT 클라우드 스토리지 서버에 접근을 하기 위해서는 access token 이
     * 필요하다. 모든 API는 X-Auth-Token 이라는 access token 헤드가 있어야 한다. 그외 필요한 해당 API 에서
     * 필요로 하는 커스텀 헤드를 입력받아 추가하여 Request Header 를 생성한다.
     * 
     * @param headerMap custom Request Header
     * @return Http Request Header
     */
    protected HttpHeaders getHeaders(Map<String, String> headerMap) throws RestClientException {

        if (isExpired()) {

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = Calendar.getInstance().getTime();
            log.info("refresh access token. current date {} [expiry date {}]", sf.format(currentDate),
                    sf.format(expired));
            log.info("current token [{}]", token);
            requestToken();
        }

        HttpHeaders headers = new HttpHeaders();

        headers.add("X-Auth-Token", token);

        if (headerMap != null) {
            Set<Entry<String, String>> entrySet = headerMap.entrySet();
            for (Entry<String, String> entry : entrySet) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }

        return headers;
    }

    /**
     * 파일박스(container)에 저장되어 있는 오브젝트의 개수를 반환한다.
     * 
     * @param container 파일박스명
     * @param path 디렉토리 오브젝트명
     * @return 오브젝트 수 (오류시 -1 반환)
     */
    public int getCount(String container, String path) throws RestClientException {
        int count = -1;

        String apiUrl = baseUrl + container + "?path=" + path;

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API getCount] URL : {}", apiUrl);
        }

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders(null));

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.HEAD, entity, String.class);

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.info("[KTCloud API getCount] {} path no content.", path);
            count = 0;
        }

        HttpHeaders responseHeaders = response.getHeaders();
        if (responseHeaders.containsKey("X-Container-Object-Count")) {
            List<String> values = responseHeaders.get("X-Container-Object-Count");
            count = Integer.parseInt(values.get(0));
        }

        return count;
    }

    /**
     * 파일박스에 오브젝트의 유무를 확인한다.
     * 
     * @param container 파일박스명
     * @param objectname 오브젝트명
     * @return 있을 경우 true, 없을시 false
     */
    public boolean exists(String container, String objectname) throws RestClientException {
        boolean isExists = false;

        String apiUrl = baseUrl + container + "/" + objectname;

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API exists] URL : {}", apiUrl);
        }

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders(null));

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.HEAD, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("[KTCloud API exists] exist object {}.", objectname);
                isExists = true;
            }
            else {
                log.info("[KTCloud API exists] error code {}.", response.getStatusCode());
            }
        }
        catch (HttpClientErrorException httpError) {
            if (HttpStatus.UNAUTHORIZED == httpError.getStatusCode()) {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API exists] 401 Unauthorized [{} : {}]", userId, apiKey);
                }

                isExists = (Boolean) invokeMethod("exists", container, objectname);
            }
            else if (HttpStatus.NOT_FOUND == httpError.getStatusCode()) {
                log.info("[KTCloud API exists] {} not found.", objectname);
            }
            else {
                throw new RestClientException(httpError.getMessage());
            }
        }

        log.info("[KTCloud API exists] method has executed successfully. [{}]", retry);

        retry = 0;

        return isExists;
    }

    /**
     * 파일박스에 있는 오브젝트 목록을 가져온다.
     * 
     * @param container 파일박스명
     * @param path 디렉토리 오브젝트명
     * @param marker 페이징을 위한 오브젝트명
     * @return 오브젝트 목록
     */
    public List<CloudItem> getList(String container, String path, String marker) throws RestClientException {
        String apiUrl = baseUrl + container + "?path=" + path + "&marker=" + marker + "&format=json";

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API getList] URL : {}", apiUrl);
        }

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders(null));

        ResponseEntity<CloudItem[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, CloudItem[].class);

        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NO_CONTENT) {
            List<CloudItem> list = Arrays.asList(response.getBody());

            return list;
        }

        log.info("[KTCloud API getList] error code {}", response.getStatusCode());

        return null;
    }

    /**
     * 특정 오브젝트를 가져온다.
     * 
     * @param container 파일박스명
     * @param objectname 오브젝트명
     * @return 오브젝트의 raw data
     * @throws RestClientException
     */
    public byte[] getObject(String container, String objectname) throws RestClientException {
        byte[] data = null;

        String apiUrl = baseUrl + container + "/" + objectname;

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API getObject] URL : {}", apiUrl);
        }

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders(null));

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                data = response.getBody();
            }
        }
        catch (HttpClientErrorException httpError) {
            if (HttpStatus.UNAUTHORIZED == httpError.getStatusCode()) {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API getObject] 401 Unauthorized [{} : {}]", userId, apiKey);
                }

                data = (byte[]) invokeMethod("getObject", container, objectname);
            }
            else if (HttpStatus.NOT_FOUND == httpError.getStatusCode()) {
                log.info("[KTCloud API getObject] {} not found.", objectname);
                data = null;
            }
            else {
                throw new RestClientException(httpError.getMessage());
            }
        }

        log.info("[KTCloud API getObject] method has executed successfully. [{}]", retry);

        retry = 0;
        return data;
    }

    /**
     * 파일박스에 오브젝트를 저장 (동일한 오브젝트가 있을시 덮어쓰기)을 한다.
     * 
     * @param container 파일박스명
     * @param objectname 오브젝트명
     * @param file 저장할 업로드 파일
     * @return 성공 유무 (true or false)
     * @throws RestClientException
     */
    public void putObject(String container, String objectname, MultipartFile file) throws RestClientException {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        }
        catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("[KTCloud API putObject] I/O error.");
            }
            throw new ResourceAccessException("[KTCloud API putObject]  I/O error: " + e.getMessage(), e);
        }

        putObject(container, objectname, file.getOriginalFilename(), file.getSize(), file.getContentType(), inputStream);
    }

    public void putObject(String container, String objectname, File file, String contentType)
            throws RestClientException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("[KTCloud API putObject] file not found.");
            }
            throw new ResourceAccessException("[KTCloud API putObject] file not found: " + e.getMessage(), e);
        }

        putObject(container, objectname, file.getName(), file.length(), contentType, inputStream);
    }

    public void putObject(String container, String objectname, String fileName, long fileSize, String contentType,
            InputStream inputStream) throws RestClientException {
        String apiUrl = baseUrl + container + "/" + objectname;

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API putObject] URL : {}", apiUrl);
        }

        Map<String, String> header = new HashMap<String, String>();

        header.put("Content-Type", contentType);
        header.put("Content-Length", Long.toString(fileSize));
        header.put("X-Object-Meta-delete", "false");

        byte[] body = null;

        try {
            body = IOUtils.toByteArray(inputStream);
        }
        catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("[KTCloud API putObject] I/O error.");
            }
            throw new ResourceAccessException("[KTCloud API putObject]  I/O error: " + e.getMessage(), e);
        }

        try {
            HttpEntity<byte[]> entity = new HttpEntity<byte[]>(body, getHeaders(header));

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                log.info("[KTCloud API putObject] \"{}\" file saved successfully.", container + "/" + objectname);
            }
            else {
                log.info("[KTCloud API putObject] failed to save file. statuscode [" + response.getStatusCode()
                        + "]");
            }
        }
        catch (HttpClientErrorException httpError) {
            if (HttpStatus.UNAUTHORIZED == httpError.getStatusCode()) {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API putObject] 401 Unauthorized [{} : {}]", userId, apiKey);
                }

                invokeMethod("putObject", container, objectname, fileName, fileSize, contentType, inputStream);
            }
            else {
                throw new RestClientException(httpError.getMessage());
            }
        }

        log.info("[KTCloud API putObject] method has executed successfully. [{}]", retry);

        retry = 0;
    }

    /**
     * 
     * @param container
     * @param objectname
     * @param delete
     * @return
     * @throws RestClientException
     */
    public void markingDelete(String container, String objectname, boolean delete) throws RestClientException {

        String apiUrl = baseUrl + container + "/" + objectname;

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API markingDelete] URL : {}", apiUrl);
        }

        Map<String, String> header = new HashMap<String, String>();

        String marking = (delete) ? "true" : "false";

        header.put("X-Object-Meta-delete", marking);

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders(header));

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.ACCEPTED) {
                log.info("[KTCloud API markingDelete] success marking delete object. [{}]", objectname);
            }
        }
        catch (HttpClientErrorException httpError) {
            if (httpError.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw new RestClientException(httpError.getMessage());
            }

            log.info("[KTCloud API markingDelete] failed to marking delete object {} [{}].", objectname);
        }
        catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("[KTCloud API markingDelete] RestClientException {}", e.getMessage());
            }
            throw new RestClientException(e.getMessage());
        }
    }

    /**
     * 오브젝트를 삭제한다.
     * 
     * @param container 파일박스명
     * @param objectname 오브젝트명
     * @return 성공 유무(true or false)
     */
    public void deleteObject(String container, String objectname) throws RestClientException {

        String apiUrl = baseUrl + container + "/" + objectname;

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API deleteObject] URL : {}", apiUrl);
        }

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders(null));
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.DELETE, entity, String.class);

            if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
                log.info("[KTCloud API deleteObject] failed to delete {} file.", objectname);
            }
            else {
                log.info("[KTCloud API deleteObject] {} file deleted successfully.", objectname);
            }
        }
        catch (HttpClientErrorException httpError) {
            if (HttpStatus.UNAUTHORIZED == httpError.getStatusCode()) {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API deleteObject] 401 Unauthorized [{} : {}]", userId, apiKey);
                }

                invokeMethod("deleteObject", container, objectname);
            }
            else if (httpError.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("[KTCloud API deleteObject] {} not found.", objectname);
            }
            else {
                throw new RestClientException(httpError.getMessage());
            }
        }

        log.info("[KTCloud API deleteObject] method has executed successfully. [{}]", retry);

        retry = 0;
    }

    public boolean makeDirectoryObject(String container, String directoryObject) {
        boolean isSuccess = false;

        String apiUrl = baseUrl + container + "/" + directoryObject;

        if (log.isDebugEnabled()) {
            log.debug("[KTCloud API makeDirectoryObject] URL : {}", apiUrl);
        }

        Map<String, String> header = new HashMap<String, String>();

        header.put("Content-Type", "application/directory");
        header.put("Content-Length", "0");

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders(header));

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                log.info("[KTCloud API makeDirectoryObject] directory created successfully. [{}]", directoryObject);
                isSuccess = true;
            }
            else {
                log.info("[KTCloud API makeDirectoryObject] failed to create {} directory.", directoryObject);
            }

        }
        catch (HttpClientErrorException httpError) {
            if (HttpStatus.UNAUTHORIZED == httpError.getStatusCode()) {
                if (log.isDebugEnabled()) {
                    log.debug("[KTCloud API makeDirectoryObject] 401 Unauthorized [{} : {}]", userId, apiKey);
                }

                isSuccess = (Boolean) invokeMethod("makeDirectoryObject", container, directoryObject);
            }
            else {
                throw new RestClientException(httpError.getMessage());
            }
        }

        log.info("[KTCloud API makeDirectoryObject] method has executed successfully. [{}]", retry);

        retry = 0;
        return isSuccess;
    }

    protected Object invokeMethod(String name, Object... objects) {

        log.info("[KTCloud API invokeMethod] retry api {} [{}]", name, retry);

        if (retry > LIMIT_RETRY) {
            if (log.isDebugEnabled()) {
                log.debug("[KTCloud API invokeMethod] Retransmission retry limit exceeded. {}[{}]", retry,
                        LIMIT_RETRY);
            }

            retry = 0;

            throw new RestClientException("[KTCloud API invokeMethod] Retransmission retry limit exceeded.");
        }

        log.info("[KTCloud API invokeMethod] retry requestToken [{}]", name, retry++);
        requestToken();

        try {
            Method[] methods = this.getClass().getMethods();

            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    return method.invoke(this, objects);
                }
            }
        }
        catch (Exception e) {
            throw new RestClientException(e.getMessage());
        }

        return null;
    }
}
