package view;

import logic.*;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class SnakePanel extends JPanel {
	@Getter private final Dimension dimension;
	@Getter private final int cellSize;
	@Getter private final Game game;

	public SnakePanel(Dimension dimension) {
		setPreferredSize(dimension);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);

		this.dimension = dimension;
		this.cellSize = this.dimension.width / 24;

		game = new Game(dimension, cellSize);
		WASDAdapter wasdAdapter = new WASDAdapter(this);
		addKeyListener(wasdAdapter);
		game.setTimer(new Timer(Game.delay, wasdAdapter));
		game.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		if (game.isRunning()) {
			drawGrid(g);
			drawApple(g);
			drawSnake(g);
			drawScore(g);
		} else {
			gameOver(g);
		}
	}

	private void drawGrid(Graphics g) {
		int width = dimension.width;
		int height = dimension.height;
		g.setColor(Color.BLACK);
		for (int i = 0; i < width / cellSize; i++) {
			g.drawLine(0, i * cellSize, width, i * cellSize);
		}
		for (int i = 0; i < height / cellSize; i++) {
			g.drawLine(i * cellSize, 0, i * cellSize, height);
		}
	}

	private void drawApple(Graphics g) {
		Apple apple = game.getApple();
		g.setColor(Apple.color);
		g.fillOval(apple.getX(), apple.getY(), cellSize, cellSize);
	}

	private void drawSnake(Graphics g) {
		for (SnakeSegment segment : game.getSnake().getSegments()) {
			g.setColor(segment.getColor());
			g.fillRect(segment.getX(), segment.getY(), cellSize, cellSize);
		}
	}

	private void drawScore(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, 24));
		FontMetrics metrics = getFontMetrics(g.getFont());
		int x = (dimension.width - metrics.stringWidth("Score: " + game.getScore())) / 2;
		g.drawString("Score: " + game.getScore(), x, g.getFont().getSize());
	}

	private void gameOver(Graphics g) {
		drawScore(g);

		FontMetrics metrics2 = getFontMetrics(g.getFont());
		int x = (dimension.width - metrics2.stringWidth("logic.Game Over")) / 2;
		int y = dimension.height / 2;
		g.drawString("Game Over", x, y);

		x = (dimension.width - metrics2.stringWidth("Press any key to restart")) / 2;
		y += g.getFont().getSize();
		g.drawString("Press any key to restart", x, y);
	}

	public void paintImmediately() {
		super.paintImmediately(0, 0, dimension.width, dimension.height);
	}
}
