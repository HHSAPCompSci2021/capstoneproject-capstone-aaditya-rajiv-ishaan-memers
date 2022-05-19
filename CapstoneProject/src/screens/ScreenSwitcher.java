package screens;


public interface ScreenSwitcher {
	public static final int MENU_SCREEN = 0;
	public static final int ROOM_SCREEN = 1;
	public static final int GAME_SCREEN = 2;
	
	/**
	 * Method that all screens should have for switching to a different screen. 
	 * @param i The index of the screen to switch to.
	 */
	public void switchScreen(int i);
}
