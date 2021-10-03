package io.github.leo848;

import java.util.*;

public class Apple {
	final Vector pos;
	final Random random = new Random();
	
	public Apple(Vector pos) {
		this.pos = pos;
	}
	
	public void move() {
		if (random.nextInt(10) == 0) {
			pos.add(Directions.directions[random.nextInt(Directions.directions.length)]);
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
