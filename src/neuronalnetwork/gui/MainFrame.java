package neuronalnetwork.gui;

import neuronalnetwork.logic.core.Network;
import neuronalnetwork.logic.training.Trainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**

 @author Carl
 */
public class MainFrame extends JFrame {

    private Trainer trainer;
    private PanNetwork panNetwork;

    public MainFrame(Network network, Trainer trainer) {
        this.trainer = trainer;
        initFrame();
        initDisplayedNetwork(network, trainer);
    }

    private void initFrame() {
        setTitle("Neuronal Network");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        GroupLayout layout = new GroupLayout(getContentPane());
        JPanel panContainer = new JPanel();
        panContainer.setLayout(new GridLayout());
        panNetwork = new PanNetwork();
        panContainer.add(panNetwork);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panContainer, GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panContainer, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE));
        getContentPane().setLayout(layout);
        pack();

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_P:
                        trainer.setPaused(true);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_P:
                        trainer.setPaused(false);
                        break;
                }
            }
        });

        GuiUtil.centerFrame(this);
        setVisible(true);
    }

    private void initDisplayedNetwork(Network network, Trainer trainer) {
        panNetwork.initialize(network);
        panNetwork.setTrainer(trainer);
        new Thread(() -> {
            while(true) {
                SwingUtilities.invokeLater(panNetwork::repaint);
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }
}
