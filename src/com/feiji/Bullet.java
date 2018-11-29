package com.feiji;

public class Bullet extends FlyObject{
	int speed=3;
	public Bullet(int x,int y){
		this.x=x;
		this.y=y;
		image=PlaneGame.bullet;
		width=image.getWidth();
		heigth=image.getHeight();
	}

	@Override
	public void move() {
		y=y-speed;
		
	}

	@Override
	public boolean out() {
		
		return y+heigth<=0;
	}

}
