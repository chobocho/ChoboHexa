package com.chobocho.chobohexa;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    HexaViewForN8 hexaViewForN8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        Log.e("Hexa", "W" + screenWidth + " H" + screenHeight);

        hexaViewForN8 = new HexaViewForN8(this);
        hexaViewForN8.setScreenSize(screenWidth,screenHeight);
        setContentView(hexaViewForN8);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hexaViewForN8 != null) {
            hexaViewForN8.pauseGame();
        }
    }
}
