package io.github.leo848;

import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {
	SnakeCanvas canvas;
	SnakeFrame(GameLoop gameLoop) {
		canvas = new SnakeCanvas(gameLoop);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(canvas);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		addKeyListener(canvas);
	}
	
	public void repaintCanvas() {
		canvas.repaint();
	}
}
