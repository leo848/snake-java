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
		this.pos = new Vector(x, y);
	}

    public void move(ArrayList<Vector> snakePositions) {
		if (random.nextInt(128) == 0) {
			do {
				pos.add(Directions.dirs[random.nextInt(Directions.dirs.length)]).add(20,20);
				pos.x %= 20;
				pos.y %= 20;
			} while (snakePositions.contains(pos));
		}
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

	public void draw(Graphics2D g2D) {
		g2D.fillRoundRect((int) pos.x * 25, (int) pos.y * 25, 25, 25, 1, 1);
		g2D.drawString(pos.x + ", " + pos.y, pos.x*25, pos.y*25);
	}
}
