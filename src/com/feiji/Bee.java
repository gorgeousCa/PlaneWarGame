package com.feiji;

public class Bee extends FlyObject {
	int xspeed = 2;
	int yspeed = 1;
	//定义蜜蜂奖励类型
	int type;

	Bee() {// 构造方法赋值
		image = PlaneGame.bee;
		width = image.getWidth();
		heigth = image.getHeight();
		x = (int) (Math.random() * PlaneGame.WiIDTH - width);
		if (x < 0) {
			x = 0;
		}
		y = -heigth;
		type=(int)(Math.random()*2);
	}

	public void move() {// 蜜蜂
		x = x + xspeed;
		y = y + yspeed;
		if (x >= PlaneGame.WiIDTH - width) {
			xspeed = -2;
		} else if (x <= 0) {
			xspeed = 2;
		}
	}

	@Override
	public boolean out() {
		return y>=PlaneGame.HEIGHT;
	}

}
