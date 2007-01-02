package com.star.sms.richclient.support;

import java.util.Random;

public class RandomColorPane extends ColorPane {

	private Random random = new Random();

	public RandomColorPane() {
	}

	public RandomColorPane(String id) {
		super(id);
	}

	public RandomColorPane(String id, int color) {
		super(id,color);
	}

	public int getColor() {
		return color == 0 ? color = random.nextInt(15) + 1 : color;
	}

}
