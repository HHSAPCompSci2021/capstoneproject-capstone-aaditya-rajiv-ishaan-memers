/** The screen that players see before they start the game
  @author Ishaan Singh
  @version 3
*/package screens;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import core.DrawingSurface;
import g4p_controls.*;
import networking.backend.PlayerClient;
import networking.backend.GameServer;
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
	private GameServer ss;
	private PlayerClient sc;
	private GGroup group;
	boolean isActive;
	
	private String programID;
	private NetworkListener clientProgram;

	private String opponentUsername;

		


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
		isActive = true;
	}
	
	
	
	public void setup() {
		nameLabel = new GLabel(surface, 300, 125, 200, 25);
		nameLabel.setAlpha(190);
		nameLabel.setTextAlign(GAlign.CENTER, null);
		nameLabel.setOpaque(true);
		nameLabel.setText("Enter Username!");
		
		nameField = new GTextField(surface, 300, 175, 200, 50);
		nameField.setPromptText("Enter Username...");
		
		sliderLabel = new GLabel(surface, 300, 275, 200, 25);
		sliderLabel.setAlpha(190);
		sliderLabel.setTextAlign(GAlign.CENTER, null);
		sliderLabel.setOpaque(true);
		sliderLabel.setText("Enter Time Limit!");
		
		slider = new GCustomSlider(surface, 250, 300, 300, 50, null);
		// show          opaque  ticks value limits
		slider.setShowDecor(false, true, false, false);
		slider.setNumberFormat(GCustomSlider.DECIMAL, 2);
		slider.setLimits(0.5f, 0.5f, 10.0f);
		slider.setNbrTicks(20);
		slider.setStickToTicks(true);  //false by default 		// show          opaque  ticks value limits
		
		createButton = new GButton(surface, 300, 400, 200, 100, "Create Room");
		
		group = new GGroup(surface);
		group.addControl(nameLabel);
		group.addControl(nameField);
		group.addControl(sliderLabel);
		group.addControl(slider);
		group.addControl(createButton);
		
		group.fadeOut(0, 0);
	}
	
	public void draw() {
		surface.background(255,100,0);
		if(isActive) {
			group.fadeIn(0, 0);
			group.setEnabled(true);
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
			ss = new GameServer(programID, myIP);
			ss.setMaxConnections(2);
			ss.waitForConnections(TCP_PORT);
			System.out.println("\nTCP server running on " + TCP_PORT);
			if (DrawingSurface.discover != null) {
				DrawingSurface.discover.setDiscoverable(true);
			}
			
			connect(myIP);
			while (ss.getConnectedHosts().length != 2) {
//				System.out.println("FINDING OPPONENT");
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				continue;
			}
//			sc.sendMessage(NetworkDataObject.MESSAGE, "USERNAME", nameField.getText());
			surface.setPerspective(surface.LEFT_SIDE);
			surface.setPlayerUsername(nameField.getText());
			surface.setOpponentUsername(opponentUsername);
			
			isActive = false;
			group.fadeOut(0, 0);
			group.setEnabled(false);
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
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
			sc = new PlayerClient(programID, myIP);
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
