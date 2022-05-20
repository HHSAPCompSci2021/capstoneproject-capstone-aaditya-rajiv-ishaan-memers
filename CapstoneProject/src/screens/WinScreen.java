package screens;

import core.DrawingSurface;
import g4p_controls.GButton;
import g4p_controls.GEvent;

public class WinScreen extends Screen {

	private DrawingSurface surface;
	private Game game;

	public WinScreen(DrawingSurface drawingSurface, Game game) {
		super(1600, 1200);
		surface = drawingSurface;
		this.game = game;
	}
	
	public void draw() {
		if (game.getPlayer1Score() > game.getPlayer2Score()) {
			surface.text("Player 1 won the game", DRAWING_WIDTH/2, DRAWING_HEIGHT/2);
		} else if (game.getPlayer2Score() > game.getPlayer1Score()) {
			surface.text("Player 2 won the game", DRAWING_WIDTH/2, DRAWING_HEIGHT/2);
		}
		else 
			surface.text("It's a tie game", DRAWING_WIDTH/2, DRAWING_HEIGHT/2);
	}

	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub

	}

}
