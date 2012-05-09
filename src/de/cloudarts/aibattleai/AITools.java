package de.cloudarts.aibattleai;

import java.awt.Point;

public class AITools {
	
	public static final long SLEEP_MILLIS = 200;    
    public static final int GRID_COLUMNS = 7;
    public static final int GRID_ROWS = 6;
    
    static public int actionToColumn(String action)
    {
        if( action.equalsIgnoreCase("a") )
        {
            return 0;
        }
        else if( action.equalsIgnoreCase("b") )
        {
            return 1;
        }
        else if( action.equalsIgnoreCase("c") )
        {
            return 2;
        }
        else if( action.equalsIgnoreCase("d") )
        {
            return 3;
        }
        else if( action.equalsIgnoreCase("e") )
        {
            return 4;
        }
        else if( action.equalsIgnoreCase("f") )
        {
            return 5;
        }
        else if( action.equalsIgnoreCase("g") )
        {
            return 6;
        }
        
        return -1;
    }
    
    static public String columnToAction(int column)
    {
        
    	switch (column) {
		case 0:
			return "a";
		case 1:
			return "b";
		case 2:
			return "c";
		case 3:
			return "d";
		case 4:
			return "e";
		case 5:
			return "f";
		case 6:
			return "g";

		default:
			return "x";
		}
    }
    
    static public Point indexToCoords(int index)
    {
        int x = index % GRID_COLUMNS;
        int y = index / GRID_COLUMNS;
        
        return new Point(x, y);
    }
    
    static public int coordsToIndex(int x, int y)
    {
        return x + (y * GRID_COLUMNS);
    }
    
    /**
     * 
     * @param gridIndex_
     * @param direction_ 0 up, 2 right, 4 down, 6 left
     * @return
     */
    static public int getNextGridIndexInDirection(int gridIndex_, int direction_)
    {
    	Point coords = indexToCoords(gridIndex_);
        
        //apply $direction to coordinates
        if( direction_ >= 5 )
        {
        	coords.x -= 1;
        }
        else if( direction_ >= 1 && direction_ <= 3)
        {
        	coords.x += 1;
        }
        
        if( direction_ == 7 || direction_ == 0 || direction_ == 1 )    //7, 0, 1 are all upish
        {
        	coords.y -= 1;
        }
        else if( direction_ == 3 || direction_ == 4 || direction_ == 5 )    //3, 4, 5 are all downish
        {
        	coords.y += 1;
        }
        
        //check for illegal moves
        if( coords.x < 0 || coords.x >= GRID_COLUMNS || coords.y < 0 || coords.y >= GRID_ROWS )
        {
            return -1;
        }
        
        return coordsToIndex(coords.x, coords.y);
    }
    
    /**
     * WARNING: may return negative values!
     *  high performance version of getNextGridIndexInDirection for fixed direction: up
     */
    public static int getGridIndexOnTopOf(int gridIndex_)
    {
    	return gridIndex_ - GRID_COLUMNS;

    }
    
    public static int getGameStatus(int[] grid_)
    {
    	int countPieces = 0;
    	
    	//check for win first
    	for( int pieceIndex = 0; pieceIndex < GRID_ROWS * GRID_COLUMNS; pieceIndex++ )
    	{
    		int pieceNumber = grid_[pieceIndex];
    		if( pieceNumber == 0 )
    		{
    			continue;
    		}
    		
    		countPieces++;
    		
    		//check in all directions
    		for( int direction = 0; direction < 8; direction++ )
    		{
    			int piecesInARow = 1;
    			piecesInARow += checkPiecesInARow(pieceNumber, pieceIndex, direction, grid_);
    			if( piecesInARow == 4 )
    			{
    				return pieceNumber;
    			}
    		}
    	}
    	
    	// no-one won. maybe draw game?
    	if( countPieces == GRID_ROWS * GRID_COLUMNS )
    	{
    		return 0;
    	}
    	
    	//ongoing game
    	return -1;
    }

	private static int checkPiecesInARow(int checkNumber, int pieceIndex, int direction, int[] grid_) {
		
		int ret = 0;
		int nextPieceIndex = getNextGridIndexInDirection(pieceIndex, direction);
		
		if( nextPieceIndex != -1 && grid_[nextPieceIndex] == checkNumber )
		{
			ret = 1;
			//check next piece recursively
			ret += checkPiecesInARow(checkNumber, nextPieceIndex, direction, grid_);
		}
		
		return ret;
	}
	
	public static void visualizeGrid(int[] grid_) {        
        
        //print GridArray
        int col = 0;
        for( int i = 0; i < grid_.length; i++ )
        {
        	String printChar = "-";
        	if( grid_[i] == 1 )
        	{
        		printChar = "X";
        	}
        	else if( grid_[i] == 2 )
        	{
        		printChar = "0";
        	}
        		
            System.out.print(printChar);
            col++;
            if( col >= AITools.GRID_COLUMNS )
            {
                System.out.print("\n");
                col = 0;
            }
        }
        System.out.print("\n");
    }
}
