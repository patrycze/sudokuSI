package pazura.si.sudoku;

public class Sudoku {

	public static final int n = 3;
	public static final int n2 = n*n;
	
	public byte[][] board = null;
	public Sudoku(){
		
		board = new byte[n2][n2];
		for(int i=0; i<n2;i++)
			for(int j=0; j<n2;j++)
				board[i][j] =0;
			
		
	}
	
	public static void main(String[] arg)
	{
		Sudoku obj = new Sudoku();

		String SudokuAsString = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
		obj.FromString(SudokuAsString);
		System.out.println(obj.toString());
	}

	@Override
	public String toString() {
		String result = "";
		
		for (int i=0; i<n2; i++){
			for (int j=0;j<n2;j++)
				result += board[i][j] + " ";
			result += "\n";
		}
			
		return result;
	}
	public void FromString(String txt){
		
		
		int k = 0;
		for (int i=0; i<n2; i++){
			for (int j=0;j<n2;j++){
				board[i][j] = Byte.valueOf(txt.substring(k,k+1));
				k++;
				}
		
		}
	}
}
