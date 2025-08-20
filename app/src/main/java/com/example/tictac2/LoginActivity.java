package com.example.tictac2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextPlayerX, editTextPlayerO;
    private Button buttonStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPlayerX = findViewById(R.id.editTextPlayerX);
        editTextPlayerO = findViewById(R.id.editTextPlayerO);
        buttonStartGame = findViewById(R.id.buttonStartGame);

        buttonStartGame.setOnClickListener(v -> {
            String playerX = editTextPlayerX.getText().toString().trim();
            String playerO = editTextPlayerO.getText().toString().trim();

            if (playerX.isEmpty()) playerX = "Player X";
            if (playerO.isEmpty()) playerO = "Player O";

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("PLAYER_X_NAME", playerX);
            intent.putExtra("PLAYER_O_NAME", playerO);
            startActivity(intent);
            finish();
        });
    }
}
