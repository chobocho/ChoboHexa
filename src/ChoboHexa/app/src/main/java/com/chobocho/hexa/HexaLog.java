package com.chobocho.hexa;

import android.util.Log;

/**
 * Created by chobo on 2017-12-26.
 */

public class HexaLog {
    public static void d(String log) {
        Log.d("Hexa", log);
    }
    public static void e(String log) {
        //System.out.println("Hexa (e) " + log);
        Log.e("Hexa", log);
    }
    public static void i(String log) {
        //System.out.println("Hexa (i) " + log);
        Log.i("Hexa", log);
    }

    public static void v(String log) {
        //System.out.println("Hexa (i) " + log);
        Log.v("Hexa", log);
    }
}
