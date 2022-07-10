import lombok.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakePanel extends JPanel {

	private final Dimension gameDimension;
	private final int cellSize;
	private int[] x;
	private int[] y;
	private final Random random;
	private final Timer timer;
	@Getter @Setter
	private int direction;
	@Getter
	private boolean running;
	private int appleX, appleY;
	private int snakeSegments;
	private int applesEaten;
	private static final int delay = 125;


	public SnakePanel(Dimension dimension) {
		setPreferredSize(dimension);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);

		KeyAdapter wasdAdapter = new WASDAdapter(this);
		addKeyListener(wasdAdapter);

		this.gameDimension = dimension;
		this.cellSize = gameDimension.width / 24;
		this.random = new Random();
		this.timer = new Timer(delay, (ActionListener) wasdAdapter);

		startGame();
	}

	public void startGame() {
		initSnake();
		placeApple();
		running = true;
		timer.start();
	}

	private void initSnake() {
		int gridCells = (gameDimension.width * gameDimension.height) / cellSize;
		this.x = new int[gridCells];
		this.y = new int[gridCells];
		this.direction = KeyEvent.VK_D;
		this.snakeSegments = 6;
		this.applesEaten = 0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		if (running) {
			drawGrid(g);
			drawApple(g);
			drawSnake(g);
			drawScore(g);
		} else {
			gameOver(g);
		}
	}

	private void drawGrid(Graphics g) {
		int width = gameDimension.width;
		int height = gameDimension.height;
		g.setColor(Color.BLACK);
		for (int i = 0; i < width / cellSize; i++) {
			g.drawLine(0, i * cellSize, width, i * cellSize);
		}
		for (int i = 0; i < height / cellSize; i++) {
			g.drawLine(i * cellSize, 0, i * cellSize, height);
		}
	}

	private void drawApple(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(appleX, appleY, cellSize, cellSize);
	}

	private void drawSnake(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x[0], y[0], cellSize, cellSize);
		for (int i = 1; i < snakeSegments; i++) {
			g.setColor(new Color(45, 180, 0, 255));
			g.fillRect(x[i], y[i], cellSize, cellSize);
		}
	}

	private void drawScore(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, 24));
		FontMetrics metrics = getFontMetrics(g.getFont());
		int x = (gameDimension.width - metrics.stringWidth("Score: " + applesEaten)) / 2;
		g.drawString("Score: " + applesEaten, x, g.getFont().getSize());
	}

	private void gameOver(Graphics g) {
		drawScore(g);

		FontMetrics metrics2 = getFontMetrics(g.getFont());
		int x = (gameDimension.width - metrics2.stringWidth("Game Over")) / 2;
		int y = gameDimension.height / 2;
		g.drawString("Game Over", x, y);

		x = (gameDimension.width - metrics2.stringWidth("Press any key to restart")) / 2;
		y += g.getFont().getSize();
		g.drawString("Press any key to restart", x, y);
	}

	private void placeApple() {
		appleX = random.nextInt((int) (gameDimension.width / cellSize)) * cellSize;
		appleY = random.nextInt((int) (gameDimension.height / cellSize)) * cellSize;
	}

	public void move() {
		for (int i = snakeSegments; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
			case (KeyEvent.VK_W) -> y[0] -= cellSize;
			case (KeyEvent.VK_S) -> y[0] += cellSize;
			case (KeyEvent.VK_A) -> x[0] -= cellSize;
			case (KeyEvent.VK_D) -> x[0] += cellSize;
		}
	}

	public void checkApple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			snakeSegments++;
			applesEaten++;
			placeApple();
		}
	}

	public void checkCollisions() {
		// Head touches body?
		for (int i = snakeSegments; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
				break;
			}
		}
		// Head touches left, right, top or bottom border?
		if (x[0] < 0 || (x[0] + cellSize) > gameDimension.width ||
			y[0] < 0 || (y[0] + cellSize) > gameDimension.height) {
			running = false;
		}
		if (!running) {
			timer.stop();
		}
	}

	public void paintImmediately() {
		super.paintImmediately(0, 0, gameDimension.width, gameDimension.height);
	}
}
