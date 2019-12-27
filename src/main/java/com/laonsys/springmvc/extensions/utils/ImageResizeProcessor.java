package com.laonsys.springmvc.extensions.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;

/**
 * 이미지 컨덴츠 타입에 대하여 제한된 파일 크기 또는 이미지 가로(혹은 세로) 사이즈보다 클 경우, 이미지 파일 크기 및 사이즈를 줄이는
 * 처리를 하는 {@link ResourceProcessor}
 * <p>
 * <a href="http://today.java.net/pub/a/today/2007/04/03/perils
 * -of-image-getscaledinstance .html">Chris Campbell's incremental-scaling
 * algorithm</a>을 개선하여 구현한 The Buzz Media의 <a href=
 * "http://www.thebuzzmedia.com/software/imgscalr-java-image-scaling-library/"
 * >imgscalr</a> 라이브러리에서 scale-down resizing 부분만을 발취하여 구현하였다.
 * 
 * @author kaldosa
 * 
 */
@Slf4j
public class ImageResizeProcessor implements ResourceProcessor {

    /** 이미지의 제한 폭(가로 혹은 세로) */
    private @Setter
    int limitDimension;

    /** 이미지의 제한 파일 사이즈 (byte 단위) */
    private @Setter
    long limitSize;

    /** 이미지 resizing시 고퀄리티 모드 (default: false) */
    private @Setter
    boolean qualityMode;

    /** {@link ImageResizeProcessor} 의 temporary 디렉토리 */
    private @Setter
    String location = System.getProperty("java.io.tmpdir");

    @Override
    public Object processing(String fileName, String contentType, long fileSize, InputStream inputStream) {
        File result = null;

        if (fileName == null) {
            throw new IllegalArgumentException("fileName cannot be null");
        }
        if (fileSize < 0) {
            throw new IllegalArgumentException("fileSize must be >= 0");
        }

        try {
            BufferedImage srcImage = ImageIO.read(inputStream);
            int nImageWidth = srcImage.getWidth(null);
            int nImageHeight = srcImage.getHeight(null);
            int nImageLargestDim = Math.max(nImageWidth, nImageHeight);
            double scale = (double) limitDimension / (double) nImageLargestDim;

            if (scale < 1.0d) {
                nImageWidth = (int) (nImageWidth * scale);
                nImageHeight = (int) (nImageHeight * scale);
            }

            // 제한 파일 크기보다 크거나 제한 이미지 사이즈보다 클 경우만 resize 를 한다.
            if (fileSize > limitSize || scale < 1.0d) {
                srcImage = resize(srcImage, nImageWidth, nImageHeight);
            }

            String extension = FilenameUtils.getExtension(fileName);
            result = new File(location, fileName);

            ImageIO.write(srcImage, extension, result);
        }
        catch (IOException e) {
            log.error("Error processing image resize processing for: " + inputStream + " in " + fileName + " for type "
                    + contentType, e);
        }

        return result;
    }

    protected BufferedImage resize(BufferedImage src, int targetWidth, int targetHeight) {
        long t = System.currentTimeMillis();

        if (src == null) {
            throw new IllegalArgumentException("src cannot be null");
        }
        if (targetWidth < 0) {
            throw new IllegalArgumentException("targetWidth must be >= 0");
        }
        if (targetHeight < 0) {
            throw new IllegalArgumentException("targetHeight must be >= 0");
        }

        BufferedImage result = null;

        int currentWidth = src.getWidth();
        int currentHeight = src.getHeight();

        float ratio = ((float) currentHeight / (float) currentWidth);

        if (log.isDebugEnabled()) {
            log.debug("Original Image [size = {}]", currentWidth + " x " + currentHeight);
            log.debug("Resizing Image [size = {}, ratio(H/W) = {}]", targetWidth + " x " + targetHeight, ratio);
        }

        if (targetWidth > currentWidth || targetHeight > currentHeight) {
            if (log.isDebugEnabled()) {
                log.debug("Image scale-up, unsupported.");
            }

            result = src;
        }
        else {
            if (log.isDebugEnabled()) {
                log.debug("Image scale-down, incremental scaling will be used...");
            }

            result = scaleDown(src, targetWidth, targetHeight, qualityMode);
        }

        if (log.isDebugEnabled())
            log.debug("Resized Image in {} ms", System.currentTimeMillis() - t);

        return result;
    }

