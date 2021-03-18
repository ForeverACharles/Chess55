import java.io.*;
import java.util.*;

public class chess
{
	public static void main(String[]args)
	{
		piece[][] disBoard = initBoard();

		Scanner scan = new Scanner(System.in);
		String userInput;	
		
		boolean gameEnd = false;
		char turn = 'w';
		
		//main game loop
		while(gameEnd == false)
		{
			printBoard(disBoard);
			
			if(turn == 'w')
			{
				System.out.print("White's move: ");
				userInput = scan.nextLine();
				System.out.println();
				
				
				//check if move is legal
				int move = doMove(disBoard, userInput, turn);
				if(move < 0)
				{
					break;
				}
				turn = 'b';
				
			}
			else //black's turn
			{
				System.out.print("Black's move: ");
				userInput = scan.nextLine();
				System.out.println();
			
				
				//check if move is legal
				int move = doMove(disBoard, userInput, turn);
				if(move < 0)
				{
					break;
				}
				
				turn = 'w';
				
			}
		}
		
		
	}
	
	public static piece[][] initBoard()
	{
		piece[][] disBoard = new piece[8][8];
		for(int n = 0; n < 8; n++)
		{
			for(int l = 0; l < 8; l++)
			{
				disBoard[n][l] = null;
			}
		}
		
		int location = 0;
		for(int l = 0; l < 8; l++)
		{
			disBoard[1][l] = new pawn('w','p', 1, l);
			disBoard[6][l] = new pawn('b', 'p', 6 , l);
		}
		
		disBoard[0][0] = new piece('w', 'R', 0, 0);
		disBoard[0][1] = new piece('w', 'N', 0, 1);
		disBoard[0][2] = new piece('w', 'B', 0, 2);
		disBoard[0][3] = new piece('w', 'Q', 0, 3);
		disBoard[0][4] = new piece('w', 'K', 0, 4);
		disBoard[0][5] = new piece('w', 'B', 0, 5);
		disBoard[0][6] = new piece('w', 'N', 0, 6);
		disBoard[0][7] = new piece('w', 'R', 0, 7);
		
		disBoard[7][0] = new piece('b', 'R', 7, 0);
		disBoard[7][1] = new piece('b', 'N', 7, 1);
		disBoard[7][2] = new piece('b', 'B', 7, 2);
		disBoard[7][3] = new piece('b', 'Q', 7, 3);
		disBoard[7][4] = new piece('b', 'K', 7, 4);
		disBoard[7][5] = new piece('b', 'B', 7, 5);
		disBoard[7][6] = new piece('b', 'N', 7, 6);
		disBoard[7][7] = new piece('b', 'R', 7, 7);
		disBoard[7][7] = new piece('b', 'R', 7, 7);
		
		return disBoard;
	}
	
	
	public static void printBoard(piece[][] disBoard)
	{
		for(int n = 7; n >= 0; n--)
		{
			for(int l = 0; l < 8 ; l++)
			{
				if(disBoard[n][l] == null)
				{
					String space = "   "; //white space
				
					if((n + l) % 2 == 0)
					{
						space = "## ";	  //black space
					}
					System.out.print(space);
				}
				else
				{
					System.out.print(disBoard[n][l] + " ");
				}
			}
			System.out.print(n + 1);
			System.out.println();
		}
		
		char letter = 'a';
		for(int l = 0; l < 8; l++)
		{
			System.out.print(" " + letter + " ");
			letter++;
		}
		System.out.println('\n');
	}
	
	
	public static int doMove(piece[][] disBoard, String userInput, char turn)
	{
		if(userInput.equals("resign"))	//return 0 for resign
		{
			return 0;
		}
		
		String[] tokens;
		tokens = userInput.split(" ", 3);
		
		if(tokens.length < 2)
		{
			return -1;
		}
		
		if(tokens[0].length() > 2 || tokens[1].length() > 2)
		{
			return -1;
		}
		
		piece thisPiece = getPiece(disBoard, tokens[0]);
		
		if(thisPiece == null)
		{
			return -1;
		}
		if(thisPiece.getColor() != turn)
		{
			return -1;
		}
		
		//System.out.println(thisPiece);
		
		
		piece thatPiece = getPiece(disBoard, tokens[1]);
		
		if(thatPiece != null)
		{
			if(thatPiece.getColor() == thisPiece.getColor())	//check if trying to replace piece belonging to you
			{
				return -1;
			}
		}
		
		char L = tokens[0].charAt(0);
		char N = tokens[0].charAt(1);
		int thisN = N - 48 - 1;
		int thisL = L - 'a';
		
		disBoard[thisN][thisL] = null;
		
		L = tokens[1].charAt(0);
		N = tokens[1].charAt(1);
		int thatN = N - 48 - 1;
		int thatL = L - 'a';
		
		if(thatN < 0 || thatN > 7 || thatL < 0 || thatL > 7)
		{
			return -1;
		}
		
		if(checkMove(disBoard, thisPiece, tokens[0], thatPiece, tokens[1]) == false)
		{
			return -1;
		}
		
		disBoard[thatN][thatL] = thisPiece;
	
		return 1;
	}
		
	public static boolean checkInput(String userInput)
	{
		return true;
	}
	
	public static piece getPiece(piece[][] disBoard, String location)
	{
		char L = location.charAt(0);
		char N = location.charAt(1);	//e3
		
		
		int numN = N - 48 - 1;
		int numL = L - 'a';
		
		//System.out.println(numN + " " + numL);
		
		if(numN < 0 || numN > 7 || numL < 0 || numL > 7)
		{
			return null;
		}
		
		return disBoard[numN][numL];
	}
	
	public static boolean checkMove(piece[][] disBoard, piece thisPiece, String thisLoc, piece thatPiece, String thatLoc)
	{
		char L = thisLoc.charAt(0);
		char N = thisLoc.charAt(1);
		int thisN = N - 48 - 1;
		int thisL = L - 'a';
		
		L = thatLoc.charAt(0);
		N = thatLoc.charAt(1);
		int thatN = N - 48 - 1;
		int thatL = L - 'a';
		
		int moveN = thatN - thisN;
		int moveL = thatL - thisL;
		
		if(thisPiece.getName() == 'P')
		{
			if(thisPiece.getColor() == 'w')
			{
				if(moveN == 2 && moveL == 0 && thisN == 1)	//move pawn 2 spaces forward from start
				{
					return true;
				}
				
				if(moveN == 1 && moveL == 0 && thatPiece == null)	//move pawn 1 space forward
				{
					return true;
				}
				
				if(moveN == 1 && moveL == 1 && thatPiece != null)	//pawn captures 
				{
					return true;
				}
				
				
				return false;
				
			}
			if(thisPiece.getColor() == 'b')
			{
				if(moveN == 2 && moveL == 0)
				{
					if(thisN == 1)
					{
						return true;
					}
					return false;
				}
				if(moveN == 1 && moveL == 0)
				{
					if(thatPiece == null)
					{
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}
	
	
}