/**
 * 
 */
package de.cloudarts.aibattleai;

import java.util.Random;

/**
 * @author W. A. Jurczyk
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String playerName = "Wojtek"+(new Random().nextInt(100));
		
		if (args.length > 0)
		{
			playerName = args[0];
		}
		
		AIContainer ai = new AIContainer(playerName);
		ai.start();
	}

}
