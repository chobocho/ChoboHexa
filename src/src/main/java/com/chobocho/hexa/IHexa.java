package com.chobocho.hexa;

/**
 * 
 */
public interface IHexa {
    public void init();

    public void moveLeft();

    public void moveRight();

    public void moveDown();

    public void rotate();

    public void moveBottom();

    public void play();

    public void pause();

    public void resume();

    public void update();

    public int getWidth();

    public int getHeight();

    public void register(IHexaObserver observer);

    public int[][] getBoard();

    public int getScore();

    public IHexaObserver getObserver();

    public HexaBlock getCurrentBlock();

    public HexaBlock getNextBlock();

    public int addSore(int score);

    public boolean isIdleState();
    public boolean isGameOverState();
    public boolean isPlayState();
    public boolean isPauseState();

}