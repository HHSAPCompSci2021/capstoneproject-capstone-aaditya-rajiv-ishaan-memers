package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Queue;

import core.DrawingSurface;
import g4p_controls.GButton;
import g4p_controls.GEvent;
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
	private DrawingSurface surface;
	private ArrayList<PaintBlock> bullets;
	private int player1Score, player2Score;
	private int timer;

	private boolean flagTaken;
	private int maxTime;
	private Avatar activePlayer;

	
	private static final String messageTypeMove = "PLAYER_MOVEMENT";
	private static final String messageTypeShoot = "SHOOT_BULLET";
	private static final String messageTypeJump = "PLAYER_JUMP";
	private static final String messageTypeFlagMovement = "FLAG_MOVEMENT";
	private static final String messageTypePlatformPaint = "PLATFORM_PAINT";
	private static final String messageTypeFlagCapture = "FLAG_CAPTURE";
	private static final String  messageTypeFlagDropped = "FLAG_DROPPED";
	private static final String messageTypePlayerKilled = "PLAYER_KILLED";
	private static final String messageTypeTouchdown = "TOUCHDOWN";
	private static final String messageTypeBombThrow = "BOMB_THROW";
	
	
	/**
	 * Constructs a screen representing the interactive game screen.
	 * @param width The width of the screen.
	 * @param 0 The 0 of the screen.
	 */
	public Game(DrawingSurface s) {
		super(1600, 1200);
		surface = s;
		
	}

	public void setup() {
		String pre = surface.sketchPath();
		player1 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), surface.loadImage(pre + "/" + "img/RedPlayerGun.png"), 100, 840, 200, 200, Color.RED);
		player2 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), surface.loadImage(pre + "/" + "img/BluePlayerGun.png"), 1300, 840, 200, 200, Color.BLUE);

		platforms = new ArrayList<Platform>();
		bullets = new ArrayList<PaintBlock>();
		boundaries = new ArrayList<Platform>();
		
		platforms.add(new Platform(650, 225, 250, 50));
		
		platforms.add(new Platform(1175, 325, 200, 50));

		platforms.add(new Platform(225, 325, 200, 50));
		
		platforms.add(new Platform(500, 500, 100 , 50));
		
		platforms.add(new Platform(950, 500, 100 , 50));

		platforms.add(new Platform(0, 675, 250, 50));
		
		platforms.add(new Platform(600, 750, 350, 50));
		
		platforms.add(new Platform(1350, 675, 250, 50));

		boundaries.add(new Platform(0, 0, 1600, 1));
		boundaries.add(new Platform (0,1040,1600,1));
		boundaries.add(new Platform (0, 0, 1, 1200));
		boundaries.add(new Platform (1599, 0, 1, 1200));
	
		flag = new Flag(730, 50, 90, 100);
		bombs = new ArrayList<PaintBomb>();
		bombs.add(player1.getBomb());
		bombs.add(player2.getBomb());
		
		flagTaken = false;
		timer = 0;
		
	}

	public void draw() {
		surface.image(surface.loadImage("img/background.png"), 1, 0);
		maxTime = surface.getTimeLimit();
		
		int player1Score = 0;
		int player2Score = 0;
		int numBlocks = 0;
		
		if(timer > maxTime) {
			surface.switchScreen(ScreenSwitcher.WIN_SCREEN);
		}
		
		
		player1.fall();
		player2.fall();
		for(Platform p : platforms) {
			ArrayList<PaintBlock> toRemove = new ArrayList<PaintBlock>();
			ArrayList<PaintBlock> toAdd = new ArrayList<PaintBlock>();
			for (PaintBlock bullet : bullets) {
				p.paint(bullet);
//				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePlatformPaint, p, bullet.x, bullet.y, bullet.width, bullet.getColor());
				if (bullet instanceof PaintBomb && p.intersects(bullet)) {
					PaintBomb bomb = (PaintBomb) bullet;
					ArrayList<PaintBlock> bombBullets = bomb.blowUp();
					toRemove.add(bomb);
					toAdd.addAll(bombBullets);
				}
				if (p.contains(bullet)) {
					toRemove.add(bullet);
				}
			}
			bullets.removeAll(toRemove);
			bullets.addAll(toAdd);
			if (player1.onPaint(p)) {
				System.out.println("BOOSTED");
				player1.boost();
			} 
			else if (player1.intersects(p) && player1.isBoosted()) {
				System.out.println("UNBOOSTED");
				player1.undoSpeedBoost();
			}
			if (player2.onPaint(p)) {
				player2.boost();
			} else if (player2.intersects(p) && player2.isBoosted()){
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

			if(b.intersects(player1) && b.getColor() != player1.getColor()) {
				if(player1.loseHealth(player2)) {
					nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypePlayerKilled, 1});
					if (!player1.hasFlag()) {
						flagTaken = false;
						nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeFlagDropped});
					}
				}
				
				toRemove.add(b);
			} 
			
			if(b.intersects(player2) && b.getColor() != player2.getColor()) {
				if(player2.loseHealth(player1)) {
					nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypePlayerKilled, 2});
					if (!player2.hasFlag()) {
						flagTaken = false;
						nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeFlagDropped});
					}
				}
				
				toRemove.add(b);
			}
			
		}
		
		bullets.removeAll(toRemove);
		
		if (flag.intersects(player1)) {
			player1.collectFlag();
			flagTaken = true;
			nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeFlagCapture, 1});
		} else if (flag.intersects(player2)){
			player2.collectFlag();	
			flagTaken = true;
			nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeFlagCapture, 2});
		} 
		
		if(!flagTaken) {
			flag.draw(surface);	
		} else {
			if(player1.hasFlag()) {
				if((Math.abs(player1.x - player1.getBase().x) < 100) && ((Math.abs(player1.y - player1.getBase().y) < 50))) {
					flagTaken = false;
					flag.reset();
					player1.touchdown();
					nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeTouchdown, 1});
					flag.draw(surface);
				} else {
					flag.draw(surface, (int)player1.x, (int)player1.y);
//					nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeFlagMovement, flag.getX(), flag.getY());
				}
			} else {
				if((Math.abs(player2.x - player2.getBase().x) < 100) && ((Math.abs(player2.y - player2.getBase().y) < 50))) {
					flagTaken = false;
					flag.reset();
					nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeTouchdown, 2});
					flag.draw(surface);
				} else {
					flag.draw(surface, (int)player2.x, (int)player2.y);
//					nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeFlagMovement, flag.getX(), flag.getY());
				}
			}	
		}
		
		player1Score += (player1.getCaptures() * 25);
		player2Score += (player2.getCaptures() * 25);
	
