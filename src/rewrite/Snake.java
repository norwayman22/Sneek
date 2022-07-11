package rewrite;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	@Setter
	private int direction;
	@Getter
	private final List<SnakeSegment> segments;
	private SnakeSegment nextTail;

	public Snake(int segments) {
		this.direction = KeyEvent.VK_D;
		this.segments = new ArrayList<>();

		initBody(segments);
	}

	private void initBody(int parts) { // put in main?
		segments.add(new SnakeSegment(0, 0, true));
		for (int i = 0; i < parts; i++) {
			move();
			addSegment();
		}
	}

	private void move() {
		SnakeSegment nextSegment, segment = segments.get(0);

		nextTail = new SnakeSegment(segment.getX(), segment.getY());

		for (int i = 0; i < segments.size() - 1; i++) {
			segment = segments.get(i);
			nextSegment = segments.get(i + 1);

			segment.setX(nextSegment.getX());
			segment.setY(nextSegment.getY());
		}

		segment = segments.get(segments.size() - 1);
		switch (direction) {
			case (KeyEvent.VK_W) -> segment.setY(segment.getY() - 1);
			case (KeyEvent.VK_S) -> segment.setY(segment.getY() + 1);
			case (KeyEvent.VK_A) -> segment.setX(segment.getX() - 1);
			case (KeyEvent.VK_D) -> segment.setX(segment.getX() + 1);
		}
	}

	public void addSegment() {
		segments.add(nextTail);
	}
}
