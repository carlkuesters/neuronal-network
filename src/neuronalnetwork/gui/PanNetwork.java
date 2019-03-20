package neuronalnetwork.gui;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JPanel;
import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.core.Neuron;
import neuronalnetwork.logic.core.Synapse;
import neuronalnetwork.logic.training.Trainer;

/**

 @author Carl
 */
class PanNetwork extends JPanel {

    private HashMap<Neuron, NeuronNode> neuronNodes = new HashMap<>();
    private Image backgroundImage;
    private BasicStroke basicStroke = new BasicStroke(1);
    private Color synapseColorPositive = new Color(0, 144, 255);
    private Color synapseColorNegative = new Color(255, 48, 0);
    private Color trainerInfoBackgroundColor = new Color(0, 160, 255);
    private Network network;
    private Trainer trainer;
    private double maximumSynapseAbsoluteWeight;

    void initialize(Network network) {
        this.network = network;

        neuronNodes.clear();
        int padding = 50;
        double marginX;
        double marginY = ((getHeight() - (2 * padding)) / 2);
        int neuronSize = 50;
        double x = padding;
        double y = padding;
        marginX = ((getWidth() - (2 * padding)) / (((double) network.getInputNeurons().size()) - 1));
        for (Neuron neuron : network.getInputNeurons()) {
            neuronNodes.put(neuron, new NeuronNode((int) x, (int) y, neuronSize));
            x += marginX;
        }
        x = padding;
        y += marginY;
        marginX = ((getWidth() - (2 * padding)) / (((double) network.getHiddenNeurons().size()) - 1));
        for (Neuron neuron : network.getHiddenNeurons()) {
            neuronNodes.put(neuron, new NeuronNode((int) x, (int) y, neuronSize));
            x += marginX;
        }
        x = padding;
        y += marginY;
        marginX = ((getWidth() - (2 * padding)) / Math.max(((double) network.getOutputNeurons().size()) - 1, 1));
        for (Neuron neuron : network.getOutputNeurons()) {
            neuronNodes.put(neuron, new NeuronNode((int) x, (int) y, neuronSize));
            x += marginX;
        }

        backgroundImage = GuiUtil.getResourceImage("images/background.jpg", getWidth(), getHeight());
    }

    void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    protected void paintComponent(Graphics _graphics) {
        super.paintComponent(_graphics);
        Graphics2D graphics = (Graphics2D) _graphics;
        graphics.drawImage(backgroundImage, 0, 0, this);
        if (network != null) {
            maximumSynapseAbsoluteWeight = -1;
            checkSynapseWeights(network.getInputNeurons());
            checkSynapseWeights(network.getHiddenNeurons());
            checkSynapseWeights(network.getOutputNeurons());
            drawNeuronsSynapses(graphics, network.getInputNeurons());
            drawNeuronsSynapses(graphics, network.getHiddenNeurons());
            drawNeuronsSynapses(graphics, network.getOutputNeurons());
            drawNeuronsValues(graphics, network.getInputNeurons(), false);
            drawNeuronsValues(graphics, network.getHiddenNeurons(), false);
            String[] outputLabels = ((trainer != null) ? trainer.getTraining().getTestSuite().getOutputLabels() : null);
            drawNeuronsValues(graphics, network.getOutputNeurons(), true, outputLabels);
            drawInputImage(graphics);
        }
        if (trainer != null) {
            drawTrainerInfo(graphics);
        }
    }

    private void checkSynapseWeights(LinkedList<Neuron> neurons) {
        double absoluteWeight;
        for (Neuron neuron : neurons) {
            for (Synapse synapse : neuron.getOutputSynapses()) {
                absoluteWeight = Math.abs(synapse.getWeight());
                if (absoluteWeight > maximumSynapseAbsoluteWeight){
                    maximumSynapseAbsoluteWeight = absoluteWeight;
                }
            }
        }
    }

    private void drawNeuronsSynapses(Graphics2D graphics, LinkedList<Neuron> neurons) {
        for (Neuron neuron : neurons) {
            drawNeuronSynapses(graphics, neuron);
        }
    }

    private void drawNeuronSynapses(Graphics2D graphics, Neuron neuron) {
        NeuronNode node = neuronNodes.get(neuron);
        for (Synapse synapse : neuron.getOutputSynapses()) {
            NeuronNode nodeTarget = neuronNodes.get(synapse.getTarget());
            graphics.setColor((synapse.getWeight() < 0) ? synapseColorNegative : synapseColorPositive);
            graphics.setStroke(new BasicStroke((float) ((Math.abs(synapse.getWeight()) / maximumSynapseAbsoluteWeight) * 15)));
            graphics.drawLine(node.getX(), node.getY(), nodeTarget.getX(), nodeTarget.getY());
        }
    }

