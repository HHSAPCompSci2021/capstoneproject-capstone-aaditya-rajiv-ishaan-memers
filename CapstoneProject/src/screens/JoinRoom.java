package screens;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.validator.routines.InetAddressValidator;

import core.DrawingSurface;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GGroup;
import g4p_controls.GLabel;
import g4p_controls.GTextField;
import networking.backend.PlayerClient;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;

public class JoinRoom extends Screen {
	
	private DrawingSurface surface;
	
	private GLabel nameLabel;
	private GTextField nameField;
	
	private GLabel hostLabel;
	private GTextField hostField;
	
	private GButton discoverButton;
	
	private GButton joinButton;
	
	private static final int TCP_PORT = 4444;

	private static final long DISCOVER_TIMEOUT = 10000;

	private InetAddress myIP;
	private PlayerClient sc;
	private GGroup group;
	boolean isActive;
	
	private String opponentUsername = "";

	
	private String programID;
	private NetworkListener clientProgram;

	private Timer refreshTimer;

	private boolean disabled;



	public JoinRoom(DrawingSurface surface) {
		super(1600, 1200);
		this.surface = surface;
		this.clientProgram = surface;
		this.programID = "APCS-Capstone-PaintBattle";
		refreshTimer = new Timer();
		isActive = true;
	}
	
	public void setup() {
		nameLabel = new GLabel(surface, 300, 75, 200, 25);
		nameLabel.setAlpha(190);
		nameLabel.setTextAlign(GAlign.CENTER, null);
		nameLabel.setOpaque(true);
		nameLabel.setText("Enter Username!");
		nameLabel.setEnabled(false);
		nameLabel.setVisible(false);
		
		nameField = new GTextField(surface, 300, 125, 200, 50);
		nameField.setPromptText("Enter Username...");
		nameField.setEnabled(false);
		nameField.setVisible(false);
		
		hostLabel = new GLabel(surface, 300, 225, 200, 25);
		hostLabel.setAlpha(190);
		hostLabel.setTextAlign(GAlign.CENTER, null);
		hostLabel.setOpaque(true);
		hostLabel.setText("Enter IP Address of Host!");
		hostLabel.setEnabled(false);
		hostLabel.setVisible(false);
		
		hostField = new GTextField(surface, 300, 275, 200, 50);
		hostField.setPromptText("Enter IP Address...");
		hostField.setEnabled(false);
		hostField.setVisible(false);
		
		
		discoverButton = new GButton(surface, 300, 350, 200, 50, "Discover!");
		discoverButton.setEnabled(false);
		discoverButton.setVisible(false);
		
		joinButton = new GButton(surface, 300, 400, 200, 50, "Join!");
		joinButton.setEnabled(false);
		joinButton.setVisible(false);
		
		group = new GGroup(surface);
		group.addControl(nameLabel);
		group.addControl(nameField);
		group.addControl(hostLabel);
		group.addControl(hostField);
		group.addControl(discoverButton);
		group.addControl(joinButton);
		
		group.fadeOut(0, 0);
	}
	
	public void draw() {
		surface.image(surface.loadImage("img/background.png"), 1, 0);
		if (isActive) {
			group.fadeIn(0, 0);
			group.setEnabled(true);
			group.setVisible(true);
		}
		

	}


	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub
		
		System.out.println("event running");
		if (button == joinButton && event == GEvent.CLICKED && !nameField.getText().isEmpty() && !nameField.getText().contains(" ") && !hostField.getText().isEmpty() && !hostField.getText().contains(" ")) {			
			System.out.println(nameField.getText());
			System.out.println("button clicked");
			try {
				myIP = InetAddress.getLocalHost();
				System.out.println("Your Hostname/IP address is " + myIP);
			} catch (UnknownHostException e) {
				e.printStackTrace ();
				System.out.println("Error getting your IP address!");
			}
			
			connect(hostField.getText());
//			sc.sendMessage(NetworkDataObject.MESSAGE, "USERNAME", nameField.getText());
//			while (opponentUsername == null) {
//				continue;
//			}
			surface.setPerspective(surface.RIGHT_SIDE);
			surface.setPlayerUsername(nameField.getText());
			surface.setOpponentUsername(opponentUsername);
			disabled = true;
			
			isActive = false;
			group.fadeOut(0, 0);
			group.setEnabled(false);
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			

		
			
		} else if (button == discoverButton) {
			try {
				System.out.println("\nSending broadcast packet...");
				DrawingSurface.discover.sendDiscoveryPacket();
				refreshTimer.schedule(new ShowHosts(), DISCOVER_TIMEOUT);
			} catch (IOException e1) {
				System.out.println("\nError sending discovery packet.");
				e1.printStackTrace();
			} 
		}
		
	}
	
	private class ShowHosts extends TimerTask {
		public void run() {
			if (DrawingSurface.discover.getPeers().length > 0) {
				System.out.println(DrawingSurface.discover.getPeers()[0].getHostAddress());
			} else {
				System.out.println("Could not find hosts");			
			}
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
	
	private void connect(String host) {
		InetAddressValidator validator = InetAddressValidator.getInstance();
		if (validator.isValid(host)) {
			try {
				connect(InetAddress.getByName(host));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
				System.out.println("Invalid IP Address format.");
			}
		} else {
			System.out.println("Invalid IP Address format.");
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
