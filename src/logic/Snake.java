package logic;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Snake {

	public static int head;
	@Getter @Setter private int direction;
	@Getter private final List<SnakeSegment> segments;
	private SnakeSegment nextTail;
	private final int cellSize;

	public Snake(int length, int cellSize) {
		this.direction = KeyEvent.VK_D;
		this.segments = new ArrayList<>();
		this.cellSize = cellSize;

		initSnake(length);
	}

	private void initSnake(int length) {
		for (int i = 0; i < length; i++) {
			addSegment(i);
			move();
		}
	}

	public void move() {
		SnakeSegment tail = segments.get(segments.size() - 1);
		SnakeSegment head = segments.get(Snake.head);
		int previousX = head.getX(), previousY = head.getY();

		nextTail = new SnakeSegment(tail.getX(), tail.getY());
		switch (direction) {
			case (KeyEvent.VK_W) -> head.setY(head.getY() - cellSize);
			case (KeyEvent.VK_S) -> head.setY(head.getY() + cellSize);
			case (KeyEvent.VK_A) -> head.setX(head.getX() - cellSize);
			case (KeyEvent.VK_D) -> head.setX(head.getX() + cellSize);
		}

		for (int i = 1; i < segments.size(); i++) {
			SnakeSegment segment = segments.get(i);
			int currentX = segment.getX(), currentY = segment.getY();

			segment.setX(previousX);
			segment.setY(previousY);

			previousX = currentX;
			previousY = currentY;

		}
	}

	public void addSegment() {
		addSegment(-1);
	}

	public void addSegment(int index) {
		if (index == Snake.head) {
			segments.add(new SnakeSegment(0, 0, true));
		} else if (nextTail != null) {
			segments.add(nextTail);
		}
	}
}
