package pazura.si.sudoku;
import java.util.List;

import sac.graph.Dijkstra;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

public class Sudoku extends GraphStateImpl {

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
		
		System.out.println(obj.isLegal());
		
	}

	@Override
	public String toString() {
		String result = "";
		int x=1, y=1;
		
		for (int i=0; i<n2; i++){
			for (int j=0;j<n2;j++)
			{
				result += board[i][j] + " ";
				if ( x % n == 0)
					result += "  ";
				x++;
			}
			if (y % n == 0)
				result += "\n";
			y++;
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
	
	public boolean isLegal()
	{
		byte[] group = new byte[n2];
		for(int i=0;i<n2;i++)
		{
			for(int j=0;j<n2;j++)
			{
				group[j]=board[i][j];
			}
			if(!isGroupLegal(group))
				return false;			
		}
		for(int i=0;i<n2;i++)
		{
			for(int j=0;j<n2;j++)
			{
				group[j]=board[j][i];
			}
			if(!isGroupLegal(group))
				return false;			
		}
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				int a=0;
				for (int k=0;k<n;k++)
					for (int l=0;l<n;l++)
					{
						group[a++]=board[i*n+k][j*n+l];
					}
				if(!isGroupLegal(group))
				{
					return false;
				}
			}
		
		
		return true;
	}
	
	private boolean isGroupLegal(byte[] group)
	{
		boolean[] visited = new boolean[n2];
		for (int i=0;i<n2;i++)
			visited[i] = false;
		for (int i=0;i<n2;i++)
		{
			if(group[i]==0)
				continue;
		
			if(visited[group[i]-1])
				return false;
			else
				visited[group[i]-1] = true;
		}
		
		return true;
	}

	@Override
	public List<GraphState> generateChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSolution() {
		// TODO Auto-generated method stub
		return false;
	}
}
