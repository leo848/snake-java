package io.github.leo848;

import java.awt.*;

@SuppressWarnings({"SameParameterValue", "unused"})
public class NumTools {
	static int colorLimit(int i) {
		return limit(i, 0, 255);
	}
	
	
	static int limit(int i, int start, int stop) {
		return i < start ? start : Math.min(i, stop);
	}
	
	static Color getGradientColor(int index, int size, int frameCount) {
		int cGradient = (int) Math.floor((frameCount % 1530) / 255d);
		int[] color;
		
		double brightness = NumTools.map(index, 0, size, 1, (float) 0.1);
		
		switch (cGradient) {
			case (0) -> color = new int[] {frameCount % 255, 255, 0};
			case (1) -> color = new int[] {255, 255 - (frameCount % 255), 0};
			case (2) -> color = new int[] {255, 0, frameCount % 255};
			case (3) -> color = new int[] {255 - (frameCount % 255), 0, 255};
			case (4) -> color = new int[] {0, frameCount % 255, 255};
			case (5) -> color = new int[] {0, 255, 255 - (frameCount % 255)};
			default -> throw new IllegalStateException("Unexpected value: " + cGradient);
			// NumTools.map(index, 0, size, 255, 25)
		}
		
		return new Color((int) (brightness * color[0]), (int) (brightness * color[1]), (int) (brightness * color[2]));
	}
	
	static float map(float f, float start1, float stop1, float start2, float stop2) {
		return ((f - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
	}
	
	static double limit(double d, double start, double stop) {
		return d < start ? start : Math.min(d, stop);
	}
}
