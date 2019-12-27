package com.laonsys.springmvc.extensions.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * MP4 동영상을 FLV 로 변환하여, MP4 와 FLV 그리고, 동영상의 포스터 이미지를 반환하는
 * {@link ResourceProcessor}
 * <p>
 * 실제 변환 작업은 FFMpeg를 이용하여 mp4를 flv로 변환하고, 포스터 이미지 10장을 추출하는 쉘 스크립트 작성하여 쉘 스크립트를
 * 실행하여 변환 작업을 수행한다. 동영상의 포스터 이미지는 10초 단위로 10장의 이미지를 추출하여 임의로 5번째 이미지를 포스트 이미지로
 * 선정하여 사용한다.
 * @author kaldosa
 * 
 */
@Slf4j
public class VodConvertProcessor implements ResourceProcessor {

    private @Setter
    String from = "mp4";

    private @Setter
    String to = "flv";

    /** {@link VodConvertProcessor} 의 temporary 디렉토리 */
    private @Setter
    String location = System.getProperty("java.io.tmpdir") + "/ffmpeg";

    @Override
    public Object processing(String fileName, String contentType, long fileSize, InputStream inputStream) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName cannot be null");
        }
        if (fileSize < 0) {
            throw new IllegalArgumentException("fileSize must be >= 0");
        }

        File original = new File(location, fileName);
        try {
            FileUtils.copyInputStreamToFile(inputStream, original);

            if (log.isDebugEnabled()) {
                log.debug("copy file to temporary directory successful. \"{}\"", fileName);
            }

            String command = "/usr/local/bin/converter.sh -f " + to + " \"" + original.getAbsolutePath() + "\"";
            executeShell(command);
        }
        catch (Exception e) {
            log.error("Error occurred while mp4 file convert to flv file. [" + fileName + "]", e);
        }

        return getResult(original);
    }

    /**
     * 쉘 명령어를 실행
     * 
     * @param command 수행할 쉘 명령어
     * @throws IOException
     * @throws InterruptedException
     */
    private void executeShell(String command) throws IOException, InterruptedException {
        if (command == null) {
            throw new IllegalArgumentException("command cannot be null");
        }

        log.info("shell execute command \"{}\"", command);

        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);

        pb.redirectErrorStream(true);
        Process ps = null;
        BufferedReader in = null;
        try {
            ps = pb.start();

            in = new BufferedReader(new InputStreamReader(ps.getInputStream()));

            String inString;
            while ((inString = in.readLine()) != null) {
                if (log.isDebugEnabled()) {
                    log.debug("FFmpeg : [{}]", inString);
                }
            }

            ps.waitFor();

            log.info("Shell process exit : [{}]", ps.exitValue());
        }
        catch (IOException e) {
            log.error("IOException occurred while Shell command execute: [{}]", e);
            throw new IOException(e.getMessage());
        }
        catch (InterruptedException e) {
            log.error("InterruptedException occurred while Shell command execute: [{}]", e);
            throw new InterruptedException(e.getMessage());
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ignore) {
                }
            }

            if (ps != null) {
                ps.destroy();
            }
        }

        log.info("mp4 file convert to flv file successful.");
    }

    /**
     * mp4를 flv로 변환 한 후, mp4, flv, poster를 map에 담아 반환한다.
     * 
     * @param original mp4 원본 파일
     * @return mp4,flv,poster File object map
     */
    protected Object getResult(File original) {
        String baseName = FilenameUtils.getBaseName(original.getName());
        File flv = new File(location, baseName + "." + to);
        File poster = new File(location, baseName + "5.png");

        Map<String, File> result = null;
        if (flv.exists() && poster.exists()) {
            result = new HashMap<String, File>();
            result.put("mp4", original);
            result.put("flv", flv);
            result.put("poster", poster);
        }
        else {
            log.warn("not found convert processing for. {}", original);
        }
        return result;
    }
}
