package com.chobocho.player;

public interface IPlayerAction {
    public boolean onKeyEvent(int k);
    public boolean onTouchScreen(int x, int y);
    public void setPlayer(IPlayer player);
}
