
package dk.easv.tictactoe.bll;

import javafx.scene.layout.GridPane;

/**
 *
 * @author EASV
 */
public class GameBoard implements IGameBoard
{

    private static final int BOARD_EMPTY_STATE = 99;
    private static final int BOARD_PLAYER1 = 0;
    private static final int BOARD_PLAYER2 = 1;
    private static final int BOARD_SIZE = 3;
    private int activePlayer = 0;
    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int numberOfSteps = 0;

    /**
     * Returns 0 for player 0, 1 for player 1.
     *
     * @return int Id of the next player.
     */
    public int getNextPlayer()
    {
        if (activePlayer == BOARD_PLAYER1) {
            activePlayer = BOARD_PLAYER2;
            return BOARD_PLAYER1;
        }
        else {
            activePlayer = BOARD_PLAYER1;
            return BOARD_PLAYER2;
        }
    }

    /**
     * Attempts to let the current player play at the given coordinates. It the
     * attempt is succesfull the current player has ended his turn and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row)
    {
        if (board[col][row] == BOARD_EMPTY_STATE && !isGameOver()) {
            numberOfSteps++;
            if (activePlayer == BOARD_PLAYER1) {
                board[col][row] = BOARD_PLAYER1;
                return true;
            }
            else {
                board[col][row] = BOARD_PLAYER2;
                return true;
            }
        }
        else {
            System.out.println("FALSE - " + col + ":" + row + "-" + board[col][row] );
            return false;
        }
    }

    /**
     * Tells us if the game has ended either by draw or by meeting the winning
     * condition.
     *
     * @return true if the game is over, else it will retun false.
     */
    public boolean isGameOver()
    {
        int winning = 0;
        int checkForPlayer = BOARD_EMPTY_STATE;
        if (activePlayer == BOARD_PLAYER1) {
            checkForPlayer = BOARD_PLAYER2;
        }
        else
            checkForPlayer = BOARD_PLAYER1;
        if (checkRows(checkForPlayer) || checkColumn(checkForPlayer) || checkDiagon(checkForPlayer)) {
            System.out.println("Winner is Player " + checkForPlayer);
            return true;
        }
        if (numberOfSteps == BOARD_SIZE*BOARD_SIZE) {
            System.out.println("GAME OVER! Board is full...");
            activePlayer = -1;
            return true;
        }
        else
            return false;
    }

    private boolean checkDiagon(int checkForPlayer) {
        return (board[0][0] == checkForPlayer && board[1][1] == checkForPlayer && board[2][2] == checkForPlayer) ||
                (board[0][2] == checkForPlayer && board[1][1] == checkForPlayer && board[2][0] == checkForPlayer);
    }

    private boolean checkColumn(int checkForPlayer) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == checkForPlayer && board[i][1] == checkForPlayer && board[i][2] == checkForPlayer) {
                System.out.println("checkColumn is true");
                return true;
            }
        }
        return false;
    }

    private boolean checkRows(int checkForPlayer) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[0][i] == checkForPlayer && board[1][i] == checkForPlayer && board[2][i] == checkForPlayer) {
                System.out.println("checkRows is true");
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */
    public int getWinner()
    {
        if (activePlayer == BOARD_PLAYER1) {
            return BOARD_PLAYER2;
        }
        else if (activePlayer == BOARD_PLAYER2) {
            return BOARD_PLAYER1;
        }
        else return -1;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = BOARD_EMPTY_STATE;
            }
        activePlayer = BOARD_PLAYER1;
        numberOfSteps = 0;
        }
    }
}
