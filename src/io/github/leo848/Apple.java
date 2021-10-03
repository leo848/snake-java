package io.github.leo848;

import java.util.*;

public class Apple {
	final Vector pos;
	final Random random = new Random();
	
	public Apple(Vector pos) {
		this.pos = pos;
	}
	
	public void move(ArrayList<Vector> snakePositions) {
		if (random.nextInt(10) == 0) {
			do {
				pos.add(Directions.directions[random.nextInt(Directions.directions.length)]);
			} while ((pos.x > 20 || pos.x < 0 || pos.y > 20 || pos.y < 0) || snakePositions.contains(pos));
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
}
