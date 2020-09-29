package com.chobocho.player;

import android.graphics.Canvas;

import com.chobocho.hexa.*;

public interface IPlayerDraw {
    public void onDraw(Canvas g);
    public void setHexa(IHexa hexa);
    public void setPlayer(IPlayer player);
}
