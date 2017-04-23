package pazura.si.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sac.graph.BestFirstSearch;
import sac.graph.Dijkstra;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphSearchConfigurator;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

public class Sudoku extends GraphStateImpl {

	public static final int n = 3;
	public static final int n2 = n * n;

	public byte[][] board = null;
	public int zeros = n2 * n2;

	static {
		setHFunction(new SudokuHFunction());
	}

	private void refreshZeros() {
		zeros = 0;
		for (int i = 0; i < n2; i++)
			for (int j = 0; j < n2; j++)
				if (board[i][j] == 0)
					zeros++;
	}

	public Sudoku() {

		board = new byte[n2][n2];
		for (int i = 0; i < n2; i++)
			for (int j = 0; j < n2; j++)
				board[i][j] = 0;

	}

	public Sudoku(Sudoku parent) {
		board = new byte[n2][n2];
		for (int i = 0; i < n2; i++)
			for (int j = 0; j < n2; j++)
				board[i][j] = parent.board[i][j];
		zeros = parent.zeros;
	}

	public static void main(String[] arg) {
		
		Sudoku obj = new Sudoku();

		//String SudokuAsString = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
		String SudokuAsString = "000020600900000001001000400008102900700000008006708200002609500800203009005010300";
		obj.FromString(SudokuAsString);
		System.out.println(obj.toString());

		System.out.println(obj.isLegal());
		GraphSearchConfigurator conf = new GraphSearchConfigurator();
		conf.setWantedNumberOfSolutions(Integer.MAX_VALUE);
		
		GraphSearchAlgorithm a = new BestFirstSearch(obj,conf);
		
		a.execute();
		List<GraphState> sols = a.getSolutions();

		System.out.println("Solutions: " + sols.size());
		for (GraphState sol : sols) {
			System.out.println(sol);
		}

		System.out.println("Time: " + a.getDurationTime() );
		System.out.println("Closed: " + a.getClosedStatesCount());
		System.out.println("Open: " + a.getOpenSet().size());
		System.out.println("Solution: " + a.getSolutions().size());
		
		
		
		

		
		
	}
	
	

	@Override
	public int hashCode() {
		int k = 0;
		byte[] linear = new byte[n2*n2];
		for(int i =0; i<n2; i++)
		{
			for(int j=0; j<n2; j++)
			{
				linear[k++] = board[i][j];
			}
		}
		
		return Arrays.hashCode(linear);
		//return toString().hashCode();
	}

	@Override
	public String toString() {
		//String result = "";
		StringBuilder result = new StringBuilder();
		int x = 1, y = 1;

		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				result.append(board[i][j]);
				result.append(" ");
				//result += board[i][j] + " ";
				if (x % n == 0)
					result.append(" ");
					//result += "  ";
				x++;
			}
			if (y % n == 0)
				result.append("\n");
				//result += "\n";
			y++;
			result.append("\n");
			//result += "\n";
		}

		return result.toString();
	}

	public void FromString(String txt) {

		int k = 0;
		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				board[i][j] = Byte.valueOf(txt.substring(k, k + 1));
				k++;
			}

		}
		refreshZeros();
	}

	public boolean isLegal() {
		byte[] group = new byte[n2];
		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				group[j] = board[i][j];
			}
			if (!isGroupLegal(group))
				return false;
		}
		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				group[j] = board[j][i];
			}
			if (!isGroupLegal(group))
				return false;
		}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int a = 0;
				for (int k = 0; k < n; k++)
					for (int l = 0; l < n; l++) {
						group[a++] = board[i * n + k][j * n + l];
					}
				if (!isGroupLegal(group)) {
					return false;
				}
			}

		return true;
	}

	private boolean isGroupLegal(byte[] group) {
		boolean[] visited = new boolean[n2];
		for (int i = 0; i < n2; i++)
			visited[i] = false;
		for (int i = 0; i < n2; i++) {
			if (group[i] == 0)
				continue;

			if (visited[group[i] - 1])
				return false;
			else
				visited[group[i] - 1] = true;
		}

		return true;
	}

	@Override
	public List<GraphState> generateChildren() {
		// szukamy zera i w miejsce zera wstawiamy wszystkie
		// mozliwe cyferki ktore nie powoduja sprzecznosci
		List<GraphState> children = new ArrayList<>();
		int i = 0, j = 0;
		zero: for (i = 0; i < n2; i++)
			for (j = 0; j < n2; j++)
				if (board[i][j] == 0)
					break zero;

		if (i == n2)
			return children;

		for (int k = 0; k < n2; k++) {
			Sudoku child = new Sudoku(this);
			child.board[i][j] = (byte) (k + 1);
			if (child.isLegal()) {
				children.add(child);
				child.zeros--;
			}
		}

		return children;
	}

	@Override
	public boolean isSolution() {

		return ((zeros == 0) && (isLegal()));
	}

}

/* zmienna n 
	tablica 2wymiarowa jako plansza byte[][] 
	konstruktor genruje plansze rozwiazana
	konstruktor kopiujacy
	
	 mieszanie planszy 
	 fumkcja mieszajaca z parametrem liczby ruchow -> losowe ruchy = mieszanie
	 uzyc klasy java.util.random
	 private static Random rand = new Random(); // wymuszanie tego samego Random(123)
	 rand.nextInt();
	 metoda wykonujaca pojedynczy ruch ^ < > 
	 metoda toString czyli StringBuilder 
	 metoda hashCode 
	 trzymaæ wspólrzedne klocka zera 
	 generowanie potomkow
	 nazywanie potomkow 
	 child.setMoveName("Up""Down"...)
	 funkcje heurystyczne = missplaced types, manhatan
	 wyswietlanie sciezek i ich kosztow:
	 metoda isSolution przy GenerateChildren
	 solution.getG() zwraca sciezke  // zwraca doubla 
	 solution.getPath list graphStatow List<GraphState>
	 solution.getMovesAlogPath() -> Lista stringow
	 co to zbior open 
	 co to manhatan 
	 struktury danych zwiazane z algorytmami 
	 
*/