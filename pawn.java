public class pawn extends piece
{
	private boolean enPassant;
	
	public pawn(char color, char name, int n, int l)
	{
		super(color, name, n, l);
		enPassant = false;
	}
	
	public boolean canEnPassant()
	{
		return enPassant;
	}
	
	public void setEnPassant(boolean enPassant)
	{
		this.enPassant = enPassant;
	}
	
}

