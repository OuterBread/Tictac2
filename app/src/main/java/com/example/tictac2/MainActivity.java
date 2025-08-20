package com.example.tictac2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCount = 0;
    private int pxs = 0;
    private int pos = 0;

    private String playerXName = "Player X";
    private String playerOName = "Player O";

    private TextView textViewStatus;
    private Button resetButton,resetScoreButton;
    private TextView playerXScore;
    private TextView playerOScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get player names from intent
        playerXName = getIntent().getStringExtra("PLAYER_X_NAME");
        playerOName = getIntent().getStringExtra("PLAYER_O_NAME");

        if (playerXName == null) playerXName = "Player X";
        if (playerOName == null) playerOName = "Player O";

        playerXScore = findViewById(R.id.text_view_X);
        playerOScore = findViewById(R.id.text_view_O);
        textViewStatus = findViewById(R.id.textViewStatus);
        resetButton = findViewById(R.id.button_R);
        resetScoreButton = findViewById(R.id.button_RRR);

        // Initialize buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnClickListener(v -> onButtonClick(finalI, finalJ));
            }
        }

        // Set initial scores and turn status
        updateScores();
        textViewStatus.setText(playerXName + "'s Turn");

        resetButton.setOnClickListener(v -> resetGame());
        resetScoreButton.setOnClickListener(v -> resetScore());
    }

    private void resetScore() {
        pxs = 0;
        pos =0 ;
        updateScores();
        resetGame();
    }

    private void onButtonClick(int i, int j) {
        if (!buttons[i][j].getText().toString().equals("")) {
            return; // Already clicked
        }

        buttons[i][j].setText(playerXTurn ? "X" : "O");
        roundCount++;

        if (checkForWin()) {
            if (playerXTurn) {
                pxs++;
                updateScores();
                showWinner(playerXName + " wins!");
            } else {
                pos++;
                updateScores();
                showWinner(playerOName + " wins!");
            }
        } else if (roundCount == 9) {
            showWinner("It's a draw!");
        } else {
            playerXTurn = !playerXTurn;
            textViewStatus.setText((playerXTurn ? playerXName : playerOName) + "'s Turn");
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                field[i][j] = buttons[i][j].getText().toString();

        // Rows and columns
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
                return true;
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
                return true;
        }

        // Diagonals
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
            return true;
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
            return true;

        return false;
    }

    private void showWinner(String winnerMessage) {
        textViewStatus.setText(winnerMessage);
        Toast.makeText(this, winnerMessage, Toast.LENGTH_SHORT).show();
        disableAllButtons();
        new Handler().postDelayed(this::resetGame, 2000);
    }

    private void disableAllButtons() {
        for (Button[] row : buttons)
            for (Button b : row)
                b.setEnabled(false);
    }

    private void resetGame() {
        for (Button[] row : buttons)
            for (Button b : row) {
                b.setText("");
                b.setEnabled(true);
            }

        roundCount = 0;
        playerXTurn = true;
        textViewStatus.setText(playerXName + "'s Turn");
    }

    private void updateScores() {
        playerXScore.setText(playerXName + ": " + pxs);
        playerOScore.setText(playerOName + ": " + pos);
    }
}
