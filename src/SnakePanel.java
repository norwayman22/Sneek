import lombok.Getter;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SnakePanel extends JPanel {

	private static final int delay = 125;
	private final Dimension dimension;
	private final int cellSize;
	private final WASDAdapter wasdAdapter;
	@Getter
	private Snake snake;
	private Apple apple;
	private final Timer timer;
	private int score;

	public SnakePanel(Dimension dimension) {
		setPreferredSize(dimension);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);

		this.dimension = dimension;
		this.cellSize = this.dimension.width / 24;

		wasdAdapter = new WASDAdapter(this);
		addKeyListener(wasdAdapter);
		this.timer = new Timer(delay, wasdAdapter);

		startGame();
	}

	public void startGame() {
		this.snake = new Snake(6, cellSize);
		wasdAdapter.setSnake(snake);
		placeApple();
		timer.start();
	}

	private void placeApple() {
		apple = new Apple(dimension, cellSize);
	}

	public void checkApple() {
		SnakeSegment snakeHead = snake.getSegments().get(Snake.head);
		if ((snakeHead.getX() == apple.getX()) && (snakeHead.getY() == apple.getY())) {
			snake.addSegment();
			score++;
			placeApple();
		}
	}

	public void checkCollisions() {
		List<SnakeSegment> segments = snake.getSegments();

		SnakeSegment head = segments.get(Snake.head);
		int headX = head.getX(), headY = head.getY();

		// Head touches body?
		for (int i = 1; i <= segments.size() - 1; i++) {
			SnakeSegment segment = segments.get(i);
			if (headX == segment.getX() && headY == segment.getY()) {
				timer.stop();
				return;
			}
		}
		// Head touches left, right, top or bottom border?
		if (headX < 0 || (headX + cellSize) > dimension.width ||
			headY < 0 || (headY + cellSize) > dimension.height) {
			timer.stop();
		}
	}

	public boolean isRunning() {
		return timer.isRunning();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		if (timer.isRunning()) {
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
		g.setColor(Apple.color);
		g.fillOval(apple.getX(), apple.getY(), cellSize, cellSize);
	}

	private void drawSnake(Graphics g) {
		for (SnakeSegment segment : snake.getSegments()) {
			g.setColor(segment.getColor());
			g.fillRect(segment.getX(), segment.getY(), cellSize, cellSize);
		}
	}

	private void drawScore(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, 24));
		FontMetrics metrics = getFontMetrics(g.getFont());
		int x = (dimension.width - metrics.stringWidth("Score: " + score)) / 2;
		g.drawString("Score: " + score, x, g.getFont().getSize());
	}

	private void gameOver(Graphics g) {
		drawScore(g);

		FontMetrics metrics2 = getFontMetrics(g.getFont());
		int x = (dimension.width - metrics2.stringWidth("Game Over")) / 2;
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
