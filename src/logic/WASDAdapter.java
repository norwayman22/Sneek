package logic;

import view.SnakePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WASDAdapter extends KeyAdapter implements ActionListener {

	private final SnakePanel snakePanel;
	private final Game game;

	public WASDAdapter(SnakePanel snakePanel) {
		this.snakePanel = snakePanel;
		this.game = snakePanel.getGame();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Snake snake = game.getSnake();
		if (game.isRunning()) {
			int direction = snake.getDirection();
			boolean validDirection;
			switch (e.getKeyCode()) {
				case KeyEvent.VK_W -> validDirection = direction != KeyEvent.VK_S;
				case KeyEvent.VK_A -> validDirection = direction != KeyEvent.VK_D;
				case KeyEvent.VK_S -> validDirection = direction != KeyEvent.VK_W;
				case KeyEvent.VK_D -> validDirection = direction != KeyEvent.VK_A;
				default -> validDirection = false;
			}
			if (validDirection) {
				snake.setDirection(e.getKeyCode());
			}
		} else {
			game.start();
			snakePanel.paintImmediately();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.isRunning()) {
			game.getSnake().move();
			game.checkApple();
			game.checkCollisions();
		}
		snakePanel.paintImmediately();
	}
}
