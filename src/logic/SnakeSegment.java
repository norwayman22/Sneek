package logic;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class SnakeSegment {

	public static final Color bodyColor = new Color(45, 180, 0);
	@Getter private final Color color;
	@Getter @Setter private int x, y;

	public SnakeSegment(int x, int y, boolean head) {
		this.color = head ? Color.GREEN : bodyColor;
		this.x = x;
		this.y = y;
	}
	public SnakeSegment(int x, int y) {
		this(x, y, false);
	}
}
