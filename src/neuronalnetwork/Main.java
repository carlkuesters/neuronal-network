package neuronalnetwork;

import neuronalnetwork.gui.MainFrame;
import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.core.activations.SigmoidFunction;
import neuronalnetwork.logic.core.networks.SimpleNetwork;
import neuronalnetwork.logic.training.*;
import neuronalnetwork.logic.training.data.MnistTestSuite;
import neuronalnetwork.logic.training.methods.BackPropagation;

/**

 @author Carl
 */
public class Main {

    public static void main(String[] args) {
        // Neuronal Network
        Network network = new SimpleNetwork(28 * 28, 10, 10, new SigmoidFunction());

        // Test data + Training method
        TestSuite testSuite = MnistTestSuite.fashion(0.002);
        TrainingMethod trainingMethod = new BackPropagation(0.25);
        Trainer trainer = new Trainer(new Training(testSuite, network, trainingMethod), 200);

        // Visualisation
        new Thread(() -> new MainFrame(network, trainer)).start();

        // Start
        trainer.trainEndless(0.5);
    }
}
