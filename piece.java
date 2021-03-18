public class piece
{
	private char color;
	private char name;
	private int n;
	private int l;
	private boolean castle;
	
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
	public void setCastle(boolean castle)
	{
		this.castle = castle;
	}
	public boolean canCastle()
	{
		return castle;
	}
	public String toString()
	{
		return "" + color + name;
	}
}