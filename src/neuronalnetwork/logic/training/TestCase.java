package neuronalnetwork.logic.training;

import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.core.Neuron;

/**

 @author Carl
 */
public class TestCase {

    private double[] input;
    private double[] output;

    public TestCase(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }

    void prepareIn(Network network) {
        int i = 0;
        for (Neuron inputNeuron : network.getInputNeurons()) {
            inputNeuron.setOutput(input[i]);
            i++;
        }
    }

    public double[] getOutput() {
        return output;
    }
}
