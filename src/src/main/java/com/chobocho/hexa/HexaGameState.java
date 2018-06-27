package com.chobocho.hexa;

public abstract class HexaGameState {
    protected IHexa hexa;

    public HexaGameState() {
    }

    public void init() {

    }

    public void rotate() {
        // TODO implement here
    }

    public void moveLeft() {
        // TODO implement here
    }

    public void moveRight() {
        // TODO implement here
    }

    public void moveDown() {
        // TODO implement here
    }

    public void fixCurrentBlock() {

    }

    public void moveBottom() {
        // TODO implement here
    }

    public void updateBlock() {
    }

    public boolean gameOver() {
        return false;
    }

    public void updateBoard() {

    }

    public HexaBlock getCurrentHexaBlock() {
        return null;
    }

    public HexaBlock getNextHexaBlock() {
        return null;
    }

    public void update() {
        if (hexa != null) {
            hexa.update();
        }
    }

    public boolean isIdleState() { return false; }
    public boolean isGameOverState() { return false; }
    public boolean isPlayState() { return false; }
    public boolean isPauseState() { return false; }
}