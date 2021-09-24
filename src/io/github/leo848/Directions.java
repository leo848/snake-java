package io.github.leo848;

public class Directions {
	final static Vector NONE = new Vector(0, 0);
	final static Vector LEFT = new Vector(-1, 0);
	final static Vector RIGHT = new Vector(1, 0);
	final static Vector UP = new Vector(0, -1);
	final static Vector DOWN = new Vector(0, 1);
	
	static public Vector nextDirection(Vector direction) {
		if (direction.equals(LEFT)) return RIGHT;
		else if (direction.equals(RIGHT)) return UP;
		else if (direction.equals(UP)) return DOWN;
		else if (direction.equals(DOWN)) return LEFT;
		else throw new IllegalArgumentException("not a valid direction");
	}
}
