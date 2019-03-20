package neuronalnetwork.logic.core;

/**

 @author Carl
 */
public class Synapse {

    private Neuron source;
    private Neuron target;
    private double weight;

    Synapse(Neuron source, Neuron target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Neuron getSource() {
        return source;
    }

    public Neuron getTarget() {
        return target;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
