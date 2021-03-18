import java.io.*;
import java.util.*;
import java.lang.*;

public class chess
{
	
	public static piece[][] disBoard;
	public static piece enPassantPawn;
	public static piece whiteKing;
	public static piece blackKing;
	
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
				if(inCheck(disBoard, 'w', kN, kL) != null){
					System.out.println("Check");
				}
				do
				{
					System.out.print("White's move: ");
					userInput = scan.nextLine();
					System.out.println();
					
					//check if move is legal
					int move = doMove(userInput, turn);
					if(move < 0)
					{
						System.out.println("Illegal move. Try again");
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
						break;
					}
					else if(move == 2)
					{	
						gameEnd = true;
						System.out.println("Checkmate\nWhite wins");
						break;
					}
				} while(true);
			}
			
			else	//black's turn
			{
									
				kN = blackKing.getN();
				kL = blackKing.getL();
				if(inCheck(disBoard, 'b', kN, kL) != null){
					System.out.println("Check");
				}
				do
				{
					System.out.print("Black's move: ");
					userInput = scan.nextLine();
					System.out.println();
					
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
						break;
					}
					else if(move == 2)
					{
						gameEnd = true;
						System.out.println("Checkmate\nBlack wins");
						break;
					}
				} while(true);
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
		disBoard[0][4] = new king('w', 'K', 0, 4);
		whiteKing = disBoard[0][4];
		disBoard[0][5] = new piece('w', 'B', 0, 5);
		disBoard[0][6] = new piece('w', 'N', 0, 6);
		disBoard[0][7] = new piece('w', 'R', 0, 7);
		
		disBoard[7][0] = new piece('b', 'R', 7, 0);
		disBoard[7][1] = new piece('b', 'N', 7, 1);
		disBoard[7][2] = new piece('b', 'B', 7, 2);
		disBoard[7][3] = new piece('b', 'Q', 7, 3);
		disBoard[7][4] = new king('b', 'K', 7, 4);
		blackKing = disBoard[7][4];
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
	
	
	public static int doMove(String userInput, char turn)
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
		
		if(tokens[0].length() != 2 || tokens[1].length() < 2 || tokens[1].length() > 3)
		{
			return -1;
		}
	
		String promoteList = "QNRB";
		if(tokens[1].length() == 3 && promoteList.indexOf(tokens[1].charAt(2)) == -1)
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
		System.out.println(moveChecker);
		
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
		if(inCheck(disBoard, turn, kN, kL) != null){
			return -1;
		}

		
		//enPassant testCase
		enPassantPawn = null;
		if(moveChecker == 2)	//enPassantPawn updates
		{
			enPassantPawn = (pawn)tempBoard[thatN][thatL];
		}
		if(moveChecker == 3)	//whiteKing/blackKing, hasMoved updates
		{
			System.out.println(thisPiece.canCastle());
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
		
		//printBoard(tempBoard);
		//printBoard(disBoard);
		disBoard = tempBoard;
		
		return 1;
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
	public static piece inCheck(piece[][] disBoard, char color, int N, int L){
		System.out.println("N = " + N + "L = " + L);
		int[] knightN = {-2, -2, -1, -1, 1, 1, 2, 2};
		int[] knightL = {-1, 1, -2, 2, -2, 2, -1, 1};
		for (int i = 0; i < 8; i++){
			if(0<=(N+knightN[i]) && (N+knightN[i])<8 && 0<=(L+knightL[i]) && (L+knightL[i])<8){
				if(disBoard[N+knightN[i]][L+knightL[i]] != null && disBoard[N+knightN[i]][L+knightL[i]].getName() == 'N' && disBoard[N+knightN[i]][L+knightL[i]].getColor() != color){
					return new piece('c', disBoard[N+knightN[i]][L+knightL[i]].getName(), N+knightN[i], L+knightL[i]);
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
						return new piece('c', disBoard[currN][currL].getName(),currN, currL);
					}
					break;
					
				}
				currN = currN+diagN[i];
				currL = currL+diagL[i];
			}
		}
		int[] rcN = {-1, -1, 0, 0};
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
						return new piece('c', disBoard[currN][currL].getName(),currN, currL);
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
		if(0<=(N+pN) && (N+pN)<8 && 0<=(L-1) && (L-1)<8 && disBoard[N+pN][L-1] != null){
			if(disBoard[N+pN][L-1].getName() == 'p' && disBoard[N+pN][L-1].getColor() != color){
				return new piece('c', disBoard[N+pN][L-1].getName(), N+pN, L-1);
			}
		}
		if(0<=(N+pN) && (N+pN)<8 && 0<=(L+1) && (L+1)<8 && disBoard[N+pN][L+1] != null){
			if(disBoard[N+pN][L+1].getName() == 'p' && disBoard[N+pN][L+1].getColor() != color){
				return new piece('c', disBoard[N+pN][L+1].getName(), N+pN, L+1);
			}
		}
		
		return null;
	}
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
		
		System.out.println("moveN: " + moveN + " moveL: " + moveL);
		System.out.println("thisN: " + thisN + " thisL: " + thisL);
		System.out.println("thatN: " + thatN + " thatL: " + thatL);
		
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
					return 1;
				}
				
				if(moveN == 1 && Math.abs(moveL) == 1)	//check for normal capture, then check for enPassant
				{
					if(thatPiece != null)
					{
						if(thatPiece.getColor() == 'b')
						{
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
					return 1;
				}
				
				if(moveN == -1 && Math.abs(moveL) == 1)	//pawn captures, check for enPassant
				{
					if(thatPiece != null)
					{
						if(thatPiece.getColor() == 'w')
						{
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
					if(inCheck(disBoard, thisPiece.getColor(), thisN, thisL) == null && inCheck(disBoard, thisPiece.getColor(), thisN, thisL + 1) == null){
						disBoard[thisN][thisL+1] = disBoard[thisN][7];
						disBoard[thisN][7] = null;
						return 3;
					}
				}	
				if(moveL == -2 && disBoard[thisN][thisL-1] == null && thatPiece == null && disBoard[thisN][thisL-3] == null && (disBoard[thisN][0]).canCastle()){
					if(inCheck(disBoard, thisPiece.getColor(), thisN, thisL) == null && inCheck(disBoard, thisPiece.getColor(), thisN, thisL - 1) == null){
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