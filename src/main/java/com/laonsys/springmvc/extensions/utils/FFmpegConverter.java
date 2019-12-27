package com.laonsys.springmvc.extensions.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FFmpegConverter {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    private String tempDir = System.getProperty("java.io.tmpdir") + "/ffmpeg_temp";

    public MultipartFile convert(MultipartFile file) throws IOException, InterruptedException {
        logger.debug("ffmpeg convert temp directory: {}", tempDir);
        
        File original = new File(tempDir, file.getOriginalFilename()); 
        FileUtils.copyInputStreamToFile(file.getInputStream(), original);
        
        logger.debug("copy file to temporary directory successful. \"{}\"", file.getOriginalFilename());
        
        String filename = original.getName();
        String basename = FilenameUtils.getBaseName(filename);
        String extension = FilenameUtils.getExtension(filename);
        String format = ("mp4".equals(extension)) ? "flv" : "mp4";

        String command = "/usr/local/bin/converter.sh -f " + format + " \"" + original.getAbsolutePath() + "\"";
        executeShell(command);

        String fileName = basename + "." + format;
        
        File convertFile = new File(tempDir, fileName);
        String contentType = ("flv".equals(extension)) ? "video/mp4" : "video/x-flv";
        
        return new MockMultipartFile(fileName, fileName, contentType, FileUtils.readFileToByteArray(convertFile));
    }
    
    public MultipartFile thumbnail(String filename) throws IOException {
        logger.debug("get mov thumbnail image. \"{}\"", filename);
        
        String basename = FilenameUtils.getBaseName(filename);
        File thumbnail = new File(tempDir, basename + "5.png");
        
        logger.debug("mov thumbnail image info. \"{}\" [exists {}]", thumbnail.getName(), thumbnail.exists());
        return new MockMultipartFile("thumbnail.png", "thumbnail.png", "image/png", FileUtils.readFileToByteArray(thumbnail));
    }

    public void clean() {
        boolean result = FileUtils.deleteQuietly(new File(tempDir));
        logger.debug("Clean the temporary directory. [{}]", result);
    }
    
    private void executeShell(String command) throws IOException, InterruptedException {
        logger.debug("shell execute command \"{}\"", command);

        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);

        pb.redirectErrorStream(true);
        Process ps = null;
        BufferedReader in = null;
        try {
            ps = pb.start();

            in = new BufferedReader(new InputStreamReader(ps.getInputStream()));

            String inString;
            while ((inString = in.readLine()) != null) {
                logger.debug("FFmpeg : [{}]", inString);
            }

            ps.waitFor();

            logger.debug("Shell process exit : [{}]", ps.exitValue());
        }
        catch (IOException e) {
            logger.debug("{}", "Error occured while Shell command execute: [" + e.getMessage() + "]");
            throw new IOException(e.getMessage());
        }
        catch (InterruptedException e) {
            logger.debug("{}", "Error occured while Shell command execute: [" + e.getMessage() + "]");
            throw new InterruptedException(e.getMessage());
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(IOException ignore) {
                }
            }
            
            if(ps != null) {
                ps.destroy();
            }
        }
        
        logger.debug("mp4 file convert to flv file successful.");
    }
}