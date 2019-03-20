package neuronalnetwork.logic.training;

import neuronalnetwork.logic.core.Network;

/**

 @author Carl
 */
public abstract class TrainingMethod {

    public abstract void correction(Network network, TestCase executedTestCase);
}
