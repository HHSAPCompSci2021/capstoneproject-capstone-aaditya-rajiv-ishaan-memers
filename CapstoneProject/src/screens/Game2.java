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
 * Represents the interactive game screen. WITH NETWORKING
 *
 * @author Ishaan Singh and Aaditya Raj
 *
 */
public class Game2 extends Screen implements NetworkListener {

	private Avatar player1, player2;
	private Avatar activePlayer;
	private ArrayList<Platform> platforms;
	private ArrayList<Platform> boundaries;
	public static Flag flag;
	private ArrayList<PaintBomb> bombs;
	public DrawingSurface surface;
	private ArrayList<PaintBlock> bullets;
	private int player1Score, player2Score;

	private boolean flagTaken;
	
	private static final String messageTypeMove = "PLAYER_MOVEMENT";
	private static final String messageTypeShoot = "SHOOT_BULLET";
	private static final String messageTypeJump = "PLAYER_JUMP";
	
	/**
	 * Constructs a screen representing the interactive game screen.
	 * @param width The width of the screen.
	 * @param 0 The 0 of the screen.
	 */
	public Game2(DrawingSurface s) {
		super(1600, 1200);
		surface = s;
	}

	public void setup() {
		String pre = surface.sketchPath();
		player1 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), 100, 840, 200, 200, Color.RED);

		player2 = new Avatar(surface.loadImage(pre + "/" + "img/character.png"), 1300, 840, 200, 200, Color.BLUE);

		platforms = new ArrayList<Platform>();
		bullets = new ArrayList<PaintBlock>();
		boundaries = new ArrayList<Platform>();
		
		platforms.add(new Platform(650, 225, 250, 50));
		
		platforms.add(new Platform(1175, 325, 200, 50));

		platforms.add(new Platform(225, 325, 200, 50));
		
		platforms.add(new Platform(500, 500, 150 , 50));
		
		platforms.add(new Platform(900, 500, 150 , 50));

		
		platforms.add(new Platform(0, 600, 300, 50));
		
		platforms.add(new Platform(600, 800, 350, 50));
		
		platforms.add(new Platform(1300, 600, 300, 50));

		
		
		boundaries.add(new Platform(0, 0, 1600, 1));
		boundaries.add(new Platform (0,1040,1600,1));
		boundaries.add(new Platform (0, 0, 1, 1200));
		boundaries.add(new Platform (1599, 0, 1, 1200));
	
		flag = new Flag(730, 50, 90, 100);
		bombs = new ArrayList<PaintBomb>();
		bombs.add(player1.getBomb());
		bombs.add(player2.getBomb());
		
		flagTaken = false;
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
				System.out.println("BOOSTED");
				player1.boost();
			} else if (player1.isBoosted()) {
				System.out.println("UNBOOSTED");
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
				if(player1.loseHealth(player2) && !player1.hasFlag()) {
					flagTaken = false;
				}
				
				toRemove.add(b);
			} 
			
			if(b.intersects(player2)) {
				if(player2.loseHealth(player1) && !player2.hasFlag()) {
					flagTaken = false;
				}
				
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
		player2Score += (player2.getCaptures() * 200);
		 
		player1Score -= (player1.getNumDeaths() * 200);
		
		player2Score -= (player2.getNumDeaths() * 200);
		
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
					player.moveToLocation((int) ndo.message[1], (int) ndo.message[2]);
				} else if (ndo.message[0].equals(messageTypeShoot)) {
					PaintBlock bullet = player.shoot(new Point2D.Double((int) ndo.message[1], (int) ndo.message[2]));
					if (bullet != null) {
						bullets.add(bullet);
					}
				} else if (ndo.message[0].equals(messageTypeJump)) {
					player.jump();
				}  else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
						this.setup();
						surface.switchScreen(ScreenSwitcher.WIN_SCREEN);
						
				}
			}


			

		}
	}

	public void mousePressed(int mouseX, int mouseY) {
		PaintBlock bullet = activePlayer.shoot(new Point2D.Double(mouseX, mouseY));
		if (bullet != null) {
			bullets.add(bullet);
			nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {messageTypeShoot, mouseX, mouseY});
		}
	}
	
	public int getPlayer1Score() {
		return player1Score;
	}
	
	public int getPlayer2Score() {
		return player2Score;
	}
	
	public int getActivePlayerScore() {
		return (activePlayer == player1 ? player1Score : player2Score);
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void setPerspective(int perspective) {
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




}