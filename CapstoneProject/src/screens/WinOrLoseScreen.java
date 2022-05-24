package screens;
/**
 * @author Ishaan Singh
 */

import core.DrawingSurface;
import g4p_controls.GButton;
import g4p_controls.GEvent;

public class WinOrLoseScreen extends Screen {

	private DrawingSurface surface;
	private Game game;
	private int opponentScore;
	private int playerScore;
	
	public WinOrLoseScreen(DrawingSurface drawingSurface) {
		super(1600, 1200);
		surface = drawingSurface;
	}
	
	public void draw() {
		
		if (playerScore > opponentScore) {
			surface.image(surface.loadImage("img/victory.png"), 0, 0);
		} else if (opponentScore > playerScore) {
			surface.image(surface.loadImage("img/loseScreen.png"), 0, 0);
		}
		else 
			surface.image(surface.loadImage("img/tieScreen.png"), 0, 0);
	}

	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub

	}

	public void setPlayerScore(int activePlayerScore) {
		// TODO Auto-generated method stub
		this.playerScore = activePlayerScore;
		
	}

	public void setOpponentScore(int opponentScore) {
		// TODO Auto-generated method stub
		this.opponentScore = opponentScore;
		
	}

}
