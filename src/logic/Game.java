package logic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Game {

	public static final int delay = 125;
	@Getter private Snake snake;
	@Getter private Apple apple;
	@Setter private Timer timer;
	@Getter private int score;
	private final Dimension dimension;
	private final int cellSize;
	public Game(Dimension dimension, int cellSize) {
		this.dimension = dimension;
		this.cellSize = cellSize;
	}

	public void start() {
		if (timer != null) {
			this.snake = new Snake(6, cellSize);
			placeApple();
			timer.start();
		}
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
}
