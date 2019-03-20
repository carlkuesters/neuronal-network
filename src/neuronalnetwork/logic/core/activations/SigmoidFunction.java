package neuronalnetwork.logic.core.activations;

import neuronalnetwork.logic.core.ActivationFunction;

/**

 @author Carl
 */
public class SigmoidFunction extends ActivationFunction {

    @Override
    public double getValue(double input) {
        return (1 / (1 + Math.exp(-1 * input)));
    }

    @Override
    public double getDerivative(double input) {
        double value = getValue(input);
        return (value * (1 - value));
    }
}
