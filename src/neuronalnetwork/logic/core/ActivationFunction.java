package neuronalnetwork.logic.core;

/**

 @author Carl
 */
public abstract class ActivationFunction {

    public abstract double getValue(double input);

    public abstract double getDerivative(double input);
}
