package de.cloudarts.aibattleai;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AIContainer {
	
	private static final long SLEEP_MILLIS = 200;
        
        private static final int GRID_COLUMNS = 7;
        private static final int GRID_ROWS = 6;
	
	private String _playerName = "Wojtek";
	private int _matchID = 0;
	private String _token = "";
	
	public AIContainer(String playerName_)
	{
		if( !playerName_.isEmpty() )
		{
			_playerName = playerName_;	
		}		
	}
	
	public void start()
	{		
		if( !requestNewMatch() )
		{
			return;
		}
		
		// actual game loop
		while(true)
		{
			//get game status
			String lastGameStatusAnswerString = requestMatchStatus();
			
			if( isErrorAnswer(lastGameStatusAnswerString) )
			{
				return;
			}
			
			if( isDrawGame(lastGameStatusAnswerString) )
			{
				return;
			}
			
			if( isGameWon(lastGameStatusAnswerString) )
			{
				return;
			}
			
			if( !isItMyTurn(lastGameStatusAnswerString) )
			{
				//wait a sec, then try again
				try 
				{
					Thread.sleep(SLEEP_MILLIS);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				continue;
			}
			
			//so it's my turn
			
			// get game status
			
			// compute next action
			String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
			Random generator = new Random();
			int actionIndex = generator.nextInt(7);
			String action = letters[actionIndex];
			
			// send next action
			System.out.println("sending action: " + action);
			postAction(action);
			
			// continue in loop
		}
	}

	private Boolean requestNewMatch()
	{
		URL u = URLCreator.getRequestMatchURL(_playerName);
		if (u == null )
		{
			System.err.println("Error! could not create request url!");
			return false;
		}
		
	    String r = "";
	    
		try 
		{
			r = new Scanner( u.openStream() ).useDelimiter( "\\Z" ).next();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		
	    System.out.println( "requestNewMatch: received answer:" + r );
	    
	    if( isErrorAnswer(r) )
	    {
	    	return false;
	    }
	    
	    // split answer string in matchID and secret token
	    String[] temp = r.split(";");
	    
	    if( temp.length != 2 )
	    {
	    	System.err.println("requestNewMatch: did expect 2 answer items, but received " + temp.length);
	    	return false;
	    }
	    
	    _matchID = Integer.valueOf(temp[0]);
	    _token = temp[1];
	    
	    System.out.println("requestNewMatch: received new matchID " + _matchID + " and token " + _token);
	    
	    return true;
	}
	
	private String requestMatchStatus()
	{
		URL u = URLCreator.getRequestMatchStatusURL(_matchID);
		if( u == null )
		{
			return "error";
		}
		
		String r = "";
	    
		try 
		{
			r = new Scanner( u.openStream() ).useDelimiter( "\\Z" ).next();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "error";
		}
		
	    System.out.println( "requestMatchStatus: received answer:" + r );
            
            visualizeGrid(r);
	    
	    return r;		
	}

	
	private String postAction(String action_)
	{
		URL u = URLCreator.getPostActionURL(_matchID, _playerName, _token, action_);
		if( u == null )
		{
			return "error";
		}
		
		String r = "";
	    
		try 
		{
			r = new Scanner( u.openStream() ).useDelimiter( "\\Z" ).next();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "error";
		}
		
	    System.out.println( "postAction: received answer:" + r );
		return r;
	}
	
	private Boolean isItMyTurn(String rawStatusString_)
	{
		String currentTurnPlayer = rawStatusString_.substring(0, _playerName.length());
		if( currentTurnPlayer.equals(_playerName) )
		{
			return true;
		}
		
		return false;
	}
	
	private Boolean isErrorAnswer(String answer_)
	{
		if( answer_.substring(0, "error".length()).equals("error") )
		{
			return true;
		}
		
		return false;
	}

	private boolean isDrawGame(String answer_) {
		if( answer_.substring(0, "draw game".length()).equals("draw game") )
		{
			return true;
		}
		
		return false;
	}

	private boolean isGameWon(String answer_) {
            String[] items = answer_.split(";");
            String status = items[0];
            
            String last3Letters = status.substring(status.length()-3, status.length());
            
            if( last3Letters.equals("won") )
            {
                    return true;
            }
		
            return false;
	}

    private void visualizeGrid(String r) {
        // create GridArray
        
        String[] items = r.split(";");
        
        //first item is match status, second item is player names
        //parse only actions
        ArrayList<String> actions = new ArrayList<>();
        for( int i = 2; i < items.length; i++ )
        {
            String item = items[i];
            String[] subitems = item.split(":");
            String player = subitems[0];
            String action = subitems[1];    //first subitem is player number
            actions.add(action);
        }
        
        //create gridArray from actions
        String[] gridArray = new String[GRID_COLUMNS * GRID_ROWS];
        for( int i = 0; i < gridArray.length; i++ )
        {
            gridArray[i] = "-"; //initialize empty fields with "-"
        }
        
        //fill gridArray with actions
        int playerIndex = 0;
        for( int i = 0; i < actions.size(); i++ )
        {
            int col = actionToColumn(actions.get(i));
            int row = GRID_ROWS-1;      //start at the bottom
            //work your way up until you hit an empty space
            int gridIndex = coordsToIndex(col, row);
            
            try
            {
                while( !gridArray[gridIndex].equalsIgnoreCase("-") )
                {
                    row--;
                    gridIndex = coordsToIndex(col, row);
                }
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {
                System.err.println("Impossible action found: " + ex);
                return;
            }
            
            String player1Symbol = "X";
            String player2Symbol = "0";
            
            gridArray[gridIndex] = playerIndex == 0 ? player1Symbol : player2Symbol;
            playerIndex++;
            if( playerIndex > 1 )
            {
                playerIndex = 0;
            }
        }
        
        //print GridArray
        int col = 0;
        for( int i = 0; i < gridArray.length; i++ )
        {
            System.out.print("" + gridArray[i]);
            col++;
            if( col >= GRID_COLUMNS )
            {
                System.out.print("\n");
                col = 0;
            }
        }
        System.out.print("\n\n");
    }
    
    private int actionToColumn(String action)
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
    
    private Point indexToCoords(int index)
    {
        int x = index % GRID_COLUMNS;
        int y = index / GRID_COLUMNS;
        
        return new Point(x, y);
    }
    
    private int coordsToIndex(int x, int y)
    {
        return x + (y * GRID_COLUMNS);
    }
}
