/** The screen that players see before they start the game
  @author Ishaan Singh
  @version 3
*/package screens;

import g4p_controls.*;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;

public class CreateRoom extends Screen {
	
	private PApplet surface;
	
	private GLabel nameLabel;
	private GTextField nameField;
	
	private GLabel sliderLabel;
	private GCustomSlider slider;
	
	private GButton createButton;


	/** Constructs the GameRoom screen
	 * 
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public CreateRoom(PApplet surface) {
		super(1600, 1200);
		this.surface = surface;
	}
	
	
	public void setup() {
		nameLabel = new GLabel(surface, 300, 125, 200, 25);
		nameLabel.setAlpha(190);
		nameLabel.setTextAlign(GAlign.CENTER, null);
		nameLabel.setOpaque(true);
		nameLabel.setText("Enter Username!");
		nameLabel.setEnabled(false);
		nameLabel.setVisible(false);
		
		nameField = new GTextField(surface, 300, 175, 200, 50);
		nameField.setPromptText("Enter Username...");
		nameField.setEnabled(false);
		nameField.setVisible(false);
		
		sliderLabel = new GLabel(surface, 300, 275, 200, 25);
		sliderLabel.setAlpha(190);
		sliderLabel.setTextAlign(GAlign.CENTER, null);
		sliderLabel.setOpaque(true);
		sliderLabel.setText("Enter Time Limit!");
		sliderLabel.setEnabled(false);
		sliderLabel.setVisible(false);
		
		slider = new GCustomSlider(surface, 250, 300, 300, 50, null);
		// show          opaque  ticks value limits
		slider.setShowDecor(false, true, false, false);
		slider.setNumberFormat(GCustomSlider.DECIMAL, 2);
		slider.setLimits(0.5f, 0.5f, 10.0f);
		slider.setNbrTicks(20);
		slider.setStickToTicks(true);  //false by default 		// show          opaque  ticks value limits
		slider.setEnabled(false);
		slider.setVisible(false);
		
		createButton = new GButton(surface, 300, 400, 200, 100, "Create Room");
		createButton.setEnabled(false);
		createButton.setVisible(false);
	}
	public void draw() {
		surface.background(255,100,0);
		nameLabel.setEnabled(true);
		nameLabel.setVisible(true);
		nameField.setEnabled(true);
		nameField.setVisible(true);
		sliderLabel.setEnabled(true);
		sliderLabel.setVisible(true);
		slider.setEnabled(true);
		slider.setVisible(true);
		createButton.setEnabled(true);
		createButton.setVisible(true);
	}

	
	public void handleButtonEvents(GButton button, GEvent event) {
		// Create the control window?
		if (button == createButton && event == GEvent.CLICKED && nameField.getText().length() > 0) {
			
		} 
		
	}
	
	
	
	
}
