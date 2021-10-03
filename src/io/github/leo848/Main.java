package io.github.leo848;

public class Main {
	public static void main(String[] args) {
		System.gc();
		GameLoop gameLoop = new GameLoop();
		gameLoop.start();
	}
}
