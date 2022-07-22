package view;

import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {

    public SnakeFrame(int frameSize) {
        setTitle("Sneek");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new SnakePanel(new Dimension(frameSize, frameSize)));
        setResizable(false);
        setVisible(true);
        pack();
    }
}
