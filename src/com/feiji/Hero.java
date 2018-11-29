package com.feiji;

import java.awt.image.BufferedImage;

public class Hero extends FlyObject {
	// 英雄机的生命
	int life;
	BufferedImage[] imgs;
	int score = 0;
	int doubleFire = 0;// 双倍火力

	public void getDoubleFire() {
		doubleFire = 2;
	}

	public void setDoubleFire(int doubleFire) {
		this.doubleFire = doubleFire;
	}

	public Hero() {
		life = 3;
		imgs = new BufferedImage[] { PlaneGame.hero1, PlaneGame.hero0 };
		x = 100;
		y = 400;
		image = PlaneGame.hero0;
		width = image.getWidth();
		heigth = image.getHeight();

	}

	public void moveTo(int x, int y) {
		this.x = x - width / 2;
		this.y = y - heigth / 2;
	}

	int index;// 切换图片的索引

	@Override
	public void move() {
		index++;
		switch (index % 2) {
		case 0:
			image = imgs[0];
			break;
		case 1:
			image = imgs[1];
			break;

		}

	}

	// 英雄机生成子弹
	public Bullet[] shoot() {
		if (doubleFire > 0) {
			Bullet[] bullet = new Bullet[2];
			bullet[0] = new Bullet(this.x + width / 4, this.y);
			bullet[1] = new Bullet(this.x + 3 * width / 4, this.y);
			return bullet;
		} else {
			Bullet[] bullet = new Bullet[1];
			bullet[0] = new Bullet(this.x + width / 2, this.y);
			return bullet;

		}
	}

	public boolean hit(FlyObject fo) {// 子弹
		int bx = this.x + width / 2;
		int by = this.y;
		return bx >= fo.x && fo.x + fo.width >= bx && fo.y + fo.heigth == by;
	}
	public boolean bang(FlyObject fo){
		return this.x<=fo.x+fo.width
				&&this.y+this.heigth>=fo.y
				&&this.x+this.width>=fo.x
				&&fo.y+fo.heigth>=this.y;
		/*return this.x+this.width>=fo.x&&fo.x+fo.width>=this.x&&this.y+this.heigth>=fo.y
				&&fo.y+fo.heigth>=this.y;*/
	}

	@Override
	public boolean out() {
		return false;
	}
}
