package io.github.leo848;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
	final Vector pos;
	final Random random = new Random();
	
	public Apple(Vector pos) {
		this.pos = pos;
	}
	
	public Apple(float x, float y) {
		this(new Vector(x, y));
	}
	
	public void move(ArrayList<Vector> snakePositions) {
		Vector randomDirection;
		do {
			randomDirection = Directions.dirs[random.nextInt(Directions.dirs.length)];
		} while (snakePositions.contains(pos.copy().add(randomDirection).add(20, 20).mod(20)));
		
		pos
				.add(randomDirection)
				.add(20, 20)
				.mod(20);
	}
	
	public void update(SnakeCanvas canvas) {
		if (random.nextInt(420) == 0) {
			move(canvas.snake.positions);
		}
	}
	
	public void draw(Graphics2D g2D) {
		draw(g2D, false);
	}
	
	public void draw(Graphics2D g2D, boolean showPosition) {
		g2D.fillRoundRect((int) pos.x * 25, (int) pos.y * 25, 25, 25, 1, 1);
		if (showPosition) g2D.drawString(pos.x + ", " + pos.y, pos.x * 25, pos.y * 25);
	}
	
	@Override
	public int hashCode() {
		return pos.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Apple apple)) return false;
		
		return pos.equals(apple.pos);
	}
}
