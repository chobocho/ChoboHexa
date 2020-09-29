package com.chobocho.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.chobocho.chobohexa.BoardProfile;
import com.chobocho.chobohexa.R;
import com.chobocho.hexa.*;



public class PlayerDraw implements IPlayerDraw {
    IHexa hexa;
    IPlayer player;
    BoardProfile boardProfile;

    Bitmap mGameBack;
    Bitmap mGameStart;
    Bitmap mGameOver;
    Bitmap mGameResume;


    Bitmap leftArrow;
    Bitmap rightArrow;
    Bitmap downArrow;
    Bitmap bottomArrow;
    Bitmap rotateArrow;
    Bitmap playBtn;
    Bitmap pauseBtn;


    Bitmap[] blockImages = null;

    Paint mPaint;

    Context context;

    int screen_width = 0;
    int screen_height = 0;
    int block_size = 0;

    boolean isLoadedImage = false;

    public static int BLOCK_IMAGE_SIZE = 80;
    int BOARD_WIDTH = 7;
    int BOARD_HEIGHT = 16;

    public PlayerDraw (Context context, BoardProfile boardProfile) {
        this.context = context;
        this.player = player;
        this.boardProfile = boardProfile;
        this.screen_width = boardProfile.screenWidth();
        this.screen_height = boardProfile.screenHeight();
        this.block_size = boardProfile.blockSize();
        this.BLOCK_IMAGE_SIZE = boardProfile.blockSize();

        loadImage();
        initButtons();

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(BLOCK_IMAGE_SIZE);
    }

