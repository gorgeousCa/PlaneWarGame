package com.feiji;

import java.awt.image.BufferedImage;

//超类
public abstract class FlyObject {
	BufferedImage image;
	int x;
	int y;
	int width;
	int heigth;

	public abstract void move();
	public abstract boolean out();
	
	public boolean shot(Bullet bullet) {
		int x = bullet.x;
		int y = bullet.y;	
		return x > this.x && x < this.x + width &&y>this.y&&y < this.y + heigth;
	}
}
