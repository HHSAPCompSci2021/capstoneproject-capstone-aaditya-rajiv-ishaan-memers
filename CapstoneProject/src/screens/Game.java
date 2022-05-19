package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import core.DrawingSurface;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
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
public class Game extends Screen implements NetworkListener {

	private Avatar player1, player2;
	private ArrayList<Platform> platforms;
	private ArrayList<Platform> boundaries;
	public static Flag flag;
	private ArrayList<PaintBomb> bombs;
	public DrawingSurface surface;
	private ArrayList<PaintBlock> bullets;
	private boolean flagTaken;
	
	/**
	 * Constructs a screen representing the interactive game screen.
	 * @param width The width of the screen.
	 * @param 0 The 0 of the screen.
	 */
	public Game(DrawingSurface s) {
		super(1600, 1200);
		surface = s;
		String pre = surface.sketchPath();
		player1 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), 100, 840, 200, 200, Color.RED);

		player2 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), 1300, 840, 200, 200, Color.BLUE);

		platforms = new ArrayList<Platform>();
		bullets = new ArrayList<PaintBlock>();
		boundaries = new ArrayList<Platform>();
		
		platforms.add(new Platform(650, 250, 250, 50));
		
		platforms.add(new Platform(1150, 325, 200, 50));

		platforms.add(new Platform(250, 325, 200, 50));
		
		platforms.add(new Platform(700, 500, 150 , 50));
		
		platforms.add(new Platform(0, 600, 300, 50));
		
		platforms.add(new Platform(600, 750, 350, 50));
		
		platforms.add(new Platform(1300, 600, 300, 50));

		
		
		boundaries.add(new Platform(0, 0, 1600, 1));
		boundaries.add(new Platform (0,1040,1600,1));
		boundaries.add(new Platform (0, 0, 1, 1200));
		boundaries.add(new Platform (1599, 0, 1, 1200));
	
		flag = new Flag(750, 50, 175, 200);
		bombs = new ArrayList<PaintBomb>();
		bombs.add(new PaintBomb(0, 0, 0, null));
		
		flagTaken = false;
	}

	public void setup() {
		
	}

	public void draw() {
		surface.image(surface.loadImage("img/background.png"), 1, 0);
		
		int player1Score = 0;
		int player2Score = 0;
		int numBlocks = 0;
		
		for(Platform p : platforms) {
			ArrayList<PaintBlock> toRemove = new ArrayList<PaintBlock>();
			for (PaintBlock bullet : bullets) {
				p.paint(bullet);
				if (p.contains(bullet)) {
					toRemove.add(bullet);
				}
			}
			bullets.removeAll(toRemove);
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
			
			numBlocks += p.getBorder().size();
		}	
		
		player1Score *= 1000;
		player2Score *= 1000;
		
		player1Score /= numBlocks;
		player2Score /= numBlocks;
		
		
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
		
		ArrayList<PaintBlock> toRemove = new ArrayList<PaintBlock>();
		
		for (PaintBlock b : bullets) {
			b.draw(surface);
			

			if(b.intersects(player1)) {
				if(player1.loseHealth() && player1.hasFlag()) {
					flagTaken = false;
				}
				
				toRemove.add(b);
			} 
			
			if(b.intersects(player2)) {
				if(player2.loseHealth() && player2.hasFlag()) {
					flagTaken = false;
				}
				
				System.out.println("Health: " + player2.getHealth());
				toRemove.add(b);
			}
			
			if(b.intersects(flag)) {
				toRemove.add(b);
			}
		}
		
		bullets.removeAll(toRemove);
		
		if (flag.intersects(player1)) {
			player1.collectFlag();
			flagTaken = true;
		} else if (flag.intersects(player2)){
			player2.collectFlag();	
			flagTaken = true;
		} 
		
		if(!flagTaken) {
			flag.draw(surface);	
		} else {
			if(player1.hasFlag()) {
				flag.draw(surface, (int)player1.x, (int)player1.y);
				
				if((Math.abs(player1.x - player1.getBase().x) < 100) && ((Math.abs(player1.y - player1.getBase().y) < 50))) {
					flagTaken = false;
					player1.touchdown();
				}
				
			} else {
				flag.draw(surface, (int)player2.x, (int)player2.y);
				
				if((Math.abs(player2.x - player2.getBase().x) < 100) && ((Math.abs(player2.y - player2.getBase().y) < 50))) {
					flagTaken = false;
					player2.touchdown();
				}
			}
			
		}
		
		player1Score += (player1.getCaptures() * 200);
		player1Score -= (player1.getNumDeaths() * 200);
		
		player2Score += (player2.getCaptures() * 200);
		player2Score -= (player2.getNumDeaths() * 200);
		
		if (surface.isPressed(KeyEvent.VK_A)) {
			player1.walk(false);
		}
		
		if (surface.isPressed(KeyEvent.VK_D)) {
			player1.walk(true);
		}
			
		if (surface.isPressed(KeyEvent.VK_W)) {
			if (player1.onPlatform()) {
				player1.jump();
			}
		}

		if (surface.isPressed(KeyEvent.VK_LEFT)) {
			player2.walk(false);
		}
		
		if (surface.isPressed(KeyEvent.VK_RIGHT)) {
			player2.walk(true);
		
		}
		if (surface.isPressed(KeyEvent.VK_UP)) {
			if (player2.onPlatform()) {
				player2.jump();
			}
		}
		
	
		surface.push();
		surface.fill(Color.WHITE.getRGB());
		surface.textSize(30);
		surface.text("Player 1 Score: " + player1Score + "\n" + "Player 2 Score: " + player2Score, 1300, 50);
		surface.pop();
		
		player1.draw(surface);
		
		player2.draw(surface);

	}

	public void mousePressed(int mouseX, int mouseY) {
		PaintBlock bullet = player1.shoot(new Point2D.Double(mouseX, mouseY));
		if (bullet != null) {
			bullets.add(bullet);
		}
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		// TODO Auto-generated method stub
		
	}




}
