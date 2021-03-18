public class piece
{
	private char color;
	private char name;
	private int n;
	private int l;
	
	public piece(char color, char name, int n, int l)
	{
		this.color = color;
		this.name = name;
		this.n = n;
		this.l = l;
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
	
	public String toString()
	{
		return "" + color + name;
	}
}