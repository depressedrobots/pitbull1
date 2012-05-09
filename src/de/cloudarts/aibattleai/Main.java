/**
 * 
 */
package de.cloudarts.aibattleai;

/**
 * @author W. A. Jurczyk
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//load all profiles
		IAIProfile[] profiles = { new AIPitbull0(), new AIPitbull1() };		
		
		int profileIndex = 1;
		
		if (args.length > 0)
		{
			profileIndex = Integer.valueOf(args[0]);
			if( profileIndex < 0 || profileIndex >= profiles.length )
			{
				profileIndex = 0;
			}
		}
		
		System.out.println("starting with "+ profiles[profileIndex].getProfileName());
		
		AIContainer ai = new AIContainer(profiles[profileIndex]);
		ai.start();
	}

}
