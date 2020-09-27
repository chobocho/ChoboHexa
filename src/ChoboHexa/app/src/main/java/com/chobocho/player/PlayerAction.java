package com.chobocho.player;

public class PlayerAction implements IPlayerAction {
   IPlayer player = null;

    public boolean onTouchScreen(int ex, int ey) {
        int startX = 80;
        int startY = 80;

        if (player.isGameOverState()) {
            if ((ex > 190) && (ey > 400)
                    && (ex < 854) && (ey < 600)) {
                //if (highScore < player.getScore()) {
                //    highScore = player.getScore();
                //    saveScore();
                //}
                onKeyEvent(Player.START);
            }
            return true;
        }

        else if (player.isIdleState()) {
            if ((ex > 190) && (ey > 400)
                    && (ex < 854) && (ey < 600)) {
                onKeyEvent(Player.START);
                player.startGame();

            } else if ((ex > 700) && (ey > 50)
                    && (ey < 250)) {
                onKeyEvent(Player.START);
                player.startGame();
            }
            return true;
        }

        else if (player.isPauseState()) {

           if ((ex > 700) && (ey > 50)
                    && (ey < 250)) {
                onKeyEvent(Player.RESUME);
                player.startGame();
            } else if ((ex > 190) && (ey > 400)
                    && (ex < 854) && (ey < 600)) {
               onKeyEvent(Player.RESUME);
               player.startGame();
            }
            return true;
        }

        else if (player.isPlayState()) {
            if ((ex > 700) && (ey > 50)
                    && (ey < 250)) {
                onKeyEvent(Player.PAUSE);
                return true;
            }
        }

        if (ex > startX &&
                ey > startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 &&
                ex < startX + 200 &&
                ey < startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 + 200) {
            if(player != null) {
                onKeyEvent(Player.LEFT);
            }
            return true;
        }

        else if (ex > startX + 250&&
                ey > startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 &&
                ex < startX + 450 &&
                ey < startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 + 200) {
            if (player != null) {
                onKeyEvent(Player.BOTTOM);
                onKeyEvent(Player.DOWN);
            }
            return true;
        }

        else if (ex > startX + 500&&
                ey > startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 &&
                ex < startX + 700 &&
                ey < startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 + 200) {
            if (player != null) {
                onKeyEvent(Player.ROTATE);
            }
            return true;
        }

        else if (ex > startX + 750&&
                ey > startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 &&
                ex < startX + 950 &&
                ey < startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT + 100 + 200) {
            if (player != null) {

                onKeyEvent(Player.RIGHT);
            }
            return true;
        }

        else if (ex > startX + 750&&
                ey > startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT - 200 &&
                ex < startX + 950 &&
                ey < startY + PlayerDraw.BLOCK_IMAGE_SIZE * Player.BOARD_HEIGHT) {
            if (player != null) {
                player.moveDown();
            }
            return true;
        }
        return false;
    }

    public boolean onKeyEvent(int keycode) {
        System.out.println("Tetris (d) Player1 Press key : " + keycode);

        if (player == null) {
            return false;
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
