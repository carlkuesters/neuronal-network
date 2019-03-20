package neuronalnetwork.logic.core;

import java.util.LinkedList;

/**

 @author Carl
 */
public class Neuron {

    private ActivationFunction activationFunction;
    private LinkedList<Synapse> inputSynapses = new LinkedList<>();
    private LinkedList<Synapse> outputSynapses = new LinkedList<>();
    private double input;
    private double output;

    public Neuron(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public void addSynapse(Neuron target) {
        Synapse synapse = new Synapse(this, target, 0);
        target.inputSynapses.add(synapse);
        outputSynapses.add(synapse);
    }

    private void update() {
        input = 0;
        for (Synapse synapse : inputSynapses) {
            input += (synapse.getWeight() * synapse.getSource().getOutput());
        }
        output = activationFunction.getValue(input);
        fire();
    }

    void fire() {
        for (Synapse synapse : outputSynapses) {
            synapse.getTarget().update();
        }
    }

    public double getInput() {
        return input;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public LinkedList<Synapse> getInputSynapses() {
        return inputSynapses;
    }

    public LinkedList<Synapse> getOutputSynapses() {
        return outputSynapses;
    }
}
