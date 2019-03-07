import shapes.*; 

public class Board {

	private int row;
	private int col;
	private int turn;
	private String[][] board;
	
	public Board(int r, int c)
	{
		row = r;
		col = c;
		turn = 1;
		board = new String[row][col];
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				board[i][j] = " ";
			}
		}
	}
	
	public void showBoardS()
	{
		int x = 10;
		int y = 10;
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				Rectangle line = new Rectangle(x,y,50,1);
				line.draw();
				Rectangle line2 = new Rectangle(x,y+50,50,1);
				line2.draw();
				Rectangle line3 = new Rectangle(x,y,1,50);
				line3.draw();
				Rectangle line4 = new Rectangle(x+50,y,1,50);
				line4.draw();
				
				Rectangle box = new Rectangle(x, y, 50, 50); 
				box.setColor(Color.BLUE);
				box.fill();
				
				Ellipse spot = new Ellipse(x + 10, y + 10, 30, 30);
				if (board[i][j].equals(" "))
				{
					spot.setColor(Color.WHITE);
				}
				else if (board[i][j].equals("X"))
				{
					spot.setColor(Color.RED);
				}
				else if (board[i][j].equals("0"))
				{
					spot.setColor(Color.YELLOW);
				}
				spot.fill();
				
				x = x + 50;
			}
			x = 10;
			y = y + 50;
		}
	}
	
	public void showBoard()
	{
		System.out.print("  ");
		for (int i = 1; i <= col; i++)
		{
			System.out.print("   "+i+"  ");
		}
		System.out.println("  ");
		System.out.print("  ");
		for (int i = 0; i < col; i++)
		{
			System.out.print("  === ");
		}
		System.out.println("  ");
		for (int i = 0; i < row; i++)
		{
			System.out.print(i + 1 + " ");
			for (int j = 0; j < col; j++)
			{
				System.out.print("|  "+board[i][j]+"  ");
			}
			System.out.println("|");
			
			System.out.print("  ");
			for (int r = 0; r < col; r++)
			{
				System.out.print("  === ");
			}
			System.out.println("  ");
		}
	}
	
	public int placePiece(int c)//returns row index -- c must be index
	{
		int h = c;
		int s = 0;
		int i = 0;
		while (i < row - 1)
		{
			if (!board[i + 1][h].equals(" "))
			{
				if (turn == 1)
				{
					board[i][h] = "X";
				}
				else
				{
					board[i][h] = "0";
				}
				s = i;
				i = row;
			}
			else
			{
				i++;
			}
		}
		if (i == row - 1)
		{
			if (turn == 1)
			{
				board[row - 1][h] = "X";
			}
			else
			{
				board[row - 1][h] = "0";
			}
			s = i;
		}
		return s;
	}
	
	public void changeTurn()
	{
		turn = -turn;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	public boolean checkFull()
	{
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				if (board[i][j].equals(" "))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public int computerMove(int num)
	{
		int score = -1;
		int val = 0;
		for (int i = 0; i < getCol(); i++)
		{
			if (!checkFullCol(i))
			{
				int hold = score;
				score = -1;
				int l = placePiece(i);
				if (checkWin(l,i,num))
				{
					score  = 3;
				}
				board[l][i] = " ";
				changeTurn();
				l = placePiece(i);
				if ((score <= 2) && checkWin(l,i,num))
				{
					score = 2;
				}
				if ((i >= 1) && (i <= 3))
				{
					int z = placePiece(i+3);
					if ((score <= 0) && checkWin(l,i,num))
					{
						score  = 1;
					}
					board[z][i+3] = " ";
				}
				if ((i >= 3) && (i <= 6))
				{
					int z = placePiece(i-3);
					if ((score <= 0) && checkWin(l,i,num))
					{
						score  = 1;
					}
					board[z][i-3] = " ";
				}
				board[l][i] = " ";
				changeTurn();
				l = placePiece(i);
				if ((score <= 0) && !checkWin(l,i,num))
				{
					score = 0;
				}
				changeTurn();
				int x = placePiece(i);
				if ((score <= 0) && checkWin(x,i,num))
				{
					score = -1;
				}
				changeTurn();
				board[l][i] = " ";
				board[x][i] = " ";
				if (score > hold)
				{
					val = i;
				}
			}
			else
			{
				val = i+1;
			}
		}
		return val;
	}
	
	public boolean checkWin(int r, int c, int num)
	{
		return checkVertWin(c, num) || checkHoriWin(r, num) || checkDiagonal1(r,c,num) || checkDiagonal2(r,c,num);
	}
	
	public boolean checkVertWin(int c, int num) //col of piece
	{
		int count = 0;
		for (int i = row - 1; i > 0; i--)
		{
			if (board[i][c].equals(board[i - 1][c]) && !board[i][c].equals(" "))
			{
				count++;
				if (inLine(count, num))
				{
					i = 0;
				}
			}
			else
			{
				count = 0;
			}
		}
		return inLine(count, num);
	}
	
	public boolean checkHoriWin(int r, int num) // row of piece
	{
		int count = 0;
		for (int i = 0; i < col - 1; i++)
		{
			if (board[r][i].equals(board[r][i + 1]) && !board[r][i].equals(" "))
			{
				count++;
				if (inLine(count, num))
				{
					i = col;
				}
			}
			else
			{
				count = 0;
			}
		}
		return inLine(count, num);
	}
	
	public boolean checkDiagonal1(int r, int c, int num)
	{
		int count = 0;
		int startRow = findStart1(r,c)[0];
		int startCol = findStart1(r,c)[1];
		int endRow = findEnd1(r,c)[0];
		int endCol = findEnd1(r,c)[1];
		while(startRow < endRow && startCol < endCol)
		{
			if (board[startRow][startCol].equals(board[startRow + 1][startCol + 1])
					&& !board[startRow][startCol].equals(" "))
			{
				count++;
				if (inLine(count, num))
				{
					startRow = endRow;
				}
			}
			else
			{
				count = 0;
			}
			startRow++;
			startCol++;
		}
		return inLine(count, num);
	}
	
	public boolean checkDiagonal2(int r, int c, int num)
	{
		int count = 0;
		int startRow = findStart2(r,c)[0];
		int startCol = findStart2(r,c)[1];
		int endRow = findEnd2(r,c)[0];
		int endCol = findEnd2(r,c)[1];
		while(startRow < endRow && startCol > endCol)
		{
			if (board[startRow][startCol].equals(board[startRow + 1][startCol - 1])
					&& !board[startRow][startCol].equals(" "))
			{
				count++;
				if (inLine(count, num))
				{
					startRow = endRow;
				}
			}
			else
			{
				count = 0;
			}
			startRow++;
			startCol--;
		}
		return inLine(count, num);
	}
	
	public int[] findStart1(int r,  int c)
	{
		int[] result = new int[2];
		while(c > 0 && r > 0)
		{
			r--;
			c--;
		}
		result[0] = r;
		result[1] = c;
		return result;
	}
	
	public int[] findStart2(int r, int c)
	{
		int[] result = new int[2];
		while(c < col - 1 && r > 0)
		{
			r--;
			c++;
		}
		result[0] = r;
		result[1] = c;
		return result;
	}
	
	public int[] findEnd1(int r, int c)
	{
		int[] result = new int[2];
		while(c < col - 1 && r < row - 1)
		{
			r++;
			c++;
		}
		result[0] = r;
		result[1] = c;
		return result;
	}
	
	public int[] findEnd2(int r, int c)
	{
		int[] result = new int[2];
		while(c > 0 && r < row - 1)
		{
			r++;
			c--;
		}
		result[0] = r;
		result[1] = c;
		return result;
	}
	
	public boolean inLine(int i, int num)
	{
		if (i >= (num - 1))
		{
			return true;
		}
		return false;
	}
	
	public boolean checkFullCol(int c)
	{
		for(int i = 0; i < row; i++)
		{
			if (board[i][c].equals(" "))
			{
				return false;
			}
		}
		return true;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getRow()
	{
		return row;
	}
}
