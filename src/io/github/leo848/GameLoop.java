package io.github.leo848;

import java.awt.*;
import java.awt.image.*;

public class GameLoop implements Runnable {
	public Thread thread;
	public BufferStrategy bs;
	public Graphics g;
	SnakeFrame snakeFrame;
	private Boolean running = false;
	
	int sleepMillis = 200;
	
	public GameLoop() {
	
	}
	
	
	private void init() {
		System.out.println("init");
		snakeFrame = new SnakeFrame(this);
		
	}
	
	
	private void Update() {
		//System.out.println("update");
		
	}
	
	
	private void Render() {
		snakeFrame.repaintCanvas();
	}
	
	
	public void run() {
		System.out.println("run");
		init();
		
		while (running) {
			Update();
			Render();
			try {
				Thread.sleep(sleepMillis);
			} catch (InterruptedException ignored) {
			
			}
			
		}
		
		stop();
	}
	
	
	public synchronized void start() {
		System.out.println("start");
		if (running)
			return;
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		snakeFrame.dispose();
		try {
			thread.join();
		} catch (Exception ignored) {
		
		}
	}
}