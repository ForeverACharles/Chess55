package chess;
/**
* Primary chess class which initiates games and executes game logic
*
* <code>chessPushed</code> contains the code and logic needed to run a game of chess, 
* including creating and printing the board,
* interpreting moves and turns, and following the rules of chess.
*
* @author Charles Li
* @author Max Sun
* @version 1.2 Mar 2021
*/
import java.io.*;
import java.util.*;
import java.lang.*;

public class Chess
{
	/**
	* The default 8x8 <code>piece</code> array that holds the official current position of our chess board
	*/	
	public static piece[][] disBoard;
	
	/**
	* <code>piece</code> object representing the eligible enPassant pawn, <code>null</code> if none 
	*/
	public static piece enPassantPawn;
	
	/**
	* <code>piece</code> object representing the white king, used to keep track of
	* its current location
	*/	
	public static piece whiteKing;
	
	/**
	* <code>piece</code> object representing the black king, used to keep track of
	* its current location
	*/	
	public static piece blackKing;
	
	/**
	* <code>Boolean</code> specifying whether a draw was offered on the previous turn
	*/
	public static boolean draw;
	
	/**
	* 
	* <code>Chess</code> main method.
	* Begins the game, calls for board initialization, gets user input, interprets turns, 
	* and controls when to end the game and terminate the program.
	*
	*/
	public static void main(String[]args)
	{
		disBoard = initBoard();
		
		Scanner scan = new Scanner(System.in);
		String userInput;	
		
		boolean gameEnd = false;
		char turn = 'w';
		
		//main game loop
		while(gameEnd == false)
		{
			int kN;
			int kL;
			printBoard(disBoard);
			if(turn == 'w')
			{	
				kN = whiteKing.getN();
				kL = whiteKing.getL();
				ArrayList<piece> checkPieces = inCheck(disBoard, 'w', kN, kL);
				if(checkPieces.size() > 0){
					if(isCheckMate(disBoard, checkPieces, 'w', kN, kL))
					{
						gameEnd = true;
						System.out.println("Checkmate\nBlack wins");
						break;
					}
					System.out.println("Check");
				}
				do
				{
					System.out.print("White's move: ");
					userInput = scan.nextLine();
					
					//check if move is legal
					int move = doMove(userInput, turn);
					if(move < 0)
					{
						System.out.println("Illegal move, try again");
					}
					else if(move == 0)
					{
						gameEnd = true;
						System.out.println("Black wins");
						break;
					}
					else if(move == 1)
					{
						turn = 'b';
						System.out.println();
						break;
					}
					else if(move == 2)
					{
						gameEnd = true;
						break;
					}
				} while(true);
			}
			
			else	//black's turn
			{
									
				kN = blackKing.getN();
				kL = blackKing.getL();
				ArrayList<piece> checkPieces = inCheck(disBoard, 'b', kN, kL);
				if(checkPieces.size() > 0){
					if(isCheckMate(disBoard, checkPieces, 'b', kN, kL))
					{
						gameEnd = true;
						System.out.println("Checkmate\nWhite wins");
						break;
					}
					System.out.println("Check");
				}
				do
				{
					System.out.print("Black's move: ");
					userInput = scan.nextLine();
					
					//check if move is legal
					int move = doMove(userInput, turn);
					if(move < 0)
					{
						System.out.println("Illegal move. Try again");
					}
					else if(move == 0)
					{
						gameEnd = true;
						System.out.println("White wins");
						break;
					}
					else if(move == 1)
					{
						turn = 'w';
						System.out.println();
						break;
					}
					else if(move == 2)
					{
						gameEnd = true;
						break;
					}
				} while(true);
			}
		}
	}
	/**
	* Creates a chess board at starting position
	*
	* @return an 8x8 <code>piece</code> array with 32 black and white <code>piece</code> objects in starting position
	*/
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
			disBoard[1][l] = new piece('w','p', 1, l);
			disBoard[6][l] = new piece('b', 'p', 6 , l);
		}
		
		disBoard[0][0] = new piece('w', 'R', 0, 0);
		disBoard[0][1] = new piece('w', 'N', 0, 1);
		disBoard[0][2] = new piece('w', 'B', 0, 2);
		disBoard[0][3] = new piece('w', 'Q', 0, 3);
		disBoard[0][4] = new piece('w', 'K', 0, 4);
		whiteKing = disBoard[0][4];
		disBoard[0][5] = new piece('w', 'B', 0, 5);
		disBoard[0][6] = new piece('w', 'N', 0, 6);
		disBoard[0][7] = new piece('w', 'R', 0, 7);
		
		disBoard[7][0] = new piece('b', 'R', 7, 0);
		disBoard[7][1] = new piece('b', 'N', 7, 1);
		disBoard[7][2] = new piece('b', 'B', 7, 2);
		disBoard[7][3] = new piece('b', 'Q', 7, 3);
		disBoard[7][4] = new piece('b', 'K', 7, 4);
		blackKing = disBoard[7][4];
		disBoard[7][5] = new piece('b', 'B', 7, 5);
		disBoard[7][6] = new piece('b', 'N', 7, 6);
		disBoard[7][7] = new piece('b', 'R', 7, 7);
		disBoard[7][7] = new piece('b', 'R', 7, 7);
		
		return disBoard;
	}
	
	/**
	* Prints the current state of the board in accordance with the proper board format
	* @param disBoard the 8x8 array board to be printed
	*/
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
	
	/**
	* Interprets move input <code>String</code>, checks for correct syntax and trivial errors, 
	* calls <code>checkMove</code> to determine move legality, executes legal moves
	*
	* @param userInput the inputted move by the user, ie e2 e4
	* @param turn white or black's turn
	* @return -1 to indicate invalid move, 0 for resignation, 1 for the execution of a legal move, 2 for draw
	*/
	public static int doMove(String userInput, char turn)
	{	
		
		if(draw){
			if(userInput.equals("draw")){
				return 2;
			}
			return -1;
		}
		if(userInput.equals("resign"))	//return 0 for resign
		{
			return 0;
		}
		
		String[] tokens;
		tokens = userInput.split(" ", 4);
		
		if(tokens.length < 2)
		{
			return -1;
		}
		
		if(tokens[0].length() != 2 || tokens[1].length() != 2)
		{
			return -1;
		}
		
		if(tokens.length > 4)
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
		if(thatPiece == thisPiece) {
			return -1;
		}
		
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
		
		L = tokens[1].charAt(0);
		N = tokens[1].charAt(1);
		int thatN = N - 48 - 1;
		int thatL = L - 'a';
		
		if(thatN < 0 || thatN > 7 || thatL < 0 || thatL > 7)
		{
			return -1;
		}
		
		piece[][] tempBoard = copyBoard(disBoard);
		
		int moveChecker = checkMove(tempBoard, thisPiece, tokens[0], thatPiece, tokens[1]);
		//System.out.println(moveChecker);
		
		if(moveChecker < 0)
		{
			return -1;
		}
		//movethepieces
		tempBoard[thisN][thisL] = null;
		tempBoard[thatN][thatL] = thisPiece;
		
		int kN, kL;
		if(moveChecker==3 && thisPiece.getName() == 'K'){
			kN = thatN;
			kL = thatL;
		}
		else if(turn == 'w'){
			kN = whiteKing.getN();
			kL = whiteKing.getL();
		}
		else{
			kN = blackKing.getN();
			kL = blackKing.getL();
		}
		
		if(inCheck(tempBoard, turn, kN, kL).size() > 0){
			return -1;
		}

		//enPassant testCase
		enPassantPawn = null;
		if(moveChecker == 2)	//enPassantPawn updates
		{
			enPassantPawn = tempBoard[thatN][thatL];
		}
		if(moveChecker == 3)	//whiteKing/blackKing, hasMoved updates
		{
			//System.out.println(thisPiece.canCastle());
			if(thisPiece.getName() == 'K') {
				if(thisPiece.getColor() == 'w')
				{
					whiteKing = thisPiece;
					whiteKing.setN(thatN);
					whiteKing.setL(thatL);
				}
				if(thisPiece.getColor() == 'b')
				{
					blackKing = thisPiece;
					blackKing.setN(thatN);
					blackKing.setL(thatL);
				}
			}
			thisPiece.setCastle(false);
		}
		if(moveChecker == 4){
			tempBoard[thatN][thatL] = new piece(turn, 'Q', thatN, thatL);
			String promoteList = "QNRB";
			if(tokens.length > 2)
			{
				if (tokens[2].equals("draw?")){
					draw = true;
				}
				else{
					if(promoteList.indexOf(tokens[2].charAt(0)) == -1 || tokens[2].length() != 1)
					{
						return -1;
					}
					tempBoard[thatN][thatL] = new piece(turn, tokens[2].charAt(0), thatN, thatL);
					if(tokens.length > 3){
						if(tokens[3].equals("draw?")){
							draw = true;
						}
					}
				}
			}
		}
		else{
			if(tokens.length == 3){
				if (tokens[2].equals("draw?")){
					draw = true;
				}
				else{
					return -1;
				}
			}
			if(tokens.length > 3){
				return -1;
			}
		}
		
		//printBoard(tempBoard);
		//printBoard(disBoard);
		disBoard = tempBoard;
		
		return 1;
	}
	/**
	* Converts chess location syntax to array coordinates and returns the piece at the location
	*
	* @param disBoard the chess board in its current state
	* @param location ie e4
	* @return the <code>piece</code> at the location
	*/		
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
	
	/**
	* Creates and returns a temporary copy of the chess board to test legality of certain moves
	*
	* @param disBoard the 8x8 <code>piece</code> array chess board in its current state to be copied
	* @return a new 8x8 <code>piece</code> array with the same piece positions
	*/			
	public static piece[][] copyBoard(piece[][] disBoard)
	{
		piece[][] boardCopy = new piece[8][8];
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				boardCopy[i][j] = disBoard[i][j];
			}
		}
		return boardCopy;
	}
	
	/**
	* Checks if a specified location can be legally captured or accessed by a piece of the opposite color given
	*
	* @param disBoard the 8x8 <code>piece</code> array chess board in its current state
	* @param color the piece color of the king in check or the color which is checking the king
	* @param N row number of specified location
	* @param L column number of specified location
	* @return the list of <code>piece</code>s of the opposite color that can move to N,L, 
	* empty would mean not in check or cannot be occupied
	*/	
	public static ArrayList<piece> inCheck(piece[][] disBoard, char color, int N, int L)
	{
		//System.out.println(color + " King location: " + N + ", " + L);
		//System.out.println("N = " + N + "L = " + L);
		
		ArrayList<piece> checkPieces = new ArrayList<piece>();
		
		int[] knightN = {-2, -2, -1, -1, 1, 1, 2, 2};
		int[] knightL = {-1, 1, -2, 2, -2, 2, -1, 1};
		for (int i = 0; i < 8; i++){
			if(0<=(N+knightN[i]) && (N+knightN[i])<8 && 0<=(L+knightL[i]) && (L+knightL[i])<8){
				if(disBoard[N+knightN[i]][L+knightL[i]] != null && disBoard[N+knightN[i]][L+knightL[i]].getName() == 'N' && disBoard[N+knightN[i]][L+knightL[i]].getColor() != color){
					checkPieces.add(new piece('c', disBoard[N+knightN[i]][L+knightL[i]].getName(), N+knightN[i], L+knightL[i]));
				}
			}
		}
		int currN = N;
		int currL = L;
		int[] diagN = {-1, -1, 1, 1};
		int[] diagL = {-1, 1, -1, 1};
		for (int i = 0; i < 4; i++){
			currN = N+diagN[i];
			currL = L+diagL[i];
			while(0<=currN && currN<8 && 0<=currL && currL<8){
				if(disBoard[currN][currL] != null){
					if(disBoard[currN][currL].getColor() == color){
						break;
					}
					else if(disBoard[currN][currL].getName() == 'Q'|| disBoard[currN][currL].getName() == 'B'){
						checkPieces.add(new piece('c', disBoard[currN][currL].getName(),currN, currL));
					}
					break;
					
				}
				currN = currN+diagN[i];
				currL = currL+diagL[i];
			}
		}
		int[] rcN = {-1, 1, 0, 0};
		int[] rcL = {0, 0, -1, 1};
		for (int i = 0; i < 4; i++){
			currN = N+rcN[i];
			currL = L+rcL[i];
			while(0<=currN && currN<8 && 0<=currL && currL<8){
				if(disBoard[currN][currL] != null){
					if(disBoard[currN][currL].getColor() == color){
						break;
					}
					else if(disBoard[currN][currL].getName() == 'Q'|| disBoard[currN][currL].getName() == 'R'){
						checkPieces.add(new piece('c', disBoard[currN][currL].getName(),currN, currL));
					}
					break;
					
				}
				currN = currN+rcN[i];
				currL = currL+rcL[i];
			}
		}
		
		int pN;
		if (color == 'w'){
			pN = 1;
		}
		else{
			pN = -1;
		}
		
		if(disBoard[N][L] == null)
		{
			if(0<=(N+pN) && (N+pN)<8 && 0<=(L) && (L)<8 && disBoard[N+pN][L] != null)
			{
				if(disBoard[N+pN][L].getName() == 'p' && disBoard[N+pN][L].getColor() != color)
				{
					checkPieces.add(new piece('c', 'p', N+pN, L));
				}	
			}
			
			else if(0 <= N +(2 * pN) && N +(2 * pN) < 8 && 0 <= L && L<8 && disBoard[N+(2* pN)][L] != null)
			{
				if(disBoard[N + (2 * pN)][L].getName() == 'p' && disBoard[N+(2 * pN)][L].getColor() != color)
				{
					checkPieces.add(new piece('c', 'p', N+ (2 * pN), L));
				}	
			}
		}
		else
		{
			if(0<=(N+pN) && (N+pN)<8 && 0<=(L-1) && (L-1)<8 && disBoard[N+pN][L-1] != null){
				if(disBoard[N+pN][L-1].getName() == 'p' && disBoard[N+pN][L-1].getColor() != color){
					checkPieces.add(new piece('c', disBoard[N+pN][L-1].getName(), N+pN, L-1));
				}
					
			}
			if(0<=(N+pN) && (N+pN)<8 && 0<=(L+1) && (L+1)<8 && disBoard[N+pN][L+1] != null){
				if(disBoard[N+pN][L+1].getName() == 'p' && disBoard[N+pN][L+1].getColor() != color){
					checkPieces.add(new piece('c', disBoard[N+pN][L+1].getName(), N+pN, L+1));
				}
			}
		}
		return checkPieces;
	}
	
	/**
	* Given location and color of king and list of attacking pieces, checks if legal moves exist
	*
	* @param disBoard the 8x8 piece array chess board in its current state
	* @param checkPieces the list of pieces putting the king in check
	* @param color the piece color of the king in check
	* @param N row number of specified location
	* @param L column number of specified location
	* @return true if no legal moves exist and false if legal move was found
	*/		
	public static boolean isCheckMate(piece[][] disBoard, ArrayList<piece> checkPieces, char color, int N, int L)
	{
		
		int[] kingN = {-1, -1, -1,  0, 0, 1, 1, 1};
		int[] kingL = {-1,  1,  0, -1, 1, 0, -1, 1};
		
		piece[][] tempBoard;
		
		for(int i = 0; i < 8; i++)
		{
			if(0<=(N+kingN[i]) && (N+kingN[i])<8 && 0<=(L+kingL[i]) && (L+kingL[i])<8)
			{
				if(disBoard[N + kingN[i]][L + kingL[i]] == null)
				{
					tempBoard = copyBoard(disBoard);
					tempBoard[N + kingN[i]][L + kingL[i]] = tempBoard[N][L];
					tempBoard[N][L] = null;
					if(inCheck(tempBoard, color, N + kingN[i], L + kingL[i]).size() == 0)
					{
						return false;
					}
					
				}
				else if(disBoard[N + kingN[i]][L + kingL[i]].getColor() != color)
				{
					tempBoard = copyBoard(disBoard);
					tempBoard[N + kingN[i]][L + kingL[i]] = tempBoard[N][L];
					tempBoard[N][L] = null;
					if(inCheck(tempBoard, color, N + kingN[i], L + kingL[i]).size() == 0)
					{
						return false;
					}
				}
			}
		}
		
		if(checkPieces.size() > 1)
		{
			return true;
		}
		
		char otherColor;
		if(color == 'w')
		{
			otherColor = 'b';
		}
		else
		{
			otherColor = 'w';
		}
			
		int attackN = checkPieces.get(0).getN();
		int attackL = checkPieces.get(0).getL();
		char type = checkPieces.get(0).getName();
		ArrayList<piece> currList = inCheck(disBoard, otherColor, attackN, attackL);
		
		for(piece currPiece : currList)
		{
			tempBoard = copyBoard(disBoard);
			tempBoard[attackN][attackL] = tempBoard[currPiece.getN()][currPiece.getL()];
			tempBoard[currPiece.getN()][currPiece.getL()] = null;
			if(inCheck(tempBoard, color, N, L).size() == 0)
			{
				return false;
			}
		}
		
		if(type == 'N' || type == 'p')
		{
			return true;
		}
		
		int nInc;
		int lInc;
		if (attackN > N) {
			nInc = 1;
		}
		else if (attackN < N) {
			nInc = -1;
		}
		else {
			nInc = 0;
		}
		if (attackL > L) {
			lInc = 1;
		}
		else if (attackL < L) {
			lInc = -1;
		}
		else {
			lInc = 0;
		}
		int currN = N + nInc;
		int currL = L + lInc;
		while(currN != attackN || currL != attackL) 
		{
			currList = inCheck(disBoard, otherColor, currN, currL);
			for(piece currPiece : currList)
			{
				tempBoard = copyBoard(disBoard);
				tempBoard[attackN][attackL] = tempBoard[currPiece.getN()][currPiece.getL()];
				tempBoard[currPiece.getN()][currPiece.getL()] = null;
				if(inCheck(tempBoard, color, N, L).size() == 0)
				{
					return false;
				}
			}
			
			currN = currN + nInc;
			currL = currL + lInc;
		}
		return true;
	}
	
	/**
	* Checks if there is a path free of pieces between current location and destination for Queens, Rooks, and Bishops
	*
	* @param thisN current row of piece
	* @param thisL current column of piece
	* @param thatN row number of specified location
	* @param thatL column number of specified location
	* @return true if the path is clear and false if any space in between is occupied
	*/		
	public static boolean path(int thisN, int thisL, int thatN, int thatL) {
		int ninc;
		int linc;
		if (thatN > thisN) {
			ninc = 1;
		}
		else if (thatN < thisN) {
			ninc = -1;
		}
		else {
			ninc = 0;
		}
		if (thatL > thisL) {
			linc = 1;
		}
		else if (thatL < thisL) {
			linc = -1;
		}
		else {
			linc = 0;
		}
		int currN = thisN+ninc;
		int currL = thisL+linc;
		while (currN != thatN || currL != thatL) {
			if(disBoard[currN][currL] != null) {
				return false;
			}
			currN = currN+ninc;
			currL = currL+linc;
		}
		return true;
	}
	
	/**
	* Checks if given move is in accordance with the moveset of the given piece and returns indicators for special cases
	*
	* @param disBoard the 8x8 <code>piece</code> array chess board in its current state
	* @param thisPiece the piece to be moved
	* @param thisLoc the current location of the piece in chess <code>String</code> syntax
	* @param thatPiece the <code>piece</code> or space to be moved to
	* @param thatLoc the destination location of the piece in chess <code>String</code> syntax
	* @return -1 for illegality, 1 for normal legal move, 2 to indicate a pawn double move, 3 for the movement of king or rook, 4 for pawn promotion
	*/		
	public static int checkMove(piece[][] disBoard, piece thisPiece, String thisLoc, piece thatPiece, String thatLoc)
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
		
		//System.out.println("moveN: " + moveN + " moveL: " + moveL);
		//System.out.println("thisN: " + thisN + " thisL: " + thisL);
		//System.out.println("thatN: " + thatN + " thatL: " + thatL);
		
		if(thisPiece.getName() == 'p')
		{
			if(thisPiece.getColor() == 'w')
			{
				if(moveN == 2 && moveL == 0 && thisN == 1)	//move pawn 2 spaces forward from start
				{
					if(disBoard[thisN + 1][thisL] == null && thatPiece == null)
					{
						return 2;
					}
				}
				
				if(moveN == 1 && moveL == 0 && thatPiece == null)	//move pawn 1 space forward
				{
					if(thatN == 7){
						return 4;
					}
					return 1;
				}
				
				if(moveN == 1 && Math.abs(moveL) == 1)	//check for normal capture, then check for enPassant
				{
					if(thatPiece != null)
					{
						if(thatPiece.getColor() == 'b')
						{
							if(thatN == 7){
								return 4;
							}
							return 1;
						}
					}
					
					//piece enPassantPawn = disBoard[thisN][thatL];
					if(enPassantPawn != null)
					{
						disBoard[thisN][thatL] = null;
						return 1;
					}
				}
				return -1;
				
			}
			if(thisPiece.getColor() == 'b')
			{
				if(moveN == -2 && moveL == 0 && thisN == 6)	//move pawn 2 spaces forward from start
				{
					if(disBoard[thisN - 1][thisL] == null && thatPiece == null)
					{
						return 2;
					}
				}
				
				if(moveN == -1 && moveL == 0 && thatPiece == null)	//move pawn 1 space forward
				{
					if(thatN == 0){
						return 4;
					}
					return 1;
				}
				
				if(moveN == -1 && Math.abs(moveL) == 1)	//pawn captures, check for enPassant
				{
					if(thatPiece != null)
					{
						if(thatPiece.getColor() == 'w')
						{
							if(thatN == 0){
								return 4;
							}
							return 1;
						}
					}
					
					//piece enPassantPawn = disBoard[thisN][thatL];
					if(enPassantPawn != null)
					{
						disBoard[thisN][thatL] = null;
						return 1;
					}
				}
				return -1;
			}
		}
		
		if(thisPiece.getName() == 'N')
		{
			if((Math.abs(moveN) == 1 && Math.abs(moveL) == 2) || (Math.abs(moveN) == 2 && Math.abs(moveL) == 1))
			{
				return 1;
			}
		}
		
		if(thisPiece.getName() == 'K')
		{

			if(Math.abs(moveN) <= 1 && Math.abs(moveL) <= 1)
			{
				return 3;
			}
			if(Math.abs(moveL) == 2 && thisPiece.canCastle()) {
				if(moveL == 2 && disBoard[thisN][thisL+1] == null && thatPiece == null && (disBoard[thisN][7]).canCastle()){
					if(inCheck(disBoard, thisPiece.getColor(), thisN, thisL).size() == 0 && inCheck(disBoard, thisPiece.getColor(), thisN, thisL + 1).size() == 0){
						disBoard[thisN][thisL+1] = disBoard[thisN][7];
						disBoard[thisN][7] = null;
						return 3;
					}
				}	
				if(moveL == -2 && disBoard[thisN][thisL-1] == null && thatPiece == null && disBoard[thisN][thisL-3] == null && (disBoard[thisN][0]).canCastle()){
					if(inCheck(disBoard, thisPiece.getColor(), thisN, thisL).size() == 0 && inCheck(disBoard, thisPiece.getColor(), thisN, thisL - 1).size() == 0){
						disBoard[thisN][thisL-1] = disBoard[thisN][0];
						disBoard[thisN][0] = null;
						return 3;
					}
				}	
			}
		}
		if(thisPiece.getName() == 'R')
		{
			if(moveN == 0 || moveL == 0) {
				if(path(thisN, thisL, thatN, thatL)) {
					return 3;
				}
			}
		}
		if(thisPiece.getName()== 'B') {
			if(Math.abs(moveN)==Math.abs(moveL)) {
				if(path(thisN, thisL, thatN, thatL)) {
					return 1;
				}
			}
		}
		if(thisPiece.getName()== 'Q') {
			if((Math.abs(moveN)==Math.abs(moveL))||(moveN == 0 || moveL == 0)) {
				if(path(thisN, thisL, thatN, thatL)) {
					return 1;
				}
			}
		}
		return -1;

	}
}