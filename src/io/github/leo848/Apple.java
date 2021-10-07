package io.github.leo848;

import java.util.*;

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

		System.out.println("pos = " + pos);
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
