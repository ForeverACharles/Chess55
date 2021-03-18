public class piece
{
	private char color;
	private char name;
	private int n;
	private int l;
	private boolean hasMoved;
	
	public piece(char color, char name, int n, int l)
	{
		this.color = color;
		this.name = name;
		this.n = n;
		this.l = l;
		hasMoved = false;
	}
	
	
	
	
	public char getColor()
	{
		return color;
	}
	
	public char getName()
	{
		return name;
	}
	
	public int getN()
	{
		return n;
	}
	public int getL()
	{
		return l;
	}
	
	public void setN(int n)
	{
		this.n = n;
	}
	
	public void setL(int l)
	{
		this.l = l;
	}
	public void setMoved(boolean moved)
	{
		hasMoved = moved;
	}
	public boolean hasMoved()
	{
		return hasMoved;
	}
	public String toString()
	{
		return "" + color + name;
	}
}