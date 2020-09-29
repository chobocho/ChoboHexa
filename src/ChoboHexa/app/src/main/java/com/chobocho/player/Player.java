package com.chobocho.player;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.chobocho.chobohexa.BoardProfile;
import com.chobocho.chobohexa.HexaViewForN8;
import com.chobocho.hexa.Hexa;
import com.chobocho.hexa.IHexa;
import com.chobocho.hexa.IHexaObserver;


public class Player implements IPlayer,IHexaObserver {
    final static int BOARD_WIDTH = 7;
    final static int BOARD_HEIGHT = 16;

    public static final int START = 21;
    public static final int PAUSE = 22;
    public static final int RESUME = 23;
    public static final int LEFT = 6;
    public static final int RIGHT = 4;
    public static final int DOWN = 2;
    public static final int ROTATE = 5;
    public static final int BOTTOM =10;

    private int highScore = 0;

    IHexa hexa;
    IPlayerDraw playerDraw;
    IPlayerAction playerAction;
    HexaViewForN8 board;
    BoardProfile boardProfile;

    private int     gameSpeed = 0;

    public Player(HexaViewForN8 board, BoardProfile profile, IPlayerDraw playerDraw, IPlayerAction playerAction) {
        this.board = board;
        this.boardProfile = profile;

        this.hexa = new Hexa(BOARD_WIDTH, BOARD_HEIGHT);
        this.hexa.register(this);
        this.hexa.init();

        this.playerDraw = playerDraw;
        this.playerDraw.setHexa(this.hexa);

        this.playerAction = playerAction;
        this.playerAction.setPlayer(this);

        this.highScore = 0;
    }

    private void initButton() {
        int startX = boardProfile.startX;
        int startY = boardProfile.startY;
        int BLOCK_IMAGE_SIZE = boardProfile.blockSize();

        HexaButton leftArrow = new HexaButton("LeftArrow", 1,
                (int) (startX + BLOCK_IMAGE_SIZE * 2.5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        HexaButton rightArrow = new HexaButton("RightArrow", 1,
                (int) (startX + BLOCK_IMAGE_SIZE * 7.5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

    }
    public void onDraw(Canvas g) {
        playerDraw.onDraw(g);
    }

    public boolean onPressKey(int k) {
        return playerAction.onKeyEvent(k);
    }

    public boolean onTouchScreen(int x, int y) {
        return playerAction.onTouchScreen(x, y);
    }

    public void init() {
        hexa.init();
    }

    public void moveLeft() {
        hexa.moveLeft();
    }

    public void moveRight() {
        hexa.moveRight();
    }

    public void moveDown() {
        hexa.moveDown();
    }

    public void rotate() {
        hexa.rotate();
    }

    public void moveBottom() {
        hexa.moveBottom();
        hexa.moveDown();
    }

    public void play() {
        hexa.play();
    }

    public void pause() {
        hexa.pause();
    }

    public void resume() {
        hexa.resume();
    }

    public void update() {
        this.board.update();
    }
    public void startGame() { this.board.startGame(); }
    public void saveScore() {
        if (this.highScore < hexa.getScore()) {
            this.highScore = hexa.getScore();
            this.board.saveScore();
        }
    }

    public int getScore() { return hexa.getScore(); }
    public int getHighScore() { return this.highScore; }
    public void setHighScore(int score) { this.highScore = score; }


    public boolean isIdleState() {
        return hexa.isIdleState();
    }

    public boolean isGameOverState() {
        return hexa.isGameOverState();
    }

    public boolean isPlayState() {
        return hexa.isPlayState();
    }

    public boolean isPauseState() {
        return hexa.isPauseState();
    }
}
