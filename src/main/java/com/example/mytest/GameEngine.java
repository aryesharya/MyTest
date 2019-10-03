package com.example.mytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    final static String TAG="MY-TEST";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    // represent the TOP LEFT CORNER OF THE GRAPHIC

    int playerXPosition;
    int playerYPosition;
    Bitmap playerImage;
    Rect playerHitbox;

    Player player;
    Item item1;
    Item item2;
    Item item3;
    Item item4;
    int racketXPosition;  // top left corner of the racket
    int racketYPosition;  // top left corner of the racket

    int racketXPosition1;  // top left corner of the racket
    int racketYPosition1;  // top left corner of the racket


    int racketXPosition2;  // top left corner of the racket
    int racketYPosition2;  // top left corner of the racket


    int racketXPosition3;  // top left corner of the racket
    int racketYPosition3;  // top left corner of the racket
    // ----------------------------
    // ## GAME STATS
    // ----------------------------


    int lives = 10;

    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();

        this.player = new Player(getContext(), 900, 300);
        this.item1 = new Item(getContext(), 60, 120);
        this.item2 = new Item(getContext(), 60, 420);
        this.item3 = new Item(getContext(), 60, 720);
        this.item4 = new Item(getContext(), 60, 1020);

    }



    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    String personTapped="";

    public void updatePositions() {


        if (this.fingerAction == "down") {
            // then move player up
            // Make the UP movement > than down movement - this will
            // make it look like the player is moving up
            this.playerYPosition = this.playerYPosition + 300;

            this.playerHitbox.left  = this.playerXPosition;
            this.playerHitbox.top = this.playerYPosition;
            this.playerHitbox.right  = this.playerXPosition + this.playerImage.getWidth();
            this.playerHitbox.bottom = this.playerYPosition + this.playerImage.getHeight();
        }

        if (this.fingerAction == "up") {
            // then move player up
            // Make the UP movement > than down movement - this will
            // make it look like the player is moving up
            this.playerYPosition = this.playerYPosition - 100;

            this.playerHitbox.left  = this.playerXPosition;
            this.playerHitbox.top = this.playerYPosition;
            this.playerHitbox.right  = this.playerXPosition + this.playerImage.getWidth();
            this.playerHitbox.bottom = this.playerYPosition + this.playerImage.getHeight();
        }



        // restart the racket position
        this.racketXPosition = 50;
        this.racketYPosition = 1100;


        this.racketXPosition1 = 50;
        this.racketYPosition1 = 800;


        this.racketXPosition2 = 50;
        this.racketYPosition2 = 500;


        this.racketXPosition3 = 50;
        this.racketYPosition3 = 200;



        /*if (personTapped.contentEquals("down")){
            this.player = this.player + 30;
        }
        else if (personTapped.contentEquals("up")){
            this.player = this.player - 40;
        }

         */

        lives = lives - 1;





    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            paintbrush.setColor(Color.BLACK);
            this.canvas.drawRect(this.racketXPosition,
                    this.racketYPosition,
                    this.racketXPosition + 800,     // 400 is width of racket
                    this.racketYPosition + 50,    // 50 is height of racket
                    paintbrush);
            paintbrush.setColor(Color.WHITE);


            paintbrush.setColor(Color.BLACK);
            this.canvas.drawRect(this.racketXPosition1,
                    this.racketYPosition1,
                    this.racketXPosition1 + 800,     // 400 is width of racket
                    this.racketYPosition1 + 50,    // 50 is height of racket
                    paintbrush);
            paintbrush.setColor(Color.WHITE);



            paintbrush.setColor(Color.BLACK);
            this.canvas.drawRect(this.racketXPosition2,
                    this.racketYPosition2,
                    this.racketXPosition2 + 800,     // 400 is width of racket
                    this.racketYPosition2 + 50,    // 50 is height of racket
                    paintbrush);
            paintbrush.setColor(Color.WHITE);


            paintbrush.setColor(Color.BLACK);
            this.canvas.drawRect(this.racketXPosition3,
                    this.racketYPosition3,
                    this.racketXPosition3 + 800,     // 400 is width of racket
                    this.racketYPosition3 + 50,    // 50 is height of racket
                    paintbrush);
            paintbrush.setColor(Color.WHITE);






            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);

            canvas.drawBitmap(player.getImage(), player.getxPosition(), player.getyPosition(), paintbrush);
            // draw the player's hitbox
            canvas.drawRect(player.getHitbox(), paintbrush);

            canvas.drawBitmap(item1.getImage(), item1.getxPosition(), item1.getyPosition(), paintbrush);
            // draw the player's hitbox
            canvas.drawRect(item1.getHitbox(), paintbrush);

            canvas.drawBitmap(item2.getImage(), item2.getxPosition(), item2.getyPosition(), paintbrush);
            // draw the player's hitbox
            canvas.drawRect(item2.getHitbox(), paintbrush);

            canvas.drawBitmap(item3.getImage(), item3.getxPosition(), item3.getyPosition(), paintbrush);
            // draw the player's hitbox
            canvas.drawRect(item3.getHitbox(), paintbrush);

            canvas.drawBitmap(item4.getImage(), item4.getxPosition(), item4.getyPosition(), paintbrush);
            // draw the player's hitbox
            canvas.drawRect(item4.getHitbox(), paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);


            // DRAW GAME STATS
            // -----------------------------
            paintbrush.setColor(Color.BLACK);
            paintbrush.setTextSize(60);
            canvas.drawText("Lives remaining: " + lives,
                    1000,
                    200,
                    paintbrush
            );

        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            float fingerXPosition = event.getX();
            float fingerYPosition = event.getY();


            int middleOfScreen = this.screenHeight / 2;
            if (fingerXPosition <= middleOfScreen) {

                fingerAction = "down";
            }
            else if (fingerXPosition > middleOfScreen) {

                fingerAction = "up";
            }

        }
        else if (userAction == MotionEvent.ACTION_UP) {

        }

        return true;
    }
}