//		player1Score -= (player1.getNumDeaths() * 25);
//		
//		player2Score -= (player2.getNumDeaths() * 25);
		
		if (surface.isPressed(KeyEvent.VK_A)) {
			activePlayer.walk(false);
			nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeMove, activePlayer.getX(), activePlayer.getY()});
		}
		
		if (surface.isPressed(KeyEvent.VK_D)) {
			activePlayer.walk(true);			
			nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeMove, activePlayer.getX(), activePlayer.getY()});
		}
			
		if (surface.isPressed(KeyEvent.VK_W)) {
			if (activePlayer.onPlatform()) {
				activePlayer.jump();
				nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeJump});
			}
		}
		
	
		surface.push();
		surface.fill(Color.WHITE.getRGB());
		surface.textSize(30);
		this.player1Score = player1Score;
		this.player2Score = player2Score;
		
		surface.text("Player 1 Score: " + this.player1Score + "\n" + "Player 2 Score: " + this.player2Score, 1300, 50);
		surface.pop();
		
		player1.draw(surface);
		
		player2.draw(surface);
		
		processNetworkMessages();

		timer++;
		
	}
	
	public void processNetworkMessages() {
		if (nm == null) {
			return;
		}
		
		Queue<NetworkDataObject> queue = nm.getQueuedMessages();
		
		while (!queue.isEmpty()) {
			NetworkDataObject ndo = queue.poll();
			
			Avatar player = (activePlayer == player1 ? player2 : player1);
			
			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(messageTypeMove)) {
					player.moveToLocation((double) ndo.message[1], (double) ndo.message[2]);
				} else if (ndo.message[0].equals(messageTypeShoot)) {
					PaintBlock bullet = player.shoot(new Point2D.Double((double) ndo.message[1], (double) ndo.message[2]));
					if (bullet != null) {
						bullets.add(bullet);
					}
				} else if (ndo.message[0].equals(messageTypeJump)) {
					player.jump();
				} else if (ndo.message[0].equals(messageTypePlayerKilled)){
					Avatar killed = (int) (ndo.message[1]) == 1 ? player1 : player2;
					killed.respawn();
				} else if (ndo.message[0].equals(messageTypeFlagDropped)){
					flagTaken = false;
				} else if (ndo.message[0].equals(messageTypeTouchdown)){
					Avatar winning = (int) (ndo.message[1]) == 1 ? player1 : player2;
					flagTaken = false;
					flag.reset();
					winning.touchdown();
				} else if (ndo.message[0].equals(messageTypeBombThrow)) {
					PaintBlock bullet = player.throwBomb(new Point2D.Double((double) ndo.message[1], (double) ndo.message[2]));
					if (bullet != null) {
						bullets.add(bullet);
					}
				} else if (ndo.message[0].equals(messageTypeFlagCapture)){
					Avatar capturer = (int) (ndo.message[1]) == 1 ? player1 : player2;
					capturer.collectFlag();
					flagTaken = true;
				}
				else if (ndo.message[0].equals(messageTypeFlagMovement)) {
					flag.draw(surface, (int) ndo.message[1], (int) ndo.message[2]);
				}  else if (ndo.message[0].equals(messageTypePlatformPaint)) {
					for (Platform p : platforms) {
						if (((Platform) ndo.message[1]).equals(p)) {
							PaintBlock block = new PaintBlock((int) ndo.message[2], (int) ndo.message[3], (int) ndo.message[4]);
							block.fill((Color) ndo.message[5]);
							p.paint(block);
						}
					}
				} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
						this.setup();
						surface.switchScreen(ScreenSwitcher.WIN_SCREEN);
						
				}
			}


			

		}
	}
	

	public void mousePressed(int mouseX, int mouseY) {
		PaintBlock bullet;
		if (!activePlayer.canThrowBomb()) {
			bullet = activePlayer.shoot(new Point2D.Double(mouseX, mouseY));
			if (bullet != null) {
				bullets.add(bullet);
				nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeShoot, (double) mouseX, (double) mouseY});
			}
		} else {
			bullet = activePlayer.throwBomb(new Point2D.Double(mouseX, mouseY));
			if (bullet != null) {
				bullets.add(bullet);
				nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeBombThrow, (double) mouseX, (double) mouseY});
			}
		}
		
	}
	
	
	public int getActivePlayerScore() {
		return (activePlayer == player1 ? player1Score : player2Score);
	}
	
	public int getOpponentScore() {
		// TODO Auto-generated method stub
		return (activePlayer == player1 ? player2Score : player1Score);
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
		
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		// TODO Auto-generated method stub
		
	}


	public void setActivePlayer(int perspective) {
		// TODO Auto-generated method stub
		if (perspective == surface.RIGHT_SIDE) {
			activePlayer = player2;
		} else if (perspective == surface.LEFT_SIDE) {
			activePlayer = player1;
		} else {
			activePlayer = player1;
		}
	}

	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub
		
	}

	



}
