public class king extends piece
{
	private boolean hasMoved;
	
	public king(char color, char name, int n, int l)
	{
		super(color, name, n, l);
		hasMoved = false;
	}
	
	public void setMoved(boolean moved)
	{
		hasMoved = moved;
	}
	public boolean hasMoved()
	{
		return hasMoved;
	}
}