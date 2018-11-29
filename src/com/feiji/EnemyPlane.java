package com.feiji;

public class EnemyPlane extends FlyObject {// 1
	int speed = 1;// 2

	public EnemyPlane() {// 3
		image = PlaneGame.airpalne;
		width = image.getWidth();
		heigth = image.getHeight();
		x = (int) (Math.random() * PlaneGame.WiIDTH - width);
		if (x < 0) {
			x = 0;
		}
		y = -heigth;

	}

	public void move() {
		y = y + speed;
	}

	@Override
	public boolean out() {
		return y >= PlaneGame.HEIGHT;
	}
}
