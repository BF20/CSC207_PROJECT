package use_cases.data_visualization;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    /**
     * Resizes an image to a absolute width and height (the image may not be proportional).
     * @param inputImagePath Path of the original image.
     * @param outputImagePath Path to save the resized image.
     * @param scaledWidth Absolute width in pixels.
     * @param scaledHeight Absolute height in pixels.
     * @throws IOException
     */
    public static void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // read the original image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // create output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scale the image
        Image resultingImage = inputImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        // extract extension
        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

        // write to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    // Usage example:
    // try {
    //     ImageResizer.resize("path/to/original/image.png", "path/to/resized/image.png", 300, 150);
    // } catch (IOException e) {
    //     e.printStackTrace();
    // }
}
