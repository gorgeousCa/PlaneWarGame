package com.feiji;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlaneGame extends JPanel implements Runnable {
	// 瀹氫箟绐椾綋瀹介珮------闈欐�佸父閲�
	public static final int WiIDTH = 400;
	public static final int HEIGHT = 700;
	public static final int StartX = 420;
	public static final int StartY = 50;

	// 鍒涘缓鑻遍泟鏈哄璞�
	Hero hero = new Hero();
	Bullet[] bullets = {};
	FlyObject[] flys = {};// 鍚戜笂閫犲瀷
	// 瀹氫箟娓告垙鐘舵��
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int OVER = 3;
	// 瀹氫箟褰撳墠娓告垙鐘舵��
	public int status;

	// 瀹氫箟闈欐�佸睘鎬у瓨鍌ㄥ浘鐗�
	public static BufferedImage airpalne;
	public static BufferedImage background;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage gameover;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage start;

	Thread t = new Thread(this);
	// 闈欐�佷唬鐮佸潡锛氱粰鍥剧墖鍙橀噺璧嬪��
	static {
		try {
			airpalne = ImageIO.read(PlaneGame.class.getResource("airplane.png"));
			background = ImageIO.read(PlaneGame.class.getResource("background.png"));
			bee = ImageIO.read(PlaneGame.class.getResource("bee.png"));
			bullet = ImageIO.read(PlaneGame.class.getResource("bullet.png"));
			gameover = ImageIO.read(PlaneGame.class.getResource("gameover.png"));
			hero0 = ImageIO.read(PlaneGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(PlaneGame.class.getResource("hero1.png"));
			pause = ImageIO.read(PlaneGame.class.getResource("pause.png"));
			start = ImageIO.read(PlaneGame.class.getResource("start.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public PlaneGame(){ repaint(); }
	 */
	PlaneGame() {
		t.start();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("fly");
		PlaneGame pg = new PlaneGame();
		frame.add(pg);
		frame.setVisible(true);// 鐢诲竷鍙
		frame.setSize(WiIDTH, HEIGHT);// 璁剧疆鐢诲竷澶у皬
		frame.setResizable(false);// 涓嶅彲鏀瑰彉鐢诲竷澶у皬
		frame.setLocation(StartX, StartY);// 璁剧疆鐢诲竷浣嶇疆
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 鍏抽棴绐楀彛鏃剁粨鏉熺▼搴�
		frame.setAlwaysOnTop(true);// 璁剧疆鍦ㄧ獥鍙ｇ殑鏈�涓婃柟
		pg.action();

	}

	public void action() {
		MouseAdapter ma = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int x2 = e.getX();
				int y2 = e.getY();
				if (status == RUNNING) {
					hero.moveTo(x2, y2);

				}
			}

			public void mouseClicked(MouseEvent e) {
				switch (status) {
				case START:
					status = RUNNING;
					break;
				case RUNNING:
					status = PAUSE;
					break;
				case PAUSE:
					status = RUNNING;
					break;
				case OVER:
					status = START;
					hero.life=3;
					hero.score =0;
					hero.x=150;
					hero.y=500;
					flys=new FlyObject[0];
					bullets=new Bullet[0];
					break;

				}
			}
		};

		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);

		/*Timer timer;
		int interval = 10;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (status == RUNNING) {
					enterAction();// 瀛愬脊鍏ュ満
					moveAction();// 鎺у埗瀛愬脊銆侀琛岀墿杩愬姩
					hitAction();// 鑻遍泟鏈轰笌椋炶鐗╃鎾�

					bangAction();
					enemyAction();// 鏁屼汉鍏ュ満锛屾暟缁勬墿瀹�

				}

				repaint();
			}, interval, interval);
*/}
			private void bangAction() {
				for (int i = 0; i < bullets.length; i++) {
					Bullet b2 = bullets[i];
					if (bang(b2)) {// 鎵撲腑鐨勫瓙寮规秷澶�
						bullets[i] = bullets[bullets.length - 1];
						bullets[bullets.length - 1] = b2;
						bullets = Arrays.copyOf(bullets, bullets.length - 1);

					}
					}
				}

			

			private boolean bang(Bullet b2) {
				int index = -1;// 
				for (int i = 0; i < flys.length; i++) {
					FlyObject ufo = flys[i];
					if (ufo.shot(b2)) {
						index = i;

						break;
					}
				}
				if (index != -1) {
					FlyObject ufo2 = flys[index];
					if (ufo2 instanceof Bee) {
						Bee b = (Bee) ufo2;
						if (b.type == 0) {
							hero.setDoubleFire(3);
						} else {
							hero.life++;
						}
					}
					if (ufo2 instanceof EnemyPlane) {
						EnemyPlane ep = (EnemyPlane) ufo2;
						hero.score += 15;
					}
					flys[index] = flys[flys.length - 1];
					flys[flys.length - 1] = ufo2;
					flys = Arrays.copyOf(flys, flys.length - 1);
					return true;
				} else {
					return false;
				}

			}

		
	

	protected void hitAction() {
		for (int i = 0; i < flys.length; i++) {
			int index = -1;// 琚鎾炵殑椋炶鐗╀笅鏍�
			if (hero.bang(flys[i])) {
				hero.life--;
				hero.setDoubleFire(0);
				index = i;
				if (hero.life == 0) {
					status = OVER;
				}

			}

			if (index != -1) {
				FlyObject fo = flys[index];
				flys[index] = flys[flys.length - 1];
				flys[flys.length - 1] = fo;
				flys = Arrays.copyOf(flys, flys.length - 1);
			}

		}

	}

	int enemyIndex = 0;

	private void enemyAction() {// 鏁扮粍鎵╁
		enemyIndex++;
		if (enemyIndex % 70 == 0) {
			FlyObject obj = nextOne();
			flys = Arrays.copyOf(flys, flys.length + 1);
			flys[flys.length - 1] = obj;
		}
	}

	private FlyObject nextOne() {
		int x = (int) (Math.random() * 8);
		if (x == 5) {
			return new Bee();
		} else {
			return new EnemyPlane();
		}
	}

	protected void moveAction() {
		hero.move();// 鑻遍泟鏈哄姩
		// 瀛愬脊鍔�
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].move();
			if (bullets[i].out()) {
				Bullet bullets2 = bullets[i];
				bullets[i] = bullets[bullets.length - 1];
				bullets[bullets.length - 1] = bullets2;
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
			}
		}
		for (int i = 0; i < flys.length; i++) {
			flys[i].move();
			if (flys[i].out()) {
				FlyObject fo = flys[i];
				flys[i] = flys[flys.length - 1];
				flys[flys.length - 1] = fo;
				flys = Arrays.copyOf(flys, flys.length - 1);
			}
		}
	}

	// 瀛愬脊璁℃暟鍣�
	int enterIndex = 0;

	protected void enterAction() {
		enterIndex++;
		if (enterIndex % 20 == 0) {
			Bullet[] shoot = hero.shoot();
			bullets = Arrays.copyOf(bullets, shoot.length + bullets.length);
			System.arraycopy(shoot, 0, bullets, bullets.length - shoot.length, shoot.length);
		}
	}

	@Override
	public void paint(Graphics g) {

		// 鍥涗釜鍙傛暟锛涗綘瑕佺敾鐨勫浘;x鐨勪綅缃�;y鐨勪綅缃�,this瀵硅薄/null锛堜笉鐢伙級
		g.drawImage(background, 0, 0, null);
		// 鐢诲垎鏁板拰鐢熷懡
		g.setFont(new Font("寰蒋闆呴粦", Font.BOLD, 15));
		g.setColor(Color.orange);
		g.drawString("SCORE=" + hero.score, 10, 20);
		g.drawString("LIFE=" + hero.life, 20, 40);
		g.drawImage(hero.image, hero.x, hero.y, this);
		for (int i = 0; i < bullets.length; i++) {
			Bullet bullet2 = bullets[i];
			g.drawImage(bullet2.image, bullet2.x, bullet2.y, this);
		}
		for (int i = 0; i < flys.length; i++) {// 瀛愬脊
			FlyObject fo = flys[i];
			g.drawImage(fo.image, fo.x, fo.y, this);

		}
		switch (status) {
		case START:
			g.drawImage(PlaneGame.start, 0, 0, this);
			break;
		case PAUSE:
			g.drawImage(PlaneGame.pause, 0, 0, this);
			break;
		case OVER:
			g.drawImage(PlaneGame.gameover, 0, 0, this);
			break;
		}
	}

	@Override
	public void run() {
		AudioClip ac;
		try {		
			ac = Applet.newAudioClip(PlaneGame.class .getResource("game_music.wav").toURI().toURL());
			ac.loop();
		} catch (MalformedURLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			if (status == RUNNING) {
				enterAction();// 瀛愬脊鍏ュ満
				moveAction();// 鎺у埗瀛愬脊銆侀琛岀墿杩愬姩
				hitAction();// 鑻遍泟鏈轰笌椋炶鐗╃鎾�
				bangAction();
				enemyAction();// 鏁屼汉鍏ュ満锛屾暟缁勬墿瀹�

			}

			repaint();
			
			try {
				int time = 10;
				if (hero.score >= 500) {
					time = 5;
				}
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

	}
}