    private void loadImage() {
        mGameBack = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.backimage);
        mGameStart = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.start);
        mGameOver = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.gameover);
        mGameResume = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.resume);

        blockImages = new Bitmap[9];

        blockImages[0] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.empty);
        blockImages[1] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.b0);
        blockImages[2] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
        blockImages[3] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.b2);
        blockImages[4] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.b3);
        blockImages[5] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.b4);
        blockImages[6] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.b5);
        blockImages[7] =  BitmapFactory.decodeResource(context.getResources(), R.drawable.b6);

        leftArrow = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.left);
        rightArrow = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.right);
        downArrow  = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.down);
        rotateArrow = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.rotate);
        playBtn = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.play);
        pauseBtn = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pause);
        bottomArrow =  BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bottom);

        isLoadedImage = true;
    }

    private void initButtons() {

    }

    public void setHexa(IHexa hexa) {
        this.hexa = hexa;
        BOARD_WIDTH = hexa.getWidth();
        BOARD_HEIGHT = hexa.getHeight();
    }

    public void setPlayer(IPlayer player) {
        this.player = player;
    }

    public void onDraw(Canvas canvas) {
        if (isLoadedImage == false) {
            return;
        }
        HexaLog.v("onDraw");
        canvas.drawBitmap(mGameBack, null, new Rect(0, 0, screen_width, screen_height), null);

        int i = 0;
        int j = 0;
        String str_gameState = "";
        int startX = boardProfile.startX;
        int startY = boardProfile.startY;

        int width = hexa.getWidth();
        int height = hexa.getHeight();

        Paint paint = new Paint();
        paint.setAlpha(128);

        int[][] m_Board = hexa.getBoard();

        // Draw board
        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {

                if (Hexa.EMPTY == m_Board[j][i]) {
                    canvas.drawBitmap(blockImages[m_Board[j][i]], null,
                            new Rect(i * BLOCK_IMAGE_SIZE + startX,
                                    j * BLOCK_IMAGE_SIZE + startY,
                                    i * BLOCK_IMAGE_SIZE + startX + BLOCK_IMAGE_SIZE,
                                    j * BLOCK_IMAGE_SIZE + startY + BLOCK_IMAGE_SIZE), paint);
                } else {
                    canvas.drawBitmap(blockImages[m_Board[j][i]], null,
                            new Rect(i * BLOCK_IMAGE_SIZE + startX,
                                    j * BLOCK_IMAGE_SIZE + startY,
                                    i * BLOCK_IMAGE_SIZE + startX + BLOCK_IMAGE_SIZE,
                                    j * BLOCK_IMAGE_SIZE + startY + BLOCK_IMAGE_SIZE), null);
                }
            }
        }

        mPaint.setTextSize(BLOCK_IMAGE_SIZE);
        canvas.drawText("Score", startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-2)*BLOCK_IMAGE_SIZE, mPaint);
        mPaint.setTextSize((int)(BLOCK_IMAGE_SIZE * 0.8));
        canvas.drawText(Integer.toString(hexa.getScore()),startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-1)*BLOCK_IMAGE_SIZE, mPaint);
        mPaint.setTextSize(BLOCK_IMAGE_SIZE);
        canvas.drawText("High", startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-4)*BLOCK_IMAGE_SIZE, mPaint);
        mPaint.setTextSize((int)(BLOCK_IMAGE_SIZE * 0.8));
        canvas.drawText(Integer.toString(player.getHighScore()), startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-3)*BLOCK_IMAGE_SIZE, mPaint);

        canvas.drawBitmap(bottomArrow, null,
                new Rect((int) (startX + BLOCK_IMAGE_SIZE * 2.5),
                        (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)),
                        startX + BLOCK_IMAGE_SIZE * 5,
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 3.5))), null);

        canvas.drawBitmap(leftArrow, null,
                new Rect(startX,
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                        (int) (startX + BLOCK_IMAGE_SIZE * 2.5),
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 7))), null);

        canvas.drawBitmap(downArrow, null,
                new Rect((int) (startX + BLOCK_IMAGE_SIZE * 2.5),
                        (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                        startX + BLOCK_IMAGE_SIZE * 5,
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 7))), null);

        canvas.drawBitmap(rotateArrow, null,
                new Rect(startX + BLOCK_IMAGE_SIZE * 5,
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                        (int) (startX + BLOCK_IMAGE_SIZE * 7.5),
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 7))), null);

        canvas.drawBitmap(rightArrow, null,
                new Rect((int) (startX + BLOCK_IMAGE_SIZE * 7.5),
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 4.5)),
                        startX + BLOCK_IMAGE_SIZE * 10,
                        (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 7))), null);

        if (hexa == null) {
            Log.d("Hexa", "Hexa is null");
            return;
        }
        if (hexa.isIdleState()) {
            canvas.drawBitmap(mGameStart, null,
                    new Rect(boardProfile.buttonStartX, block_size*7, boardProfile.buttonStartX + block_size*6, block_size*10), null);
        } else if (hexa.isGameOverState()) {
            canvas.drawBitmap(mGameOver, null,
                    new Rect(boardProfile.buttonStartX, block_size*7, boardProfile.buttonStartX + block_size*6, block_size*10), null);
        } else if (hexa.isPlayState()) {
            canvas.drawBitmap(pauseBtn, null,
                    new Rect((int) (startX + BLOCK_IMAGE_SIZE * 7.5),
                            (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)),
                            startX + BLOCK_IMAGE_SIZE * 10,
                            (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 3.5))), null);

            HexaBlock block = hexa.getCurrentBlock();
            onDrawBlock(canvas, block, startX, startY);

            HexaBlock nextHexaBlock = hexa.getNextBlock();
            int nStartX = startX + (BOARD_WIDTH-2) * BLOCK_IMAGE_SIZE;
            int nStartY = startY;
            onDrawBlock(canvas, nextHexaBlock, nStartX, nStartY);
        } else if (hexa.isPauseState()) {
            canvas.drawBitmap(mGameResume, null,
                    new Rect(boardProfile.buttonStartX, block_size*7, boardProfile.buttonStartX + block_size*6, block_size*10), null);
            canvas.drawBitmap(playBtn, null,
                    new Rect((int) (startX + BLOCK_IMAGE_SIZE * 7.5),
                            (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)),
                            startX + BLOCK_IMAGE_SIZE * 10,
                            (int) (startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 3.5))), null);
            mPaint.setTextSize((int)(BLOCK_IMAGE_SIZE * 0.5));
            canvas.drawText("[" + screen_width + "x" + screen_height + "]", startX, (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 8)), mPaint);
        }
    }

    public void onDrawBlock(Canvas canvas, HexaBlock block, int startX, int startY) {
        onDrawBlock(canvas, block, startX, startY, null);
    }

    public void onDrawBlock(Canvas canvas, HexaBlock block, int startX, int startY, Paint paint) {
        int[] m_block = block.getBlock();

        int i = 0, j = 0;
        int w = block.getWidth();
        int h = block.getHeight();
        int x = block.getX();
        int y = block.getY();

        for (i = 0; i < w; i++) {
            for (j = 0; j < h; j++) {
                if (m_block[j] != Hexa.EMPTY) {
                    canvas.drawBitmap(blockImages[m_block[j]], null,
                            new Rect((x + i) * BLOCK_IMAGE_SIZE + startX,
                                    (y + j) * BLOCK_IMAGE_SIZE + startY,
                                    (x + i + 1) * BLOCK_IMAGE_SIZE + startX,
                                    (y + j + 1) * BLOCK_IMAGE_SIZE + startY),
                            paint);
                }
            }
        }
    }
}
