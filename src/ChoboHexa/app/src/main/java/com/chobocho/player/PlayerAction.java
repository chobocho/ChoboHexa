package com.chobocho.player;

import android.graphics.Rect;

import com.chobocho.chobohexa.BoardProfile;
import com.chobocho.hexa.Hexa;

public class PlayerAction implements IPlayerAction {
    IPlayer player = null;
    BoardProfile boardProfile;
    final static int BOARD_WIDTH = 7;
    final static int BOARD_HEIGHT = 16;

    HexaButton bottomArrow;
    HexaButton leftArrow;
    HexaButton rightArrow;
    HexaButton rotateArrow;
    HexaButton downArrow;
    HexaButton playArrow;
    HexaButton pauseArrow;
    HexaButton playButton;

    public PlayerAction(BoardProfile profile) {
        boardProfile = profile;
        initButton();
    }

    private void initButton() {
        int startX = boardProfile.startX;
        int startY = boardProfile.startY;
        int BLOCK_IMAGE_SIZE = boardProfile.blockSize();

        bottomArrow = new HexaButton("bottom", 1,
                (int) (startX + BLOCK_IMAGE_SIZE * 2.5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        leftArrow = new HexaButton("leftArrow", 2,
                (int)(startX),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        downArrow = new HexaButton("downArrow", 3,
                (int)(startX + BLOCK_IMAGE_SIZE * 2.5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        rotateArrow = new HexaButton("rotatArrow", 4,
                (int)(startX + BLOCK_IMAGE_SIZE * 5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        rightArrow = new HexaButton("RightArrow", 5,
                (int)(startX + BLOCK_IMAGE_SIZE * 7.5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        playArrow = new HexaButton("playArrow", 6,
                (int)(startX + BLOCK_IMAGE_SIZE * 7.5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        pauseArrow = new HexaButton("pauseArrow", 7,
                (int)(startX + BLOCK_IMAGE_SIZE * 7.5),
                (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)),
                (int)(BLOCK_IMAGE_SIZE*2.5),
                (int)(BLOCK_IMAGE_SIZE*2.5));

        playButton = new HexaButton("playButton", 8,
                (int)(boardProfile.buttonStartX),
                (int)(BLOCK_IMAGE_SIZE * 7),
                (int)(BLOCK_IMAGE_SIZE * 6),
                (int)(BLOCK_IMAGE_SIZE * 3));
    }

    public boolean onTouchScreen(int ex, int ey) {
        int startX = 80;
        int startY = 80;

        if (player.isGameOverState()) {
            if (playButton.in(ex, ey)) {
                //if (highScore < player.getScore()) {
                //    highScore = player.getScore();
                //    saveScore();
                //}
                onKeyEvent(Player.START);
            }
            return true;
        }

        else if (player.isIdleState()) {
            if (playButton.in(ex, ey)) {
                onKeyEvent(Player.START);
                player.startGame();
            }
            return true;
        }

        else if (player.isPauseState()) {
           if (playArrow.in(ex,ey)) {
                onKeyEvent(Player.RESUME);
                player.startGame();
            } else if (playButton.in(ex, ey)) {
               onKeyEvent(Player.RESUME);
               player.startGame();
            }
            return true;
        }

        else if (player.isPlayState()) {
            if (pauseArrow.in(ex,ey)) {
                onKeyEvent(Player.PAUSE);
                return true;
            }
        }

        if (leftArrow.in(ex, ey)) {
            if(player != null) {
                onKeyEvent(Player.LEFT);
            }
            return true;
        }

        else if (bottomArrow.in(ex, ey)) {
            if (player != null) {
                onKeyEvent(Player.BOTTOM);
                onKeyEvent(Player.DOWN);
            }
            return true;
        }

        else if (rotateArrow.in(ex, ey)) {
            if (player != null) {
                onKeyEvent(Player.ROTATE);
            }
            return true;
        }

        else if (rightArrow.in(ex, ey)) {
            if (player != null) {
                onKeyEvent(Player.RIGHT);
            }
            return true;
        }

        else if (downArrow.in(ex, ey)) {
            if (player != null) {
                player.moveDown();
            }
            return true;
        }
        return false;
    }

    public boolean onKeyEvent(int keycode) {
        System.out.println("Hexa (d) Player1 Press key : " + keycode);

        if (player == null) {
            return true;
        }

        if (player.isIdleState()) {
            if (keycode == Player.START){
                System.out.println("Start!");
                player.play();
            }
            return true;
        }

        if (player.isGameOverState()) {
            if (keycode == Player.START){
                player.saveScore();
                player.init();
            }
            return true;
        }

        if (player.isPauseState()) {
            if (keycode == Player.RESUME){
                player.resume();
            }
            return true;
        }

        if (player.isPlayState() == false) {
            return true;
        }

        switch (keycode) {
            case Player.LEFT:
                player.moveLeft();
                break;
            case Player.RIGHT:
                player.moveRight();
                break;
            case Player.DOWN:
                player.moveDown();
                break;
            case Player.ROTATE:
                player.rotate();
                break;
            case Player.BOTTOM:
                player.moveBottom();
                break;
            case Player.PAUSE:
                player.pause();
                break;
        }
        return true;
    }

    public void setPlayer(IPlayer player) {
        this.player = player;
    }

}
