package io.github.leo848;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
	final Vector pos;
	Vector animPos = new Vector();

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

	public void update() {
		Vector diff = pos.copy().sub(animPos);



		System.out.println("diff = " + diff);
		animPos = pos;
	}

	public void draw(Graphics2D g2D) {
		draw(g2D, false);
	}

	public void draw(Graphics2D g2D, boolean showPosition) {
		g2D.fillRoundRect((int) animPos.x * 25, (int) animPos.y * 25, 25, 25, 1, 1);
		if (showPosition) g2D.drawString(pos.x + ", " + pos.y, animPos.x*25, animPos.y*25);
	}
}
