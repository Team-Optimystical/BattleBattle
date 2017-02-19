package tictactoeRandomO;

import java.util.ArrayList;
import java.util.List;

import expectimax.NotExpectTurnException;
import expectimax.State;
import tictactoe.Board.Player;

public class Board implements State {
	public enum Player {X, O, NONE};
	private Player[][] board = new Player[3][3];
	
	private Player turn = Player.X;
	
	public Board() {
		for (int x = 0; x < board.length; ++x) {
			for (int y = 0; y < board.length; ++y) {
				board[x][y] = Player.NONE;
			}
		}
	}
	
	public Board(Board toCopy) {
		for (int x = 0; x < board.length; ++x) {
			for (int y = 0; y < board.length; ++y) {
				board[x][y] = toCopy.board[x][y];
			}
		}
		
		turn = toCopy.turn;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Board) {
			Board otherBoard = (Board)other;
			
			for (int x = 0; x < 3; ++x) {
				for (int y = 0; y < 3; ++y) {
					if (this.board[x][y] != otherBoard.board[x][y]) {
						return false;
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public int hashCode() {
		int code = 0;
		
		for (int x = 0; x < 3; ++x) {
			for (int y = 0; y < 3; ++y) {
				if (this.board[x][y] == Player.X) {
					code |= (1l << (x + y * 3));
				} else if (this.board[x][y] == Player.O) {
					code |= (1l << (x + y * 3));
				}
			}
		}
		
		return code;
	}
	
	public void put(Player p, int x, int y) {
		if (p == turn) {
			board[x][y] = p;
			turn = p == Player.X ? Player.O : Player.X;
		} else {
			throw new RuntimeException("It's not " + p.name() + "'s turn.");
		}
	}
	
	public boolean isFull() {
		for (Player[] col : board) {
			for (Player square : col) {
				if (square == Player.NONE) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Player getWinner() {
		// check rows
		for (int y = 0; y < 3; ++y) {
			if (board[0][y] == board[1][y] 
				&& board[1][y] == board[2][y] 
				&& board[0][y] != Player.NONE) {
				return board[0][y];
			}
		}
		
		// check columns
		for (int x = 0; x < 3; ++x) {
			if (board[x][0] == board[x][1] 
				&& board[x][1] == board[x][2] 
				&& board[x][0] != Player.NONE) {
				return board[x][0];
			}
		}
		
		// check diagonals
		if (board[0][0] == board[1][1] 
			&& board[1][1] == board[2][2] 
			&& board[0][0] != Player.NONE) {
			return board[0][0];
		}
		
		if (board[0][2] == board[1][1] 
			&& board[1][1] == board[2][0] 
			&& board[0][2] != Player.NONE) {
			return board[0][2];
		}
		
		return Player.NONE;
	}
	
	@Override
	public String toString() {
		String s = "\n";
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				if (board[x][y] == Player.X) {
					s += "X";
				} else if (board[x][y] == Player.O) {
					s += "O";
				} else {
					s += "_";
				}
			}
			s += "\n";
		}
		
		return s;
	}
	
	@Override
	public boolean isTerminal() {
		return isFull() || (getWinner() != Player.NONE);
	}
	
	@Override
	public boolean isMaxWin() {
		return getWinner() == Player.X;
	}
	
	@Override
	public boolean isMinWin() {
		return getWinner() == Player.O;
	}

	@Override
	public float score() {
		if (getWinner() == Player.X) {
			return 1;
		} else if (getWinner() == Player.O) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean isMaxTurn() {
		return turn == Player.X && !isFull();
	}

	@Override
	public boolean isMinTurn() {
		return false;
	}

	@Override
	public boolean isExpectTurn() {
		return !isFull() && !isMaxTurn();
	}

	@Override
	public List<State> getNeighbors() {
		List<State> neighbors = new ArrayList<>();
		Player p;
		
		if (isFull()) {
			return neighbors;
		} else if (isMaxTurn()) {
			p = Player.X;
		} else {
			p = Player.O;
		}
		
		for (int x = 0; x < 3; ++x) {
			for (int y = 0; y < 3; ++y) {
				if (board[x][y] == Player.NONE) {
					Board neighbor = new Board(this);
					neighbor.put(p, x, y);
					
					neighbors.add(neighbor);
				}
			}
		}
		
		return neighbors;
	}

	@Override
	public List<Float> getProbs() throws NotExpectTurnException {
		if (!isExpectTurn()) {
			throw new NotExpectTurnException();
		}
		
		List<Float> probs = new ArrayList<>();
		
		int numMoves = 0; 
		for (int x = 0; x < 3; ++x) {
			for (int y = 0; y < 3; ++y) {
				if (board[x][y] == Player.NONE) {
					++numMoves;
				}
			}
		}
		
		for (int i = 0; i < numMoves; ++i) {
			probs.add(1f / numMoves);
		}
		
		return probs;
	}

}
