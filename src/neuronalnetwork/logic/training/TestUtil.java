package neuronalnetwork.logic.training;

import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.core.Neuron;
import neuronalnetwork.logic.core.Synapse;

/**

 @author Carl
 */
class TestUtil {

    static void setRandomWeights(Network network) {
        for (Neuron neuron : network.getHiddenNeurons()) {
            for (Synapse inputSynapse : neuron.getInputSynapses()) {
                inputSynapse.setWeight(generateRandomSynapseWeight());
            }
            for (Synapse outputSynapse : neuron.getOutputSynapses()) {
                outputSynapse.setWeight(generateRandomSynapseWeight());
            }
        }
    }

    private static double generateRandomSynapseWeight() {
        return (Math.random() - 0.5);
    }
}
