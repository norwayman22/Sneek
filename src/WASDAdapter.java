import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WASDAdapter extends KeyAdapter implements ActionListener {
    SnakePanel snakePanel;
    public WASDAdapter(SnakePanel snakePanel) {
        this.snakePanel = snakePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (snakePanel.isRunning()) {
            int direction = snakePanel.getDirection();
            boolean validDirection;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> validDirection = direction != KeyEvent.VK_S;
                case KeyEvent.VK_A -> validDirection = direction != KeyEvent.VK_D;
                case KeyEvent.VK_S -> validDirection = direction != KeyEvent.VK_W;
                case KeyEvent.VK_D -> validDirection = direction != KeyEvent.VK_A;
                default -> validDirection = false;
            }
            if (validDirection) {
                snakePanel.setDirection(e.getKeyCode());
            }
        } else {
            snakePanel.startGame();
            snakePanel.paintImmediately();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (snakePanel.isRunning()){
            snakePanel.move();
            snakePanel.checkApple();
            snakePanel.checkCollisions();
        }
        snakePanel.paintImmediately();
    }
}
