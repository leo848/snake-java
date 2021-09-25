package io.github.leo848;

import java.awt.*;
import java.util.*;

@SuppressWarnings("unused")
public class Snake {
	boolean gameOver = false;
	
	Vector direction = Directions.NONE;
	ArrayList<Vector> positions = new ArrayList<>();
	private int animFrame;
	
	public Snake() {
		positions.add(new Vector(10, 10));
		positions.add(new Vector(10, 11));
	}
	
	public void draw(Graphics2D g2D, int frameCount, int count) {
		g2D.fillRect(0, 0, 500, 500);
		animFrame++;
		
		g2D.setPaint(NumTools.getGradientColor(0, 1, frameCount));
		g2D.fillRect((int) (positions.get(0).x * 25), (int) (positions.get(0).y * 25), 25, 25);
		
		for (Vector pos : positions) {
			
			g2D.setPaint(NumTools.getGradientColor(positions.indexOf(pos), positions.size(), frameCount));
			
			int initialX = (int) (pos.x * 25);
			int initialY = (int) (pos.y * 25);
			
			Vector tempDirection = positions.get(0).equals(pos) ?
			                       direction :
			                       positions.get(positions.size() - 1).equals(pos) ?
			                       new Vector(positions.get(positions.indexOf(pos) - 1).x,
			                                  positions.get(positions.indexOf(pos) - 1).y).sub(pos) :
			                       Directions.NONE;
			
			
			if (!direction.equals(Directions.NONE)) {
				initialX += tempDirection.x * NumTools.map(animFrame, 0, count, 0, 25);
				initialY += tempDirection.y * NumTools.map(animFrame, 0, count, 0, 25);
			}
			
			
			g2D.fillRoundRect(initialX, initialY, 25, 25, 20, 20);
			
			
			boolean top = positions.contains(pos.copy().add(0, -1));
			boolean bottom = positions.contains(pos.copy().add(0, 1));
			boolean left = positions.contains(pos.copy().add(-1, 0));
			boolean right = positions.contains(pos.copy().add(1, 0));
			
			
			if (top || left) {
				g2D.fillRect(initialX, initialY, 12, 12);
			}
			if (top || right) {
				g2D.fillRect(initialX + 13, initialY, 12, 12);
			}
			if (bottom || left) {
				g2D.fillRect(initialX, initialY + 13, 12, 12);
			}
			if (bottom || right) {
				g2D.fillRect(initialX + 13, initialY + 13, 12, 12);
			}
		}
	}
	
	public void setDirection(Vector direction) {
		this.direction = direction;
	}
	
	public boolean updateAppleCollision(ArrayList<Vector> apples) {
		if (apples.contains(positions.get(0))) {
			apples.remove(positions.get(0));
			positions.add(positions.get(positions.size() - 1));
			return true;
		}
		
		return false;
	}
	
	public void updatePosition() {
		animFrame = 0;
		if (direction.mag() > 0) {
			positions.add(0, positions.get(0).copy().add(direction));
			positions.remove(positions.size() - 1);
		}
	}
	
	boolean checkForGameOver(Graphics2D g2D) {
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
