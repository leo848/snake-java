package io.github.leo848;

import java.awt.*;
import java.util.*;

@SuppressWarnings("unused")
public class Snake {
	boolean gameOver = false;
	
	Vector direction = Directions.NONE;
	ArrayList<Vector> positions = new ArrayList<>();
	
	public Snake() {
	}
	
	public void setDirection(Vector direction) {
		this.direction = direction;
	}
	
	public void updatePosition() {
		if (direction.mag() > 0) {
			positions.add(0, positions.get(0).copy().add(direction));
			positions.remove(positions.size() - 1);
		}
	}
	
	private boolean checkForGameOver(Graphics2D g2D) {
		gameOver = false;
		if (positions.subList(1, positions.size()).contains(positions.get(0))) {
			gameOver = true;
		} else {
			Vector sp = positions.get(0);
			if (sp.x < 0 || sp.x > 19 || sp.y < 0 || sp.y > 19) {
				gameOver = true;
			}
		}
		return gameOver;
	}
}
