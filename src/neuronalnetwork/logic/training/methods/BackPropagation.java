package neuronalnetwork.logic.training.methods;

import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.core.Neuron;
import neuronalnetwork.logic.core.Synapse;
import neuronalnetwork.logic.training.TestCase;
import neuronalnetwork.logic.training.TrainingMethod;

import java.util.HashMap;
import java.util.Map;

/**

 @author Carl
 */
public class BackPropagation extends TrainingMethod {

    private HashMap<Neuron, Double> outputNeuronDvs_ErrorToInput = new HashMap<>();
    private HashMap<Synapse, Double> weightChanges = new HashMap<>();
    private double learningRate;

    public BackPropagation(double learningRate) {
        this.learningRate = learningRate;
    }

    public void correction(Network network, TestCase executedTestCase) {
        outputNeuronDvs_ErrorToInput.clear();
        weightChanges.clear();
        int outputNeuronIndex = 0;
        for (Neuron outputNeuron : network.getOutputNeurons()) {
            double dv_ErrorToOutput = -1 * (executedTestCase.getOutput()[outputNeuronIndex] - outputNeuron.getOutput());
            double dv_OutputToInput = outputNeuron.getActivationFunction().getDerivative(outputNeuron.getInput());
            double dv_ErrorToInput = dv_ErrorToOutput * dv_OutputToInput;
            outputNeuronDvs_ErrorToInput.put(outputNeuron, dv_ErrorToInput);
            for (Synapse inputSynapse : outputNeuron.getInputSynapses()) {
                double dv_InputToWeight = inputSynapse.getSource().getOutput();
                double dv_ErrorToWeight = dv_ErrorToInput * dv_InputToWeight;
                weightChanges.put(inputSynapse, -1 * (learningRate * dv_ErrorToWeight));
            }
            outputNeuronIndex++;
        }
        for (Neuron hiddenNeuron : network.getHiddenNeurons()) {
            double dv_TotalErrorToOutput = 0;
            for (Synapse outputSynapse : hiddenNeuron.getOutputSynapses()) {
                double dv_ErrorToInput = outputNeuronDvs_ErrorToInput.get(outputSynapse.getTarget());
                double dv_InputToOutput = outputSynapse.getWeight();
                double dv_ErrorToOutput = dv_ErrorToInput * dv_InputToOutput;
                dv_TotalErrorToOutput += dv_ErrorToOutput;
            }
            double dv_OutputToInput = hiddenNeuron.getActivationFunction().getDerivative(hiddenNeuron.getInput());
            double dv_TotalErrorToInput = dv_TotalErrorToOutput * dv_OutputToInput;
            for (Synapse inputSynapse : hiddenNeuron.getInputSynapses()) {
                double dv_InputToWeight = inputSynapse.getSource().getOutput();
                double dv_TotalErrorToWeight = dv_TotalErrorToInput * dv_InputToWeight;
                weightChanges.put(inputSynapse, -1 * (learningRate * dv_TotalErrorToWeight));
            }
        }
        for (Map.Entry<Synapse, Double> entry : weightChanges.entrySet()) {
            Synapse synapse = entry.getKey();
            double weightChange = entry.getValue();
            synapse.setWeight(synapse.getWeight() + weightChange);
        }
    }
}
