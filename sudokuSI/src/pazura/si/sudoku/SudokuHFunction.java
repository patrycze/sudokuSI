package pazura.si.sudoku;

import sac.State;
import sac.StateFunction;

public class SudokuHFunction  extends StateFunction{

	@Override
	public double calculate(State state) {
		
	 
		return ((Sudoku)state).zeros;
	}
	

}


