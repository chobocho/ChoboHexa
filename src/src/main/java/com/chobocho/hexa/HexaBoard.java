package com.chobocho.hexa;

import android.graphics.Point;
import java.io.IOException;
import java.util.ArrayList;

public class HexaBoard {
    private int width;
    private int height;
    private IHexa hexa;
    private int[][] board;
    private ArrayList<Point> removeBlockList;
    /**
     * Default constructor
     */
    public HexaBoard(int width, int height, IHexa hexa) {
        this.width = width;
        this.height = height;
        this.hexa = hexa;
        this.removeBlockList = new ArrayList<Point>();

        board = new int[this.height][this.width];
        init();
    }

    public void init() {
       for (int i = 0; i < this.getHeight(); i++) {
           for (int j = 0; j < this.getWidth(); j++) {
               board[i][j] = 0;
           }
       }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isAcceptable(HexaBlock hexablock) {
        int[] block = hexablock.getBlock();
        int w = hexablock.getWidth();
        int h = hexablock.getHeight();
        int x = hexablock.getX();
        int y = hexablock.getY();

        HexaLog.d("W : " + w + " H : " + h);
        HexaLog.d("X : " + x + " Y : " + y);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (x < 0 || (x + j) > (width - 1) || (y + i) > (height - 1) || y < 0) {
                    return false;
                }
                if (board[y + i][x + j] != Hexa.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    public void addHexaBlock(HexaBlock hexaBlock) {
        int[] block = hexaBlock.getBlock();
        int w = hexaBlock.getWidth();
        int h = hexaBlock.getHeight();
        int x = hexaBlock.getX();
        int y = hexaBlock.getY();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                board[i + y][j + x] = block[i];
            }
        }
    }

    public int arrange() {

        int removedBlock = 0;
        HexaBlock currentBlock = hexa.getCurrentBlock();

        removeBlockList.clear();
        for (int i = 0; i < 3; i++) {
             checkRemoveBlock(currentBlock.x, currentBlock.y+i);
        }

        while (removeBlockList.size() >= 3) {
            removedBlock += removeBlock();
            checkRemoveBlock();
        }

        return removedBlock;
    }

    private int removeBlock() {
        int removedBlock = 0;

        removeBlockList.stream().forEach(p -> {
             board[p.y][p.x] = -1;
        });

        for (int x = 0; x < this.width; x++) {
            for (int y = this.height-1; y > 0; y--) {
                if (board[y][x] == -1) {
                    for (int k = y; k > 0; k--) {
                        board[k][x] = board[k-1][x];
                    }
                    y++;
                    board[0][x] = Hexa.EMPTY;
                    removedBlock++;
                }
            }
        }
        removeBlockList.clear();
        return removedBlock;
    }

    private int checkRemoveBlock() {
        int removedBlock = 0;

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (board[y][x] != Hexa.EMPTY) {
                    removedBlock += checkRemoveBlock4Way(x, y);
                }
            }
        }

        return removedBlock;
    }

    private int checkRemoveBlock(int x_, int y_) {
        int removedBlock = 0;

        for (int x = x_-2; x <= x_+2; x++) {
            for (int y = y_-2; y <= y_+2; y++) {
                if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
                    continue;
                }
                if (board[y][x] != Hexa.EMPTY) {
                    removedBlock += checkRemoveBlock4Way(x, y);
                }
            }
        }
        return removedBlock;
    }


    private int checkRemoveBlock4Way(int x, int y) {
        HexaLog.d("checkRemoveBlock X: " + x + " Y: " + y);

        // O##
        if ((x+2) < this.width && board[y][x] == board[y][x+1] && board[y][x+1] == board[y][x+2]) {
            removeBlockList.add(new Point(x, y));
            removeBlockList.add(new Point(x+1, y));
            removeBlockList.add(new Point(x+2, y));
        }

        //  O
        //  #
        //  #
        if (y+2 < this.height && board[y][x] == board[y+1][x] && board[y+1][x] == board[y+2][x]) {
            removeBlockList.add(new Point(x, y));
            removeBlockList.add(new Point(x, y+1));
            removeBlockList.add(new Point(x, y+2));
        }

        //  O
        //  #0
        //  ##0
        if (x+2 < this.width && y+2 < this.height && board[y][x] == board[y+1][x+1] && board[y+1][x+1] == board[y+2][x+2]) {
            removeBlockList.add(new Point(x, y));
            removeBlockList.add(new Point(x+1, y+1));
            removeBlockList.add(new Point(x+2, y+2));
        }

        //  ##0
        //  #0
        //  0
        if (x-2 >= 0 && y+2 < this.height && board[y][x] == board[y+1][x-1] && board[y+1][x-1] == board[y+2][x-2]) {
            removeBlockList.add(new Point(x, y));
            removeBlockList.add(new Point(x-1, y+1));
            removeBlockList.add(new Point(x-2, y+2));
        }


        HexaLog.d("count : " + removeBlockList.size());
        return removeBlockList.size();
    }

}