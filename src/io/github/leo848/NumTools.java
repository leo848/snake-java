package io.github.leo848;

public class NumTools {
	static int colorLimit(int d) {
		return limit(d, 0, 255);
	}
	
	static int limit(int d, int start, int stop) {
		return d < start ? start : Math.min(d, stop);
	}
	
	static double limit(double d, double start, double stop) {
		return d < start ? start : Math.min(d, stop);
	}
	
	static float map(float f, float start1, float stop1, float start2, float stop2) {
		return ((f - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
	}
}
