package io.github.leo848;

import java.awt.*;
import java.util.*;

public class Directions {
	final static Vector NONE = new Vector(0, 0);
	final static Vector LEFT = new Vector(-1, 0);
	final static Vector RIGHT = new Vector(1, 0);
	final static Vector UP = new Vector(0, -1);
	final static Vector DOWN = new Vector(0, 1);
	
	public static HashMap<Vector, Image> getImageHashMap() {
		HashMap<Vector, Image> imageHashMap = new HashMap<>();
		imageHashMap.put(UP, Toolkit.getDefaultToolkit().getImage("assets/arrow-up-solid.svg"));
		return imageHashMap;
	}
}
