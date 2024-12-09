
package dk.easv.tictactoe.bll;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author EASV
 */
public class GameBoard implements IGameBoard
{

    private static final int BOARD_EMPTY_STATE = 99;
    private static final int BOARD_PLAYER1 = 0;
    private static final int BOARD_PLAYER2 = 1;
    private static int BOARD_SIZE = 3;
    private static final int FOR_WIN = 3;
    private int activePlayer = 0;
    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int numberOfSteps = 0;
    private int numberOfPlayers = 2;

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
            }
            else {
                board[col][row] = BOARD_PLAYER2;
            }
            return true;
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
        if (checkRows(BOARD_PLAYER1) || checkColumn(BOARD_PLAYER1) || checkDiagon(BOARD_PLAYER1)) {
            activePlayer = BOARD_PLAYER1;
            System.out.println("Winner is Player " + activePlayer);
            return true;
        } else if (checkRows(BOARD_PLAYER2) || checkColumn(BOARD_PLAYER2) || checkDiagon(BOARD_PLAYER2)) {
            activePlayer = BOARD_PLAYER2;
            System.out.println("Winner is Player " + activePlayer);
            return true;
        }
        else if (numberOfSteps == BOARD_SIZE*BOARD_SIZE) {
            System.out.println("GAME OVER! Board is full...");
            activePlayer = -1;
            return true;
        }
        else
            return false;
    }

    private boolean checkDiagon(int checkForPlayer) {
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = 0; j <= BOARD_SIZE - FOR_WIN; j++) {
                if (board[i][j] == checkForPlayer &&
                        board[i + 1][j + 1] == checkForPlayer &&
                        board[i + 2][j + 2] == checkForPlayer) {
                    return true;
                }
            }
        }

        // Check all anti-diagonals (top-right to bottom-left)
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = FOR_WIN - 1; j < BOARD_SIZE; j++) {
                if (board[i][j] == checkForPlayer &&
                        board[i + 1][j - 1] == checkForPlayer &&
                        board[i + 2][j - 2] == checkForPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColumn(int checkForPlayer) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= (BOARD_SIZE-FOR_WIN); j++) {
                if (board[i][j] == checkForPlayer &&
                        board[i][j + 1] == checkForPlayer &&
                        board[i][j + 2] == checkForPlayer) {
                    System.out.println("checkColumn is true");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRows(int checkForPlayer) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= (BOARD_SIZE-FOR_WIN); j++) {
                if (board[j][i] == checkForPlayer && board[j + 1][i] == checkForPlayer && board[j + 2][i] == checkForPlayer) {
                    System.out.println("checkRows is true");
                    return true;
                }
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
            return BOARD_PLAYER1;
        }
        else if (activePlayer == BOARD_PLAYER2) {
            return BOARD_PLAYER2;
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

    public int getBoardSize() {
        return BOARD_SIZE;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public void setBoardSize(int boardSize) {
        BOARD_SIZE = boardSize;
    }

    public int[] computerIsPlaying() {
        int[] blocking = blockingPlayer(BOARD_PLAYER1);
        int[] winning = blockingPlayer(BOARD_PLAYER2);
        int[] random = computerRandomPlay();
        if (winning != null) {
            if (play(winning[0], winning[1])) {  // Ensure the play is valid
                System.out.println("Computer played (W): " + winning[0] + "-" + winning[1]);
                return winning;
            }
        }
        else if (blocking != null) {
            if (play(blocking[0], blocking[1])) {  // Ensure the play is valid
                System.out.println("Computer played (B): " + blocking[0] + "-" + blocking[1]);
                return blocking;
            }
        }
        else if (random != null) {
            if (play(random[0], random[1])) {  // Ensure the play is valid
                System.out.println("Computer played (R): " + random[0] + "-" + random[1]);
                return random;
            }
        }
        return null;
    }
    private int[] blockingPlayer(int playerToPlay) {
    // FOR COLUMN
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= (BOARD_SIZE-FOR_WIN); j++) {
                if (board[i][j] == playerToPlay &&
                        board[i][j + 1] == playerToPlay &&
                        board[i][j + 2] == BOARD_EMPTY_STATE)
                    return new int[]  {i, (j+2)};
                    else if (board[i][j] == BOARD_EMPTY_STATE &&
                                board[i][j + 1] == playerToPlay &&
                                board[i][j + 2] == playerToPlay) {
                    return new int[] {i, (j)};
                }
            }
        }

        // FOR ROWS

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= BOARD_SIZE-FOR_WIN; j++) {
                if (board[j][i] == playerToPlay
                        && board[j + 1][i] == playerToPlay
                        && board[j + 2][i] == BOARD_EMPTY_STATE) {

                    return new int[] {(j+2), i};
                }
                else if (board[j][i] == BOARD_EMPTY_STATE
                        && board[j + 1][i] == playerToPlay
                        && board[j + 2][i] == playerToPlay) {
                    return new int[] {(j), (i)};
                }
            }
        }
        // Check for diagonal
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = 0; j <= BOARD_SIZE - FOR_WIN; j++) {
                if (board[i][j] == BOARD_EMPTY_STATE &&
                        board[i + 1][j + 1] == playerToPlay &&
                        board[i + 2][j + 2] == playerToPlay) {
                    return new int[] {i, j};
                }
            }
        }// Check for diagonal
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = 0; j <= BOARD_SIZE - FOR_WIN; j++) {
                if (board[i][j] == playerToPlay &&
                        board[i + 1][j + 1] == BOARD_EMPTY_STATE &&
                        board[i + 2][j + 2] == playerToPlay) {
                    return new int[] {i+1, j+1};
                }
            }
        }
        // Check for diagonal
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = 0; j <= BOARD_SIZE - FOR_WIN; j++) {
                if (board[i][j] == playerToPlay &&
                        board[i + 1][j + 1] == playerToPlay &&
                        board[i + 2][j + 2] == BOARD_EMPTY_STATE) {
                    return new int[] {i+2, j+2};
                }
            }
        }
        // Check all anti-diagonals (top-right to bottom-left) top one is empty field
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = FOR_WIN - 1; j < BOARD_SIZE; j++) {
                if (board[j][i] == playerToPlay &&
                        board[j - 1][i + 1] == playerToPlay &&
                        board[j - 2][i + 2] == BOARD_EMPTY_STATE) {
                    return new int[]{j-2, i+2};
                }
            }
        }
        // Check all anti-diagonals (top-right to bottom-left) middle one is empty field
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = FOR_WIN - 1; j < BOARD_SIZE; j++) {
                if (board[j][i] == playerToPlay &&
                        board[j - 1][i + 1] == BOARD_EMPTY_STATE &&
                        board[j - 2][i + 2] == playerToPlay) {
                    return new int[]{j-1, i+1};
                }
            }
        }

        // Check all anti-diagonals (top-right to bottom-left) bottom one is empty field
        for (int i = 0; i <= BOARD_SIZE - FOR_WIN; i++) {
            for (int j = FOR_WIN - 1; j < BOARD_SIZE; j++) {
                if (board[j][i] == BOARD_EMPTY_STATE &&
                        board[j - 1][i + 1] == playerToPlay &&
                        board[j - 2][i + 2] == playerToPlay) {
                    return new int[]{j, i};
                }
            }
        }
        return null;
    }

    private int[] computerRandomPlay() {
        Random random = new Random();
        ArrayList<int[]> freeArray = new ArrayList<>();

        // Collect all available (empty) positions on the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == BOARD_EMPTY_STATE) {
                    freeArray.add(new int[]{i, j});
                }
            }
        }
        // Check if there are available moves and it's the computer's turn
        if (!freeArray.isEmpty() && numberOfSteps > 0 && numberOfSteps % 2 == 1) {
            int randomNumber = random.nextInt(freeArray.size());  // Use size of freeArray for the random index
            int[] nextStep = freeArray.get(randomNumber);         // Get random available position
            activePlayer = BOARD_PLAYER2;
            return nextStep;
            } else
                return null;  // Return empty array if no move was made
    }

    public int changeNumberOfPlayers() {
        if (numberOfPlayers == 2) {
            numberOfPlayers = 1;
            return 2;
        }
        else {
            numberOfPlayers = 2;
            return 1;
        }
    }
    }

