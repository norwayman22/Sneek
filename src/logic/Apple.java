package logic;

import lombok.Getter;

import java.awt.*;
import java.util.Random;

public class Apple {

	public static Color color = Color.RED;
	private static final Random random = new Random();
	@Getter private final int x, y;

	public Apple(Dimension gridDimension, int cellSize) {
		this.x = random.nextInt(gridDimension.width / cellSize) * cellSize;
		this.y = random.nextInt(gridDimension.height / cellSize) * cellSize;
	}
}
