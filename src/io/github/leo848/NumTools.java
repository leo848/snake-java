package io.github.leo848;

public class NumTools {
	static float map(float f, float start1, float stop1, float start2, float stop2) {
		return ((f - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
	}
}
