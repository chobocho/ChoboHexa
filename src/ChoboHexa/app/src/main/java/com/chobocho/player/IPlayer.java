package com.chobocho.player;

import android.graphics.Canvas;

public interface IPlayer {
    public void onDraw(Canvas gt);
    public boolean onPressKey(int k);
    public boolean onTouchScreen(int x, int y);

    public void init();
    public void moveLeft();
    public void moveRight();
    public void moveDown();
    public void rotate();
    public void moveBottom();
    public void play();
    public void pause();
    public void resume();

    public void startGame();
    public int getScore();
    public int getHighScore();
    public void setHighScore(int score);
    public void saveScore();

    public boolean isIdleState();
    public boolean isGameOverState();
    public boolean isPlayState();
    public boolean isPauseState();
}
