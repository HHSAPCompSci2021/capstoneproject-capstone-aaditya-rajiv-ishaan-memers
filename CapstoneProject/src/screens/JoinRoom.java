package screens;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.apache.commons.validator.routines.InetAddressValidator;

import core.DrawingSurface;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GTextField;
import networking.backend.PeerDiscovery;
import networking.backend.SchoolClient;
import networking.backend.SchoolServer;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;

public class JoinRoom extends Screen {
	
	private DrawingSurface surface;
	
	private GLabel nameLabel;
	private GTextField nameField;
	
	private GLabel hostLabel;
	private GTextField hostField;
	
	private GButton joinButton;
	
	private static final int TCP_PORT = 4444;

	private InetAddress myIP;
	private PeerDiscovery discover;
	private SchoolServer ss;
	private SchoolClient sc;
	
	private String opponentUsername;

	
	private String programID;
	private NetworkListener clientProgram;



	public JoinRoom(DrawingSurface surface) {
		super(1600, 1200);
		this.surface = surface;
		this.clientProgram = surface;
		this.programID = "APCS-Capstone-PaintBattle";
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
		
		hostLabel = new GLabel(surface, 300, 275, 200, 25);
		hostLabel.setAlpha(190);
		hostLabel.setTextAlign(GAlign.CENTER, null);
		hostLabel.setOpaque(true);
		hostLabel.setText("Enter IP Address of Host!");
		hostLabel.setEnabled(false);
		hostLabel.setVisible(false);
		
		hostField = new GTextField(surface, 300, 325, 200, 50);
		hostField.setPromptText("Enter IP Address...");
		hostField.setEnabled(false);
		hostField.setVisible(false);
		
		joinButton = new GButton(surface, 300, 400, 200, 100, "Join!");
		joinButton.setEnabled(false);
		joinButton.setVisible(false);	
	}
	
	public void draw() {
		surface.background(255,100,0);
		nameLabel.setEnabled(true);
		nameLabel.setVisible(true);
		nameField.setEnabled(true);
		nameField.setVisible(true);
		hostLabel.setEnabled(true);
		hostLabel.setVisible(true);
		hostField.setEnabled(true);
		hostField.setVisible(true);
		joinButton.setEnabled(true);
		joinButton.setVisible(true);

	}

	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub
		
		System.out.println("event running");
		if (button == joinButton && event == GEvent.CLICKED && !nameField.getText().isEmpty() && nameField.getText().contains(" ") && hostField.getText().length() > 0) {			
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
				sc.sendMessage(NetworkDataObject.MESSAGE, "USERNAME", nameField.getText());
				while (opponentUsername == null) {
					continue;
				}
				surface.setPerspective(surface.RIGHT_SIDE);
				surface.setPlayerUsername(nameField.getText());
				surface.setOpponentUsername(opponentUsername);
				surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			}
		}
	}

	

}
