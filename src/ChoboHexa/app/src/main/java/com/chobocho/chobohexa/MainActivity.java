package com.chobocho.chobohexa;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    HexaViewForN8 hexaViewForN8;
    BoardProfile boardProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        Log.e("Hexa", "W" + screenWidth + " H" + screenHeight);

        boardProfile = new BoardProfile(screenWidth, screenHeight);

        hexaViewForN8 = new HexaViewForN8(this, boardProfile);
        setContentView(hexaViewForN8);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hexaViewForN8 != null) {
            hexaViewForN8.pauseGame();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hexaViewForN8 != null) {
            hexaViewForN8.resumeGame();
        }
    }
}
