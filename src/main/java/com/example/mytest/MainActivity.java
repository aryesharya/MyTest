package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    GameEngine mytest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize the GameEngine object
        // Pass it the screen size (height & width)
        mytest = new GameEngine(this, size.x, size.y);

        // Make GameEngine the view of the Activity
        setContentView(mytest);
    }

    // Android Lifecycle function
    @Override
    protected void onResume() {
        super.onResume();
        mytest.startGame();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        mytest.pauseGame();
    }
}
