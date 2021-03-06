package io.github.leo848;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeCanvas extends JPanel implements KeyListener {
	
	final Snake snake = new Snake(this);
	
	final long startTime = System.nanoTime();
	final Vector gridSize;
	private final Vector size;
	Random random = new Random();
	GameLoop gameLoop;
	int frameCount = 0;
	boolean gameOver = false;
	Vector grid = new Vector(21, 21);
	ArrayList<Character> keyStack = new ArrayList<>();
	ArrayList<Apple> applePositions = new ArrayList<>(5);
	
	int score = 0;
	
	Font scoreFont = new Font("Inter", Font.PLAIN, 20);
	
	int skippedFrames = 12;
	
	SnakeCanvas(GameLoop loop, Vector size) {
		gameLoop = loop;
		setPreferredSize(new Dimension((int) size.x, (int) size.y));
		this.size = size;
		
		for (int i = 0; i < 5; i++) {
			newRandomApple();
		}
		gridSize = grid.copy().div(size);
	}
	
	public Vector randomGridLocation() {
		return new Vector(random.nextInt((int) grid.x), random.nextInt((int) grid.y));
	}
	
	private void newRandomApple() {
		Apple newAppleVector;
		
		do {
			newAppleVector = new Apple(this, randomGridLocation());
		} while (applePositions.contains(newAppleVector) || snake.positions.contains(newAppleVector.pos));
		applePositions.add(newAppleVector);
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g2D = (Graphics2D) graphics;
		
		if (gameOver) {
			drawSplashEffect(g2D);
			return;
		}
		
		frameCount++;
		
		setInitialValues(g2D);
		
		if (frameCount % skippedFrames == 0) {
			updateDirection();
			updateSnake();
		}
		
		if (snake.checkForGameOver()) {
			g2D.drawRect(-0, -0, (int) size.x, (int) size.y);
			drawText(g2D);
			drawSplashEffect(g2D);
			gameOver = true;
			return;
		}
		
		updateGame();
		drawGame(g2D);
		drawText(g2D);
	}
	
	private void setInitialValues(Graphics2D g2D) {
		g2D.setPaint(Color.black);
		g2D.setStroke(new BasicStroke(0));
		g2D.setFont(scoreFont);
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
							snake.positions.get(0).x * gridSize.x / 2 - gridSize.x / 2,
							snake.positions.get(0).x * gridSize.x / 2 + gridSize.x / 2) - 3,
					(int) NumTools.map((float) random.nextGaussian(),
							-1,
							1,
							snake.positions.get(0).y * gridSize.y / 2 - gridSize.y / 2,
							snake.positions.get(0).y * gridSize.y / 2 + gridSize.y / 2) - 3,
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
	
	public void updateGame() {
		for (Apple apple : applePositions) {
			apple.update(this);
		}
	}
	
	public void drawGame(Graphics2D g2D) {
		snake.draw(g2D);
		
		g2D.setPaint(new Color(0xff0000));
		
		for (Apple apple : applePositions) {
			apple.update(this);
			apple.draw(g2D);
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
