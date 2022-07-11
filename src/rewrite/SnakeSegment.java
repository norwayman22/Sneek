package rewrite;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class SnakeSegment {

	public static final Color bodyColor = new Color(45, 180, 0);
	@Getter @Setter
	private int x, y;
	@Getter
	private final Color color;

	public SnakeSegment(int x, int y, boolean head) {
		this.x = x;
		this.y = y;
		this.color = head ? Color.GREEN : bodyColor;
	}
	public SnakeSegment(int x, int y) {
		this(x, y, false);
	}
}
