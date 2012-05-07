package de.cloudarts.aibattleai;

public class AIPitbull1 extends AIPitbull0 implements IAIProfile {

	@Override
	public String getProfileName() {
		return "Pitbull Adam";
	}


	@Override
	public String getNextAction(int[] grid, int _playerNumber) {
		
		String ret = computeRandomAction();	// as fallback, make a random move
		
		//now try to come up with something more clever
		
		//find longest row of your pieces
		int longestRowNumPieces = 0;
		int longestRowEndingPiece = -1;
		int longestRowDirection = -1;
		for( int i = 0; i < grid.length; i++ )
		{
			if( grid[i] == _playerNumber )
			{
				
			}
		}
		if( longestRowNumPieces > 0 )
		{
			//try to set a stone next to this one in the same direction
		}
		
		return ret;
	}
}
