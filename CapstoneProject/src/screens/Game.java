package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import core.DrawingSurface;
import sprites.Avatar;
import sprites.Flag;
import sprites.PaintBlock;
import sprites.PaintBomb;
import sprites.Platform;
import sprites.Sprite;

/**
 * Represents the interactive game screen.
 *
 * @author Ishaan Singh and Aaditya Raj
 *
 */
public class Game extends Screen{

	Avatar player1, player2;
	Double player1Points, player2Points;
	ArrayList<Platform> platforms;
	ArrayList<Platform> boundaries;
	public static Flag flag;
	ArrayList<PaintBomb> bombs;
	DrawingSurface surface;
	private ArrayList<PaintBlock> bullets;
	/**
	 * Constructs a screen representing the interactive game screen.
	 * @param width The width of the screen.
	 * @param 0 The 0 of the screen.
	 */
	public Game(DrawingSurface s) {
		super(1600, 1200);
		surface = s;
		String pre = surface.sketchPath();
		player1 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), 200, 500, 200, 200, Color.RED);

		player2 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), 1300, 500, 200, 200, Color.BLUE);

		platforms = new ArrayList<Platform>();
		bullets = new ArrayList<PaintBlock>();
		boundaries = new ArrayList<Platform>();
		
		platforms.add(new Platform(1200, 200, 400, 100));
		boundaries.add(new Platform(0, 0, 1600, 1));
		boundaries.add(new Platform (0,1040,1600,1));
		boundaries.add(new Platform (0, 0, 1, 1200));
		boundaries.add(new Platform (1599, 0, 1, 1200));
	
		flag = new Flag(10, 10, 100, 100);
		bombs = new ArrayList<PaintBomb>();
		bombs.add(new PaintBomb(0, 0, 0, null));
	}

	public void setup() {
		
	}

	public void draw() {
		surface.image(surface.loadImage("img/background.png"), 1, 0);
		
		int player1Score = 0;
		int player2Score = 0;
		
		for(Platform p : platforms) {
			for (PaintBlock bullet : bullets) {
				p.paint(bullet);
				if (p.insidePlatform(bullet)) {
					bullets.remove(bullet);
				}
			}
			if (player1.onPaint(p)) {
				player1.boost();
			} else if (player1.isBoosted()) {
				player1.undoSpeedBoost();
			}
			if (player2.onPaint(p)) {
				player2.boost();
			} else if (player2.isBoosted()){
				player2.undoSpeedBoost();
			}
			p.draw(surface);
			player1Score += p.numBlocksWithColor(player1.getColor());
			player2Score += p.numBlocksWithColor(player2.getColor());
		}
		
		player1Score = (player1Score * 10000 / 48000); // change from hard-coded vals
		player2Score = (player2Score * 10000 / 48000);

		ArrayList<Sprite> checkPieces = new ArrayList<Sprite>();
		checkPieces.addAll(platforms);
		checkPieces.addAll(boundaries);
		checkPieces.add(player2);
		player1.act(checkPieces);
				
		checkPieces = new ArrayList<Sprite>();
		checkPieces.addAll(platforms);
		checkPieces.add(player1);
		checkPieces.addAll(boundaries);
		player2.act(checkPieces);
		
		for (PaintBlock b : bullets) {
			b.draw(surface);
		}
		
		if (flag.intersects(player1)) {
			player1.collectFlag();
		} else if (flag.intersects(player2)){
			player2.collectFlag();	
		}
		surface.textSize(30);
		surface.text("Player 1 Score: " + player1Score + "\n" + "Player 2 Score: " + player2Score, 1300, 50);

		flag.draw(surface);


		if (surface.isPressed(KeyEvent.VK_A)) {
			player1.walk(false);
		}
		
		if (surface.isPressed(KeyEvent.VK_D)) {
			player1.walk(true);
		}
			
		if (surface.isPressed(KeyEvent.VK_W)) {
			player1.jump();
		}

		if (surface.isPressed(KeyEvent.VK_LEFT)) {
			player2.walk(false);
		}
		
		if (surface.isPressed(KeyEvent.VK_RIGHT)) {
			player2.walk(true);
		
		}
		if (surface.isPressed(KeyEvent.VK_UP)) {
			player2.jump();
		}
		
		
		player1.draw(surface);
		
		player2.draw(surface);



	}

	public void mousePressed(int mouseX, int mouseY) {
		PaintBlock bullet = player1.shoot(new Point2D.Double(mouseX, mouseY));
		if (bullet != null) {
			bullets.add(bullet);
		}
	}




}
