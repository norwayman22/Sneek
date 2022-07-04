import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {

    public SnakeFrame(Dimension dimension) {
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new SnakePanel(dimension));
        setResizable(false);
        setVisible(true);
        pack();
    }
}
