package io.github.leo848;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeCanvas extends JPanel implements KeyListener {
	
	Random random = new Random();
	GameLoop gameLoop;
	
	int frameCount = 0;
	
	boolean gameOver = false;
	
	ArrayList<Character> keyStack = new ArrayList<>();
	
	ArrayList<Vector> snakePosition = new ArrayList<>();
	Vector applePosition = new Vector(random.nextInt(20), random.nextInt(20));
	
	int score = 0;
	
	Font scoreFont = new Font("Inter", Font.PLAIN, 20);
	Font gameOverFont = new Font("Inter", Font.PLAIN, 65);
	
	Vector direction = new Vector(0, 0);
	
	
	SnakeCanvas(GameLoop loop) {
		gameLoop = loop;
		setPreferredSize(new Dimension(500, 500));
		
		snakePosition.add(new Vector(10, 10));
		snakePosition.add(new Vector(10, 11));
	}
	
	public void paint(Graphics graphics) {
		frameCount++;
		if (gameOver) {
			return;
		}
		
		Graphics2D g2D = (Graphics2D) graphics;
		
		g2D.setPaint(Color.black);
		g2D.setStroke(new BasicStroke(0));
		g2D.setFont(scoreFont);
		
		updateDirection();
		updateSnake();
		
		if (checkForGameOver(g2D)) {
			return;
		}
		
		drawGame(g2D);
		drawText(g2D);
	}
	
	private void updateSnake() {
		if (direction.mag() > 0) {
			snakePosition.add(0, snakePosition.get(0).copy().add(direction));
			snakePosition.remove(snakePosition.size() - 1);
		}
		
		if (snakePosition.get(0).equals(applePosition)) {
			score++;
			while (snakePosition.contains(applePosition)) {
				applePosition = new Vector(random.nextInt(20), random.nextInt(20));
			}
			snakePosition.add(snakePosition.get(snakePosition.size() - 1));
		}
	}
	
	private void updateDirection() {
		if (keyStack.size() > 0) {
			switch (keyStack.remove(0)) {
				case 'w' -> direction.set(0, -1);
				case 'a' -> direction.set(-1, 0);
				case 's' -> direction.set(0, +1);
				case 'd' -> direction.set(+1, 0);
			}
		}
	}
	
	private boolean checkForGameOver(Graphics2D g2D) {
		gameOver = false;
		if (snakePosition.subList(1, snakePosition.size()).contains(snakePosition.get(0))) {
			gameOver(g2D);
		} else {
			Vector sp = snakePosition.get(0);
			if (sp.x < 0 || sp.x > 19 || sp.y < 0 || sp.y > 19) {
				gameOver(g2D);
			}
		}
		return gameOver;
	}
	
	public void gameOver(Graphics2D g2D) {
		g2D.setPaint(new Color(0xffffff));
		g2D.setFont(gameOverFont);
		g2D.drawString("Game over", 100, 100);
		
		gameOver = true;
	}
	
	public void drawGame(Graphics2D g2D) {
		g2D.fillRect(0, 0, 500, 500);
		for (Vector pos : snakePosition) {
			g2D.setPaint(getGradientColor());
			g2D.fillRect((int) pos.x * 25, (int) pos.y * 25, 25, 25);
		}
		
		g2D.setPaint(new Color(0xFF0000));
		g2D.fillRoundRect((int) applePosition.x * 25, (int) applePosition.y * 25, 25, 25, 1, 1);
	}
	
	private Color getGradientColor() {
		int cGradient = (int) Math.floor((frameCount % 1530) / 255d);
		Color color;
		
		switch (cGradient) {
			// green to yellow
			case (0) -> color = new Color(frameCount % 255, 255, 0);
			
			//yellow to red
			case (1) -> color = new Color(255, 255 - (frameCount % 255), 0);
			
			//red to pink
			case (2) -> color = new Color(255, 0, frameCount % 255);
			
			//pink to blue
			case (3) -> color = new Color(255 - (frameCount % 255), 0, 255);
			
			//blue to turquoise
			case (4) -> color = new Color(0, frameCount % 255, 255);
			
			//turquoise to green
			case (5) -> color = new Color(0, 255, 255 - (frameCount % 255));
			default -> throw new IllegalStateException("Unexpected value: " + cGradient);
			// NumTools.map(index, 0, size, 255, 25)
		}
		return color;
	}
	
	private void drawText(Graphics2D g2D) {
		g2D.setStroke(new BasicStroke(0));
		g2D.setPaint(new Color(0xffffff));
		g2D.setFont(scoreFont);
		g2D.drawString(String.valueOf(score), 0, 20);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		char keyCode = e.getKeyChar();
		switch (keyCode) {
			case 'w' -> {
				if (!keyStack.contains('w') && (!direction.equals(Directions.DOWN) || !keyStack.isEmpty())) {
					keyStack.add('w');
				}
			}
			case 'a' -> {
				if (!keyStack.contains('a') && (!direction.equals(Directions.RIGHT) || !keyStack.isEmpty())) {
					keyStack.add('a');
				}
			}
			case 's' -> {
				if (!keyStack.contains('s') && (!direction.equals(Directions.UP) || !keyStack.isEmpty())) {
					keyStack.add('s');
				}
			}
			case 'd' -> {
				if (!keyStack.contains('d') && (!direction.equals(Directions.LEFT) || !keyStack.isEmpty())) {
					keyStack.add('d');
				}
			}
			
			case 'y' -> System.out.println(NumTools.map(10, 0, 19, 25, 255));
		}
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
