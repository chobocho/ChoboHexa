package com.chobocho.hexa;
import java.util.*;

/**
 * 
 */
public class HexaIdleState extends HexaGameState {

    /**
     * Default constructor
     */
    public HexaIdleState(Hexa hexa) {
        HexaLog.d("HexaIdleState()");
        this.hexa = hexa;
    }
    public boolean isIdleState() {
       return true;
    }
}