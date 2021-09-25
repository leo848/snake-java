package io.github.leo848;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SnakeCanvas extends JPanel implements KeyListener {
	
	final Snake snake = new Snake();
	
	final long startTime = System.nanoTime();
	Random random = new Random();
	GameLoop gameLoop;
	
	int frameCount = 0;
	boolean gameOver = false;
	
	ArrayList<Character> keyStack = new ArrayList<>();
	
	
	ArrayList<Vector> applePositions = new ArrayList<>(5);
	
	int score = 0;
	
	Font scoreFont = new Font("Inter", Font.PLAIN, 20);
	Font gameOverFont = new Font("Inter", Font.PLAIN, 65);
	
	
	int skippedFrames = 12;
	
	
	SnakeCanvas(GameLoop loop) {
		gameLoop = loop;
		setPreferredSize(new Dimension(500, 500));
		
		
		for (int i = 0; i < 5; i++) {
			newRandomApple();
		}
		
	}
	
	private void newRandomApple() {
		Vector newAppleVector;
		do {
			newAppleVector = new Vector(random.nextInt(20), random.nextInt(20));
		} while (applePositions.contains(newAppleVector) || snake.positions.contains(newAppleVector));
		applePositions.add(newAppleVector);
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g2D = (Graphics2D) graphics;
		
		if (gameOver) {
			drawSplashEffect(g2D);
			return;
		}
		
		frameCount++;
		
		g2D.setPaint(Color.black);
		g2D.setStroke(new BasicStroke(0));
		g2D.setFont(scoreFont);
		
		if (frameCount % skippedFrames == 0) {
			updateDirection();
			updateSnake();
		}
		
		if (frameCount % 100 == random.nextInt(100)) {
			applePositions.remove(0);
			newRandomApple();
		}
		
		
		if (snake.checkForGameOver(g2D)) {
			g2D.drawRect(-0, -0, 500, 500);
			drawText(g2D);
			drawSplashEffect(g2D);
			gameOver = true;
			return;
		}
		
		drawGame(g2D);
		drawText(g2D);
	}
	
	private void drawSplashEffect(Graphics2D g2D) {
		Color gColor = NumTools.getGradientColor(0, 1, frameCount);
		for (int i = 0; i < 20; i++) {
			int red = NumTools.colorLimit((gColor.getRed() + random.nextInt(301)) - 150);
			int green = NumTools.colorLimit((gColor.getGreen() + random.nextInt(301)) - 150);
			int blue = NumTools.colorLimit((gColor.getBlue() + random.nextInt(301)) - 150);
			g2D.setPaint(new Color(red, green, blue));
			
			g2D.fillOval((int) NumTools.map((float) random.nextGaussian(),
			                                -1,
			                                1,
			                                snake.positions.get(0).x * 25 - 25,
			                                snake.positions.get(0).x * 25 + 25) - 3,
			             (int) NumTools.map((float) random.nextGaussian(),
			                                -1,
			                                1,
			                                snake.positions.get(0).y * 25 - 25,
			                                snake.positions.get(0).y * 25 + 25) - 3,
			             5,
			             5);
		}
	}
	
	
	private void updateSnake() {
		snake.updatePosition();
		
		if (snake.updateAppleCollision(applePositions)) {
			score++;
			newRandomApple();
		}
	}
	
	private void updateDirection() {
		if (keyStack.size() > 0) {
			switch (keyStack.remove(0)) {
				case 'w' -> snake.setDirection(Directions.UP);
				case 'a' -> snake.setDirection(Directions.LEFT);
				case 's' -> snake.setDirection(Directions.DOWN);
				case 'd' -> snake.setDirection(Directions.RIGHT);
			}
		}
	}
	
	
	public void drawGame(Graphics2D g2D) {
		snake.draw(g2D, frameCount);
		
		g2D.setPaint(new Color(0xFF0000));
		
		for (Vector pos : applePositions) {
			g2D.fillRoundRect((int) pos.x * 25, (int) pos.y * 25, 25, 25, 1, 1);
			
		}
	}
	
	private void drawText(Graphics2D g2D) {
		g2D.setStroke(new BasicStroke(0));
		g2D.setPaint(new Color(0xffffff));
		g2D.setFont(scoreFont);
		
		g2D.drawString(String.valueOf(score), 0, 20);
		
		double seconds = (double) ((System.nanoTime() - startTime) / 1_000_000_000);
		g2D.drawString(String.format("%.2f", (double) frameCount / seconds), 0, 50);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_UP, KeyEvent.VK_W -> {
				if (!keyStack.contains('w') && (!snake.direction.equals(Directions.DOWN) || !keyStack.isEmpty())) {
					keyStack.add('w');
				}
			}
			case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
				if (!keyStack.contains('a') && (!snake.direction.equals(Directions.RIGHT) || !keyStack.isEmpty())) {
					keyStack.add('a');
				}
			}
			case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
				if (!keyStack.contains('s') && (!snake.direction.equals(Directions.UP) || !keyStack.isEmpty())) {
					keyStack.add('s');
				}
			}
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
				if (!keyStack.contains('d') && (!snake.direction.equals(Directions.LEFT) || !keyStack.isEmpty())) {
					keyStack.add('d');
				}
			}
			
			case KeyEvent.VK_SPACE -> skippedFrames = 4;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_SPACE -> skippedFrames = 12;
			case KeyEvent.VK_CAPS_LOCK -> frameCount += 20;
		}
	}
}
