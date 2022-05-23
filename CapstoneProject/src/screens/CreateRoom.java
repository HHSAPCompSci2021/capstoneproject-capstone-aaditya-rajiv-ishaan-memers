/** The screen that players see before they start the game
  @author Ishaan Singh
  @version 3
*/package screens;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import core.DrawingSurface;
import g4p_controls.*;
import networking.backend.PeerDiscovery;
import networking.backend.SchoolClient;
import networking.backend.SchoolServer;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;

public class CreateRoom extends Screen {
	
	private DrawingSurface surface;
	
	private GLabel nameLabel;
	private GTextField nameField;
	
	private GLabel sliderLabel;
	private GCustomSlider slider;
	
	private GButton createButton;
	
	private static final int TCP_PORT = 4444;

	private InetAddress myIP;
	private SchoolServer ss;
	private SchoolClient sc;
	
	private String programID;
	private NetworkListener clientProgram;

	private String opponentUsername;

	private boolean disabled = false;
		


	/** Constructs the GameRoom screen
	 * 
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public CreateRoom(DrawingSurface surface) {
		super(1600, 1200);
		this.surface = surface;
		this.clientProgram = surface;
		this.programID = "APCS-Capstone-PaintBattle";
	}
	
	
	public void setup() {
		System.out.println("IN SETUP");
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
		if (!disabled) {
			System.out.println("ENABLING BUTTONS");
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
		} else {
			nameLabel.setEnabled(false);
			nameLabel.setVisible(false);
			nameField.setEnabled(false);
			nameField.setVisible(true);
			sliderLabel.setEnabled(false);
			sliderLabel.setVisible(false);
			slider.setEnabled(false);
			slider.setVisible(true);
			createButton.setEnabled(false);
			createButton.setVisible(false);
		}
		
	}

	
	public void handleButtonEvents(GButton button, GEvent event) {
		// Create the control window?
		System.out.println("event running");
		if (button == createButton && event == GEvent.CLICKED && nameField.getText().length() > 0 && !nameField.getText().contains(" ")) {			
			System.out.println("button clicked");
			try {
				myIP = InetAddress.getLocalHost();
				System.out.println("Your Hostname/IP address is " + myIP);
			} catch (UnknownHostException e) {
				e.printStackTrace ();
				System.out.println("Error getting your IP address!");
			}
			ss = new SchoolServer(programID, myIP);
			ss.setMaxConnections(2);
			ss.waitForConnections(TCP_PORT);
			System.out.println("\nTCP server running on " + TCP_PORT);
			if (DrawingSurface.discover != null)
				DrawingSurface.discover.setDiscoverable(true);
			connect(myIP);
			while (ss.getConnectedHosts().length != 2) {
//				System.out.println("FINDING OPPONENT");
				continue;
			}
//			sc.sendMessage(NetworkDataObject.MESSAGE, "USERNAME", nameField.getText());
//			while (opponentUsername == null) {
//				System.out.println("GETTING USERNAME");
//				continue;
//			}
			surface.setPerspective(surface.LEFT_SIDE);
			surface.setPlayerUsername(nameField.getText());
			surface.setOpponentUsername(opponentUsername);
			disabled = true;
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			System.out.println("CALLING SETUP");
			this.setup();

		
			
		} 
		
	}
	
	private class NetworkMessageHandler implements NetworkListener {

		@Override
		public void networkMessageReceived(NetworkDataObject ndo) {
			
			
			if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				System.out.println("\nClient list updated.");
//				connectedList.setListData(Arrays.copyOf(ndo.message, ndo.message.length, InetAddress[].class));
				System.out.println(Arrays.copyOf(ndo.message, ndo.message.length, InetAddress[].class));
			} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
				System.out.println("\nDisconnected from " + ndo.dataSource);
			} else if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0] == "USERNAME") {
					opponentUsername = (String) ndo.message[1];
				}
			}

		}

		@Override
		public void connectedToServer(NetworkMessenger nm) {
			// TODO Auto-generated method stub
		}
	}
	
	private void disconnect() {
		if (sc != null) {
			sc.disconnect();
			sc = null;
		}
	}
	
	
	
	private void connect(InetAddress host) {
		if (host != null) {
			disconnect();
			sc = new SchoolClient(programID, myIP);
			boolean success = sc.connect(host,TCP_PORT);
			if (!success) {
				System.out.println("\nCould not connect to "+host+" on " + TCP_PORT);
				sc.disconnect();
				sc = null;
			} else {
				System.out.println("\nConnected to "+host+" on " + TCP_PORT);
				sc.addNetworkListener(clientProgram);
				sc.addNetworkListener(new NetworkMessageHandler());
				clientProgram.connectedToServer(sc);
			}
		}
	}



	

	
	
	
	
}
