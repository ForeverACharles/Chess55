/**
* Piece object that represents a chess piece.
*
* A <code>piece</code> object has qualities of a chess piece, which includes its
* color, its piece type, and its location on the chess board.
*
* @author Charles Li
* @author Max Sun
* @version 1.2 Mar 2021
*/

public class piece
{
	private char color;
	private char name;
	private int n;
	private int l;
	private boolean castle;
	
	/**
	* 
	* Default <code>piece</code> constructor.
	*
	* @param color color of the chess piece
	* @param name the type of chess piece, abbreviated as a single character
	* @param n the vertical coordinate of the piece on the board
	* @param l the horizontal coordinate of the piece on the board
	*/
	public piece(char color, char name, int n, int l)
	{
		this.color = color;
		this.name = name;
		this.n = n;
		this.l = l;
		castle = false;
		if(name == 'K' || name == 'R'){
			castle = true;
		}
	}
	
	/**
	* Gets the color of the chess piece.
	*
	* @return the color of the chess piece
	*/
	public char getColor()
	{
		return color;
	}
	
	/**
	* Gets the name of the chess piece.
	*
	* @return the name of the chess piece
	*/
	public char getName()
	{
		return name;
	}
	
	/**
	* Gets the vertical coordinate of the chess piece.
	*
	* @return the vertical coordinate of the chess piece
	*/
	public int getN()
	{
		return n;
	}
	
	/**
	* Gets the horizontal coordinate of the chess piece.
	*
	* @return the horizontal coordinate of the chess piece
	*/
	public int getL()
	{
		return l;
	}
	
	/**
	* Sets the vertical coordinate of the chess piece.
	*
	* @param n the desired vertical coordinate of the chess piece
	*/
	public void setN(int n)
	{
		this.n = n;
	}
	
	/**
	* Sets the horizontal coordinate of the chess piece.
	*
	* @param l the desired horizontal coordinate of the chess piece
	*
	*/
	public void setL(int l)
	{
		this.l = l;
	}
	
	/**
	* Gets the castle capability of the chess piece. This is to be used with
	* Kings and Rooks.
	*
	* @return the castle capability of the chess piece
	*/
	public boolean canCastle()
	{
		return castle;
	}
	
	/**
	* Sets the castle capability of the chess piece. This is to be used with
	* Kings and Rooks.
	*
	* @param castle the desired castle capability of the chess piece
	*
	*/
	public void setCastle(boolean castle)
	{
		this.castle = castle;
	}
	
	/**
	* Gets the chess piece represented as a String. 
	*
	* @return the chess piece as a <code>String</code>
	*/
	public String toString()
	{
		return "" + color + name;
	}
}