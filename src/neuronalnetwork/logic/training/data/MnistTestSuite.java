package neuronalnetwork.logic.training.data;

import neuronalnetwork.logic.training.TestCase;
import neuronalnetwork.logic.training.TestSuite;

import java.util.*;

/**

 @author Carl
 */
public class MnistTestSuite {

    public static TestSuite letters(double keepRate) {
        return create("letters", new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        }, keepRate);
    }

    public static TestSuite fashion(double keepRate) {
        return create("fashion", new String[]{
            "T-shirt/Top", "Trouser", "Pullover", "Dress", "Coat", "Sandal", "Shirt", "Sneaker", "Bag", "Ankle boot"
        }, keepRate);
    }

    private static TestSuite create(String testdataKey, String[] outputLabels, double keepRate) {
        HashMap<Integer, List<GrayscaleImage>> imagesPerLabel = IdxReader.readGrayscaleImages(testdataKey, keepRate);
        ArrayList<TestCase> testCases = new ArrayList<>();
        for (Map.Entry<Integer, List<GrayscaleImage>> entry : imagesPerLabel.entrySet()) {
            int label = entry.getKey();
            for (GrayscaleImage image : entry.getValue()) {
                double[] input = new double[image.getGrayValues().length];
                for (int r = 0; r < input.length; r++) {
                    input[r] = (1 - (image.getGrayValues()[r] / 255.0));
                }
                double[] output = new double[imagesPerLabel.keySet().size()];
                for (int r = 0; r < output.length; r++) {
                    output[r] = ((r == label) ? 1 : 0);
                }
                testCases.add(new TestCase(input, output));
            }
        }
        return new TestSuite(testCases, outputLabels);
    }
}
