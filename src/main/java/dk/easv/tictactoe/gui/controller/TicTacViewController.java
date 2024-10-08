
package dk.easv.tictactoe.gui.controller;

// Java imports
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dk.easv.tictactoe.gui.TicTacToe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

// Project imports
import dk.easv.tictactoe.bll.GameBoard;
import dk.easv.tictactoe.bll.IGameBoard;
import javafx.stage.Stage;

/**
 *
 * @author EASV
 */
public class TicTacViewController implements Initializable
{
    @FXML
    private Label lblPlayer;

    @FXML
    private MenuItem btnNewGame;

    @FXML
    private MenuItem menuSize;

    @FXML
    private GridPane gridPane;
    
    private static final String TXT_PLAYER = "Player: ";
    private IGameBoard game;

    /**
     * Event handler for the grid buttons
     *
     * @param event
     */
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        try
        {
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;
            if (game.play(c, r))
            {
                int player = game.getNextPlayer();
                Button btn = (Button) event.getSource();
                String xOrO = player == 0 ? "X" : "O";
                btn.setText(xOrO);
                if (game.isGameOver())
                {
                    displayWinner(game.getWinner());
                }
                else
                {
                        setPlayer();
                }
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Event handler for starting a new game
     *
     * @param event
     */
    @FXML
    private void handleNewGame(ActionEvent event)
    {
        game.newGame();
        setPlayer();
        clearBoard();
    }

    /**
     * Initializes a new controller
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param rb
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        game = new GameBoard();
        game.newGame();
        setPlayer();
    }

    /**
     * Set the next player
     */
    private void setPlayer()
    {
        int displayPlayer = game.getNextPlayer();
        displayPlayer++;
        lblPlayer.setText(TXT_PLAYER + displayPlayer);
        game.getNextPlayer();

    }

    /**
     * Finds a winner or a draw and displays a message based
     * @param winner
     */
    private void displayWinner(int winner)
    {
        String message = "";
        System.out.println("Displaywinner: " + winner);
        switch (winner)
        {
            case -1:
                message = "The only winning move is\n not to play.";
                break;
            default:
                message = "Player " + ++winner + " wins!!!";
                break;
        }
        lblPlayer.setText(message);
    }

    /**
     * Clears the game board in the GUI
     */
    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
        }
    }

    public void changeBoardSize(ActionEvent event) throws IOException {
        try {
            GameBoard boardClass = new GameBoard();
            int board = boardClass.getBoardSize();
            FXMLLoader fxmlLoader;
            if (board == 3) {
                fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("/views/TicTacView5.fxml"));
                boardClass.setBoardSize(5);

            } else {
                fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("/views/TicTacView.fxml"));
                boardClass.setBoardSize(3);
            }
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) gridPane.getScene().getWindow();

            // Set the scene to the new root loaded from FXML
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Optionally set the title and show the stage
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
