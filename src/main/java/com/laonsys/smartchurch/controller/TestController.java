package com.laonsys.smartchurch.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.PhotoFormBean;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Controller
public class TestController {

    @Autowired
    ResourceProcessingEngine resourceProcessingEngine;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTest() {
        return "/main/test";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String postTest(@Validated
    @ModelAttribute
    PhotoFormBean formBean) {
        MultipartFile file = formBean.getFile();
        try {
            resourceProcessingEngine.processing(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    file.getInputStream());
        }
        catch (IOException e) {
            // ignore
        }
//        try {
//            resizeImage(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getInputStream());
//        }
//        catch (IOException e) {
//        }
        return "/main/test";
    }

//    private void resizeImage(String fileName, String contentType, long fileSize, InputStream inputStream) {
//
//        String extension = FilenameUtils.getExtension(fileName);
//
//        try {
//            BufferedImage srcImage = ImageIO.read(inputStream);
//            int nImageWidth = srcImage.getWidth(null);
//            int nImageHeight = srcImage.getHeight(null);
//            int nImageLargestDim = Math.max(nImageWidth, nImageHeight);
//            double scale = (double) 1024 / (double) nImageLargestDim;
//
//            if (scale < 1.0d) {
//                nImageWidth = (int) (nImageWidth * scale);
//                nImageHeight = (int) (nImageHeight * scale);
//            }
//            if (fileSize > 512000 || scale < 1.0d) {
//                BufferedImage destImage = Scalr.resize(srcImage, Method.QUALITY, Mode.FIT_EXACT, nImageWidth,
//                        nImageHeight);
//                ImageIO.write(destImage, extension, new File(System.getProperty("java.io.tmpdir"), fileName));
//            }
//            else {
//                File output = new File(System.getProperty("java.io.tmpdir"), fileName);
//                IOUtils.copy(inputStream, new FileOutputStream(output));
//            }
//        }
//        catch (IOException e) {
//
//        }
//    }
    
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 1);
        System.out.println(sdf.format(cal.getTime()));
        
        int tmp = 8 - cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("tmp : " + tmp);
        
        cal.roll(Calendar.DAY_OF_YEAR, tmp);
        System.out.println(sdf.format(cal.getTime()));
        
        Calendar startCal = (Calendar) cal.clone();
        Calendar endCal = (Calendar) cal.clone();
        
        int week = cal.get(Calendar.WEEK_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        
        int start = startCal.get(Calendar.DAY_OF_WEEK) - 1;
        startCal.add(Calendar.DAY_OF_MONTH, start * -1);
        
        int end = 7 - endCal.get(Calendar.DAY_OF_WEEK);
        endCal.add(Calendar.DAY_OF_MONTH, end);
        
        System.out.println(year + " 년 " + month + " 월 " + week + " 주차" + " [" + sdf.format(startCal.getTime()) + " ~ " + sdf.format(endCal.getTime()) + "]");
    }
}
