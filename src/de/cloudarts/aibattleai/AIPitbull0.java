package de.cloudarts.aibattleai;

import java.util.Random;

/**
 * spams random actions between a and g
 * @author wojciech andreas jurczyk
 *
 */
public class AIPitbull0 implements IAIProfile {

	@Override
	public String getProfileName() {
		return "Pitbull_0";
	}

	@Override
	public String getNextAction(int[] grid_, int _playerNumber) {
		return computeRandomAction();
	}
	
	protected String computeRandomAction()
	{
		String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
		Random generator = new Random();
		int actionIndex = generator.nextInt(7);
		String action = letters[actionIndex];
		
		return action;
	}

}
