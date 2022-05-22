package screens;

import core.DrawingSurface;
import g4p_controls.GButton;
import g4p_controls.GEvent;

public class WinScreen extends Screen {

	private DrawingSurface surface;
	private Game game;
	private int opponentScore;
	private int playerScore;
	
	public WinScreen(DrawingSurface drawingSurface) {
		super(1600, 1200);
		surface = drawingSurface;
	}
	
	public void draw() {
		
		surface.image(surface.loadImage("img/victory.png"), 1, 0);
		if (playerScore > opponentScore) {
			surface.text(surface.playerUsername + " won the game", DRAWING_WIDTH/2, DRAWING_HEIGHT/2);
		} else if (opponentScore > playerScore) {
			surface.text(surface.opponentUsername + " won the game", DRAWING_WIDTH/2, DRAWING_HEIGHT/2);
		}
		else 
			surface.text("It's a tie game", DRAWING_WIDTH/2, DRAWING_HEIGHT/2);
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
