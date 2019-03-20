package neuronalnetwork.gui;

/**

 @author Carl
 */
class NeuronNode{

    private int x;
    private int y;
    private int size;

    NeuronNode(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getSize() {
        return size;
    }
}
