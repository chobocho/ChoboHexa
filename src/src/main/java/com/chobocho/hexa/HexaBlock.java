package com.chobocho.hexa;

public class HexaBlock {
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected int type;
    protected final int numOfBlockType = 7;
    protected int[] block;


    public HexaBlock() {
        this.block = new int[3];
        this.block[0] = (int) (Math.random() * 7)+1;
        this.block[1] = (int) (Math.random() * 7)+1;
        this.block[2] = (int) (Math.random() * 7)+1;

        x = 3;
        y = 0;
        w = 1;
        h = 3;
    }

    public void rotate() {
        int block = this.block[0];
        this.block[0] = this.block[1];
        this.block[1] = this.block[2];
        this.block[2] = block;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public void moveUp() {
        y--;
    }

    public int[] getBlock() {
        return block;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}