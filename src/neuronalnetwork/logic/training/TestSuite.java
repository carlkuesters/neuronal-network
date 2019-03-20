package neuronalnetwork.logic.training;

import java.util.ArrayList;

/**

 @author Carl
 */
public class TestSuite {

    private ArrayList<TestCase> testCases;
    private String[] outputLabels;

    public TestSuite(ArrayList<TestCase> testCases, String[] outputLabels) {
        this.testCases = testCases;
        this.outputLabels = outputLabels;
    }

    TestCase getRandomTestCase() {
        return testCases.get((int) (Math.random() * testCases.size()));
    }

    public ArrayList<TestCase> getTestCases() {
        return testCases;
    }

    public String[] getOutputLabels() {
        return outputLabels;
    }
}
