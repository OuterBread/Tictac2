package com.example.tictac2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCount = 0;
    private int pxs=0;
    private int pos=0;
    private TextView textViewStatus;
    private Button resetButton;
    private TextView playerXScore;
    private TextView playerOScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOScore= findViewById(R.id.text_view_O);
        playerXScore= findViewById(R.id.text_view_X);

        textViewStatus = findViewById(R.id.textViewStatus);
        resetButton = findViewById(R.id.button_R);

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

        resetButton.setOnClickListener(v -> resetGame());
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
                playerXScore.setText("Player X:" + pxs);
                showWinner("Player X wins!");
            } else {
                pos++;
                playerOScore.setText("Player O:"+ pos);
                showWinner("Player O wins!");
            }
        } else if (roundCount == 9) {
            showWinner("It's a draw!");
        } else {
            playerXTurn = !playerXTurn;
            textViewStatus.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");
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

    private void showWinner(String winner) {
        textViewStatus.setText(winner);
        disableAllButtons();
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
        textViewStatus.setText("Player X's Turn");
    }
}
