package neuronalnetwork.logic.core.networks;

import neuronalnetwork.logic.core.ActivationFunction;
import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.core.Neuron;

import java.util.LinkedList;

/**

 @author Carl
 */
public class SimpleNetwork extends Network {

    public SimpleNetwork(int inputCount, int hiddenCount, int outputCount, ActivationFunction activationFunction) {
        addNeurons(inputNeurons, activationFunction, inputCount);
        addNeurons(hiddenNeurons, activationFunction, hiddenCount);
        addNeurons(outputNeurons, activationFunction, outputCount);
        for (Neuron hiddenNeuron : hiddenNeurons) {
            for (Neuron inputNeuron : inputNeurons) {
                inputNeuron.addSynapse(hiddenNeuron);
            }
            for (Neuron outputNeuron : outputNeurons) {
                hiddenNeuron.addSynapse(outputNeuron);
            }
        }
    }

    private void addNeurons(LinkedList<Neuron> neurons, ActivationFunction activationFunction, int count) {
        for (int i = 0; i < count; i++) {
            neurons.add(new Neuron(activationFunction));
        }
    }
}
