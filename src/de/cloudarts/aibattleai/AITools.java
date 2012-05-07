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
    static public int getNextPieceInDirection(int gridIndex_, int direction_)
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
}
