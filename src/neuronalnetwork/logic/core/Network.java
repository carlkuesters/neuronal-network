package neuronalnetwork.logic.core;

import java.util.LinkedList;

/**

 @author Carl
 */
public class Network {

    protected LinkedList<Neuron> inputNeurons = new LinkedList<>();
    protected LinkedList<Neuron> hiddenNeurons = new LinkedList<>();
    protected LinkedList<Neuron> outputNeurons = new LinkedList<>();

    public void run() {
        for (Neuron inputNeuron : inputNeurons) {
            inputNeuron.fire();
        }
    }

    public LinkedList<Neuron> getInputNeurons() {
        return inputNeurons;
    }

    public LinkedList<Neuron> getHiddenNeurons() {
        return hiddenNeurons;
    }

    public LinkedList<Neuron> getOutputNeurons() {
        return outputNeurons;
    }
}
