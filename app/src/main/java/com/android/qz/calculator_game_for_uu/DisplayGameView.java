package com.android.qz.calculator_game_for_uu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DisplayGameView extends AppCompatActivity {
    public static final String EXTRA_SECOND_MESSAGE = "Go back to the first activity";
    Button goBackButton;

    public void goBack(View view) {
        goBackButton = (Button) findViewById(R.id.game_button);
        goBackButton.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        String message = "open the second the view ";
        intent.putExtra(EXTRA_SECOND_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_game_view);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.game_textView);
        textView.setText(message);
    }
}
