package de.cloudarts.aibattleai;

public interface IAIProfile {
	
	public String getProfileName();
	
	/**
	 * 
	 * @param grid_ an one-dimensional array as game grid
	 * @param _playerNumber either 1 or 2
	 * @return a string from "a" to "g"
	 */
	public String getNextAction(int[] grid, int playerNumber);
}
