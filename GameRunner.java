import java.util.Scanner;
public class GameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean end = true;
		boolean run = true;
		int r = 6;
		int c = 7;
		int n = 4;
		
		Scanner in = new Scanner(System.in);
		System.out.print("Do you want to play Connect Four (yes or no): ");
		String reply = in.next();
		while(end)
		{
			if(reply.equals("yes"))
			{
				while(run)
				{
					boolean h = true;
					while (h)
					{
						System.out.print("Do you want to set the board size (yes or no): ");
						String ans = in.next();
						if (ans.equals("yes"))
						{
							System.out.print("What is the row length: ");
							r = in.nextInt();
							System.out.print("What is the column length: ");
							c = in.nextInt();
							h = false;
						}
						else if (ans.equals("no"))
						{
							h = false;
						}
						else
						{
							System.out.println("Invalid answer.");
						}
					}
					h = true;
					
					while (h)
					{
						System.out.print("Do you want to set the connect number needed to win (yes or no): ");
						String a = in.next();
						if (a.equals("yes"))
						{
							System.out.print("How many needed to connect: ");
							n = in.nextInt();
							h = false;
						}
						else if (a.equals("no"))
						{
							h = false;
						}
						else
						{
							System.out.println("Invalid answer.");
						}
					}
					
					System.out.print("Do you wanna play against the computer (yes or no): ");
					String com = in.next();
					
					
					if (com.equals("yes"))
					{
						System.out.println("You are playing the computer");
						Board board = new Board(r, c);
						int col = 0;
						int row = 0;
						boolean check = true;
						while (!board.checkWin(row, col, n) && !board.checkFull())
						{
							if (board.getTurn() == 1)
							{
								System.out.println("Player 1's Turn");
								board.showBoardS();
								while(check)
								{
									col = -1;
									while (col < 0 || col >= board.getCol())
									{
										System.out.print("Enter a column to place piece: ");
										col = in.nextInt() - 1;
									}
									if(board.checkFullCol(col))
									{
										System.out.println("Column Full.");
									}
									else
									{
										check = false;
									}
								}
								check = true;
								row = board.placePiece(col);
							}
							else
							{
								System.out.println("Computer's Turn");
								col = board.computerMove(n);
								row = board.placePiece(col);
							}
							board.changeTurn();
						}
						if (board.checkWin(row,col, n))
						{
							if(-board.getTurn() == 1)
							{
								board.showBoardS();
								System.out.println("Congrats Player 1");
							}
							else
							{
								board.showBoardS();
								System.out.println("Computer won");
							}
							System.out.print("Wanna play Connect Four (yes or no): ");
							reply = in.next();
						}
						else
						{
							board.showBoardS();
							System.out.print("Wanna play Connect Four (yes or no): ");
							reply = in.next();
						}
						run = false;
						
					}
					else if (com.equals("no"))
					{
						System.out.println("You are playing P v P");
						
						Board board = new Board(r, c);
						int col = 0;
						int row = 0;
						boolean check = true;
						while (!board.checkWin(row, col, n) && !board.checkFull())
						{
							if (board.getTurn() == 1)
							{
								System.out.println("Player 1's Turn");
							}
							else
							{
								System.out.println("Player 2's Turn");
							}
							board.showBoardS();
							while(check)
							{
								col = -1;
								while (col < 0 || col >= board.getCol())
								{
									System.out.print("Enter a column to place piece: ");
									col = in.nextInt() - 1;
								}
								if(board.checkFullCol(col))
								{
									System.out.println("Column Full.");
								}
								else
								{
									check = false;
								}
							}
							check = true;
							row = board.placePiece(col);
							board.changeTurn();
						}
						if (board.checkWin(row,col, n))
						{
							if(-board.getTurn() == 1)
							{
								board.showBoardS();
								System.out.println("Congrats Player 1");
							}
							else
							{
								board.showBoardS();
								System.out.println("Congrats Player 2");
							}
							System.out.print("Wanna play Connect Four (yes or no): ");
							reply = in.next();
						}
		
						else
						{
							board.showBoardS();
							System.out.print("Wanna play Connect Four (yes or no): ");
							reply = in.next();
						}
						
						run = false;
					}
					else
					{
						System.out.println("Invalid answer.");
					}
				}
				run = true;
			}
			else if (reply.equals("no"))
			{
				System.out.println("End Game");
				end = false;
			}
			else
			{
				System.out.println("Invalid answer.");
				end = false;
			}
		}
	}

}
