package neuronalnetwork.logic.training.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**

 @author Carl
 */
class IdxReader {

    static HashMap<Integer, List<GrayscaleImage>> readGrayscaleImages(String testdataKey, double keepRate) {
        HashMap<Integer, List<GrayscaleImage>> images = new HashMap<>();
        try {
            InputStream inLabel = new FileInputStream("./testdata/" + testdataKey + "/train-labels.idx1-ubyte");
            InputStream inImage = new FileInputStream("./testdata/" + testdataKey + "/train-images.idx3-ubyte");

            int magicNumberLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
            int numberOfLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());

            int magicNumberImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfRows  = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfColumns = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());

            int numberOfPixels = numberOfRows * numberOfColumns;
            for (int i = 0; i < numberOfImages; i++) {
                if (Math.random() < keepRate) {
                    int label = inLabel.read();
                    int[] grayValues = new int[numberOfPixels];
                    for (int r = 0; r < numberOfPixels; r++) {
                        // Images are upside down, mirroring is good enough
                        int reversedPixelIndex = (numberOfPixels - 1) - r;
                        grayValues[reversedPixelIndex] = 255 - inImage.read();
                    }
                    images.computeIfAbsent(label, (l) -> new LinkedList<>()).add(new GrayscaleImage(numberOfRows, numberOfColumns, grayValues));
                } else {
                    inLabel.skip(1);
                    inImage.skip(numberOfPixels);
                }
                if ((i + 1) % 1000 == 0) {
                    System.out.println("Number of images processed: " + (i + 1));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return images;
    }
}