    /**
     * <a href="http://today.java.net/pub/a/today/2007/04/03/perils
     * -of-image-getscaledinstance .html">Chris Campbell's incremental-scaling
     * algorithm</a>을 개선하여 구현한 The Buzz Media의 <a href=
     * "http://www.thebuzzmedia.com/software/imgscalr-java-image-scaling-library/"
     * >imgscalr</a> 라이브러리에서 scale-down resizing 부분만을 발취하여 구현하였다.
     * 
     * @param src The image that will be scaled.
     * @param targetWidth The target width for the scaled image.
     * @param targetHeight The target height for the scaled image.
     * @param scalingMethod The scaling method specified by the user (or
     * calculated by imgscalr) to use for this incremental scaling operation.
     * @param interpolationHintValue The {@link RenderingHints} interpolation
     * value used to indicate the method that {@link Graphics2D} should use when
     * scaling the image.
     * 
     * @return an image scaled to the given dimensions using the given rendering
     * hint.
     */
    protected BufferedImage scaleDown(BufferedImage src, int targetWidth, int targetHeight, boolean qualityMode) {
        boolean hasReassignedSrc = false;
        int incrementCount = 0;
        int currentWidth = src.getWidth();
        int currentHeight = src.getHeight();

        /*
         * The original QUALITY mode, representing Chris Campbell's algorithm,
         * is to step down by 1/2s every time when scaling the image
         * incrementally. Users pointed out that using this method to scale
         * images with noticeable straight lines left them really jagged in
         * smaller thumbnail format.
         * 
         * After investigation it was discovered that scaling incrementally by
         * smaller increments was the ONLY way to make the thumbnail sized
         * images look less jagged and more accurate; almost matching the
         * accuracy of Mac's built in thumbnail generation which is the highest
         * quality resize I've come across (better than GIMP Lanczos3 and
         * Windows 7).
         * 
         * A divisor of 7 was chose as using 5 still left some jaggedness in the
         * image while a divisor of 8 or higher made the resulting thumbnail too
         * soft; like our OP_ANTIALIAS convolve op had been forcibly applied to
         * the result even if the user didn't want it that soft.
         * 
         * Using a divisor of 7 for the ULTRA_QUALITY seemed to be the sweet
         * spot.
         * 
         * NOTE: Below when the actual fraction is used to calculate the small
         * portion to subtract from the current dimension, this is a
         * progressively smaller and smaller chunk. When the code was changed to
         * do a linear reduction of the image of equal steps for each
         * incremental resize (e.g. say 50px each time) the result was
         * significantly worse than the progressive approach used below; even
         * when a very high number of incremental steps (13) was tested.
         */
        int fraction = (qualityMode ? 7 : 2);

        do {
            int prevCurrentWidth = currentWidth;
            int prevCurrentHeight = currentHeight;

            /*
             * If the current width is bigger than our target, cut it in half
             * and sample again.
             */
            if (currentWidth > targetWidth) {
                currentWidth -= (currentWidth / fraction);

                /*
                 * If we cut the width too far it means we are on our last
                 * iteration. Just set it to the target width and finish up.
                 */
                if (currentWidth < targetWidth)
                    currentWidth = targetWidth;
            }

            /*
             * If the current height is bigger than our target, cut it in half
             * and sample again.
             */

            if (currentHeight > targetHeight) {
                currentHeight -= (currentHeight / fraction);

                /*
                 * If we cut the height too far it means we are on our last
                 * iteration. Just set it to the target height and finish up.
                 */

                if (currentHeight < targetHeight)
                    currentHeight = targetHeight;
            }

            if (prevCurrentWidth == currentWidth && prevCurrentHeight == currentHeight)
                break;

            if (log.isDebugEnabled()) {
                log.debug("Scaling from [{}] to [{}]", prevCurrentWidth + " x " + prevCurrentHeight, currentWidth
                        + " x " + currentHeight);
            }

            BufferedImage incrementalImage = drawImage(src, currentWidth, currentHeight);

            /*
             * Before re-assigning our interim (partially scaled)
             * incrementalImage to be the new src image before we iterate around
             * again to process it down further, we want to flush() the previous
             * src image IF (and only IF) it was one of our own temporary
             * BufferedImages created during this incremental down-sampling
             * cycle. If it wasn't one of ours, then it was the original
             * caller-supplied BufferedImage in which case we don't want to
             * flush() it and just leave it alone.
             */
            if (hasReassignedSrc) {
                src.flush();
            }

            /*
             * Now treat our incremental partially scaled image as the src image
             * and cycle through our loop again to do another incremental
             * scaling of it (if necessary).
             */
            src = incrementalImage;

            /*
             * Keep track of us re-assigning the original caller-supplied source
             * image with one of our interim BufferedImages so we know when to
             * explicitly flush the interim "src" on the next cycle through.
             */
            hasReassignedSrc = true;

            incrementCount++;
        } while (currentWidth != targetWidth || currentHeight != targetHeight);

        if (log.isDebugEnabled()) {
            log.debug("Incrementally Scaled Image in {} steps.", incrementCount);
        }

        /*
         * Once the loop has exited, the src image argument is now our scaled
         * result image that we want to return.
         */
        return src;
    }

    /**
     * Used to implement a straight-forward image-scaling operation using Java
     * 2D.
     * <p/>
     * 
     * @param src The image that will be scaled.
     * @param width The target width for the scaled image.
     * @param height The target height for the scaled image.
     * @param interpolationHintValue The {@link RenderingHints} interpolation
     * value used to indicate the method that {@link Graphics2D} should use when
     * scaling the image.
     * 
     * @return the result of scaling the original <code>src</code> to the given
     * dimensions using the given interpolation method.
     */
    protected BufferedImage drawImage(BufferedImage src, int width, int height) {

        if (width < 0 || height < 0)
            throw new IllegalArgumentException("width [" + width + "] and height [" + height + "] must be >= 0");

        // Setup the rendering resources to match the source image's
        BufferedImage result = new BufferedImage(width, height,
                (src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB
                        : BufferedImage.TYPE_INT_ARGB));

        Graphics2D g2d = result.createGraphics();

        // Scale the image to the new buffer using the specified rendering hint.
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(src, 0, 0, width, height, null);

        // Just to be clean, explicitly dispose our temporary graphics object
        g2d.dispose();

        return result;
    }
}
