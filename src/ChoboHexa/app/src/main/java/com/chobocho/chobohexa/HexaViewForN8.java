package com.chobocho.chobohexa;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.nfc.tech.NfcB;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chobocho.player.*;

import static android.content.Context.MODE_PRIVATE;

public class HexaViewForN8 extends View  {
	Context mContext;

	IPlayer player;
	IPlayerDraw playerDraw;
	IPlayerAction playerAction;

	final int N8_width = 1080;
	final int N8_height = 1920;
	final int UPDATE_SCREEN = 1001;
	final int PRESS_KEY = 1002;
	final int BLOCK_DOWN = 1003;

	private int screenWidth;
	private int screenHeigth;
	int gameSpeed;
    int highScore = 0;
	HandlerThread mHandlerThread;
	Handler mHexaHandler;

	public HexaViewForN8(Context context) {
		super(context);
		this.mContext = context;
		loadScore();

        createHexaThread();

		playerAction = new PlayerAction();
		playerDraw = new PlayerDraw(mContext, N8_width, N8_height);
		player = new Player(this, playerDraw, playerAction);
		player.setHighScore(highScore);
		playerAction.setPlayer(player);
		playerDraw.setPlayer(player);

		gameSpeed = 800;

	}

	private void createHexaThread() {
        Log.d("Hexa","createHexaThread");
        mHandlerThread = new HandlerThread("Hexa Processing Thread");
        mHandlerThread.start();
        mHexaHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg){
                Log.d("Hexa", "HexaHandler: There is event : " + msg.what);
                if (player == null) {
                    return;
                }

                if (msg.what == PRESS_KEY) {
                    player.onTouchScreen(msg.arg1, msg.arg2);
                }

                else if (msg.what == BLOCK_DOWN && player.isPlayState()) {
                    player.moveDown();
                    gameSpeed = 800 - (player.getScore() / 50000);
                    Message message = new Message();
                    message.what = BLOCK_DOWN;
                    mHexaHandler.sendMessageDelayed(message, gameSpeed);
                }
            }
        };
    }

	public void setScreenSize(int w, int h) {
		this.screenWidth = w;
		this.screenHeigth = h;
		playerDraw.setScreenSize(this.screenWidth, this.screenHeigth);
	}

	public void onDraw(Canvas canvas) {
        if (player != null) {
        	player.onDraw(canvas);
		}
	}

	public void pauseGame() {
        Log.d("Hexa", "pauseGame");
		if (mHandler.hasMessages(0)) {
			mHandler.removeMessages(0);
			Log.d("Hexa", "Removed event");
		}
        if (mHexaHandler.hasMessages(0)) {
            mHexaHandler.removeMessages(0);
            Log.d("Hexa", "Removed event from HexaHander");
        }

		if (player != null && player.isPlayState()) {
			player.pause();
		}
		if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
	}

    public void resumeGame() {
        Log.d("Hexa", "resumeGame");
        createHexaThread();
    }

	public void update() {
		Log.d("Hexa", "View.update()");
		Message message= new Message();
		message.what = UPDATE_SCREEN;
        mHandler.sendMessage(message);
	}

	public void startGame() {
		Message message = new Message();
		message.what = BLOCK_DOWN;
		mHexaHandler.sendMessageDelayed(message, gameSpeed);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d("Hexa", "There is event : " + msg.what);
			if (msg.what == UPDATE_SCREEN) {
				invalidate();
			}
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		Log.d("Hexa", ">> X: " + event.getX() + " Y: " + event.getY());

		if (MotionEvent.ACTION_DOWN == event.getAction()) {

			if (player == null) {
				return true;
			}


			int x = Math.round(event.getX());
			int y = Math.round(event.getY());

			Message msg = new Message();
			msg.what = PRESS_KEY;
			msg.arg1 = x;
			msg.arg2 = y;
			mHexaHandler.sendMessage(msg);

			return true;
		}
		return false;
	}

	public void loadScore() {
		Log.d("Hexa", "load()");
		SharedPreferences pref = mContext.getSharedPreferences("choboTetris", MODE_PRIVATE);
		this.highScore = pref.getInt("highscore", 0);
	}

	public void saveScore() {
		Log.d("Hexa", "saveScore()");
		SharedPreferences pref = mContext.getSharedPreferences("choboTetris", MODE_PRIVATE);
		SharedPreferences.Editor edit = pref.edit();

		if (player != null && this.highScore < player.getHighScore()) {
			this.highScore = player.getHighScore();
		}

		edit.putInt("highscore", this.highScore);
		edit.commit();
	}
}
