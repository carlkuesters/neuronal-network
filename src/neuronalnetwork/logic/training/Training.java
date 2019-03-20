package neuronalnetwork.logic.training;

import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.core.Neuron;

/**

 @author Carl
 */
public class Training{

    private Network network;
    private TestSuite testSuite;
    private TrainingMethod trainingMethod;
    private double averageDistance = -1;
    private double maximumDistance = -1;

    public Training(TestSuite testSuite, Network network, TrainingMethod trainingMethod) {
        this.testSuite = testSuite;
        this.network = network;
        this.trainingMethod = trainingMethod;
    }

    void trainRandomTestCase() {
        TestCase testCase = testSuite.getRandomTestCase();
        testCase.prepareIn(network);
        network.run();
        trainingMethod.correction(network, testCase);
    }

    void updateStatistics(double statisticsKeepRate) {
        double totalDistance = 0;
        maximumDistance = -1;
        int checkedTestsCases = 0;
        for (TestCase testCase : testSuite.getTestCases()) {
            if (Math.random() > statisticsKeepRate) {
                continue;
            }
            testCase.prepareIn(network);
            network.run();
            double testDistance = 0;
            int i = 0;
            for (Neuron outputNeuron : network.getOutputNeurons()) {
                testDistance += Math.abs(outputNeuron.getOutput() - testCase.getOutput()[i]);
                i++;
            }
            totalDistance += testDistance;
            if (testDistance > maximumDistance) {
                maximumDistance = testDistance;
            }
            checkedTestsCases++;
        }
        averageDistance = (totalDistance / checkedTestsCases);
    }

    Network getNetwork() {
        return network;
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }

    public double getAverageDistance() {
        return averageDistance;
    }

    public double getMaximumDistance() {
        return maximumDistance;
    }
}
