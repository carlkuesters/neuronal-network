package neuronalnetwork.logic.training.data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 @author Carl
 */
public class IdxExporter {

    public static void main(String[] args) throws Exception {
        String testdataKey = "fashion";
        HashMap<Integer, List<GrayscaleImage>> imagesPerLabel = IdxReader.readGrayscaleImages(testdataKey, 1);

        int imageIndex = 0;
        for (Map.Entry<Integer, List<GrayscaleImage>> entry : imagesPerLabel.entrySet()) {
            int label = entry.getKey();
            int labelImagesCount = 0;
            for (GrayscaleImage image : entry.getValue()) {
                if (imageIndex % 100 == 0) {
                    System.out.println("Number of images exported: " + imageIndex);
                }

                BufferedImage exportedImage = new BufferedImage(image.getPixelColumns(), image.getPixelRows(), BufferedImage.TYPE_INT_ARGB);
                int[] imagePixels = new int[image.getPixelColumns() * image.getPixelRows()];
                for (int i = 0; i < image.getGrayValues().length; i++) {
                    int gray = image.getGrayValues()[i];
                    imagePixels[i] = 0xFF000000 | (gray << 16) | (gray << 8) | gray;
                }
                exportedImage.setRGB(0, 0, image.getPixelColumns(), image.getPixelRows(), imagePixels, 0, image.getPixelColumns());

                File outputFile = new File("./testdata/" + testdataKey + "/exported/" + label + "_" + labelImagesCount + ".png");
                ImageIO.write(exportedImage, "png", outputFile);

                labelImagesCount++;
                imageIndex++;
            }
        }
    }
}
