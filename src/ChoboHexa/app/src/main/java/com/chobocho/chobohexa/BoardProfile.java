package com.chobocho.chobohexa;

public class BoardProfile {
    final static public String TAG = "BoardProfile";

    public static int screenW = 1080;
    public static int screenH = 1820;
    public static int startX = 0;
    public static int startY = 0;
    public static int buttonStartX = 0;
    public static int boardWidth = 7;
    public static int boardHeight = 16;
    public static int buttonHeight = 4;
    private int block_size = 80;

    public BoardProfile(int w, int h) {
        setScreenSize(w, h);
    }

    public int screenWidth() {
        return this.screenW;
    }

    public int screenHeight() {
        return this.screenH;
    }

    public void setScreenSize(int w, int h) {
        this.screenW = w;
        this.screenH = h;

        int widthBlockSize = (int) (w / (boardWidth+5));
        block_size = (int) (h / (boardHeight+buttonHeight*2+1));
        block_size = block_size <= widthBlockSize ? block_size : widthBlockSize;

        startX = (screenWidth() - block_size*(boardWidth + 3))/2;
        startY = block_size;
        buttonStartX = startX + block_size * 2;
    }

    public int blockSize() {
        return block_size;
    }

    public static int[] imageName = {

    };

    public int[] buttonImageName = {

    };
}