    private void drawNeuronsValues(Graphics2D graphics, LinkedList<Neuron> neurons, boolean drawOutputValue) {
        drawNeuronsValues(graphics, neurons, drawOutputValue, null);
    }

    private void drawNeuronsValues(Graphics2D graphics, LinkedList<Neuron> neurons, boolean drawOutputValue, String[] labels) {
        int i = 0;
        for (Neuron neuron : neurons) {
            String label = ((labels != null) ? labels[i] : null);
            drawNeuronValue(graphics, neuron, drawOutputValue, label);
            i++;
        }
    }

    private void drawNeuronValue(Graphics2D graphics, Neuron neuron, boolean drawOutputValue, String label) {
        NeuronNode node = neuronNodes.get(neuron);
        graphics.setStroke(basicStroke);
        graphics.setColor(getNeuronColor(neuron));
        graphics.fillOval(node.getX() - (node.getSize() / 2), node.getY() - (node.getSize() / 2), node.getSize(), node.getSize());
        graphics.setColor(Color.BLACK);
        graphics.drawOval(node.getX() - (node.getSize() / 2), node.getY() - (node.getSize() / 2), node.getSize(), node.getSize());

        if (drawOutputValue) {
            double outputRoundedPercentage = (Math.round(neuron.getOutput() * 10000.0) / 100.0);
            String outputText = outputRoundedPercentage + "%";
            int outputWidth = graphics.getFontMetrics().stringWidth(outputText);
            graphics.drawString(outputText, node.getX() - (outputWidth / 2), (node.getY() + 5));
        }

        if (label != null) {
            int labelWidth = graphics.getFontMetrics().stringWidth(label);
            graphics.setColor(Color.WHITE);
            graphics.drawString(label, node.getX() - (labelWidth / 2), (node.getY() + 42));
        }
    }

    private Color getNeuronColor(Neuron neuron) {
        double outputPortion = Math.max(0, Math.min(neuron.getOutput(), 1));
        Color color0 = Color.LIGHT_GRAY;
        Color color1 = Color.YELLOW;
        int red = getColorValue(color0.getRed(), color1.getRed(), outputPortion);
        int green = getColorValue(color0.getGreen(), color1.getGreen(), outputPortion);
        int blue = getColorValue(color0.getBlue(), color1.getBlue(), outputPortion);
        return new Color(red, green, blue);
    }

    private int getColorValue(int colorValue0, int colorValue1, double portion) {
        return (int) (colorValue0 + (portion * (colorValue1 - colorValue0)));
    }

    private void drawInputImage(Graphics2D graphics) {
        // TODO: Pass from test
        int pixelRows = 28;
        int pixelColumns = 28;

        int pixelSize = 6;
        int pixelX = 0;
        int pixelY = 0;
        for (Neuron neuron : network.getInputNeurons()) {
            int gray = (int) (neuron.getOutput() * 255);
            int x = ((getWidth() / 2) - ((pixelX - (pixelColumns / 2)) * pixelSize));
            int y = (195 - ((pixelY - (pixelRows / 2)) * pixelSize));
            graphics.setColor(new Color(gray, gray, gray));
            graphics.fillRect(x, y, pixelSize, pixelSize);
            pixelX++;
            if (pixelX >= pixelRows) {
                pixelX = 0;
                pixelY++;
            }
        }
    }

    private void drawTrainerInfo(Graphics2D graphics) {
        int containerWidth = 230;
        int containerHeight = 90;
        graphics.setColor(trainerInfoBackgroundColor);
        graphics.fillRect(0, 0, containerWidth, containerHeight);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(0, containerHeight, containerWidth, containerHeight);
        graphics.drawLine(containerWidth, 0, containerWidth, containerHeight);

        int lineHeight = 20;
        int x = 10;
        int y = 20;
        graphics.drawString("TestCases: " + trainer.getTraining().getTestSuite().getTestCases().size(), x, y);
        y += lineHeight;
        graphics.drawString("Runs: #" + trainer.getRuns(), x, y);
        y += lineHeight;
        if (trainer.getTraining().getAverageDistance() != -1) {
            graphics.drawString("Error Avg: " + trainer.getTraining().getAverageDistance(), x, y);
            y += lineHeight;
        }
        if (trainer.getTraining().getMaximumDistance() != -1) {
            graphics.drawString("Error Max: " + trainer.getTraining().getMaximumDistance(), x, y);
        }
        graphics.setColor(Color.WHITE);
        graphics.drawString("Hold P to pause", x, containerHeight + 20);
    }
}
