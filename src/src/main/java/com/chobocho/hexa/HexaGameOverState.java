package com.chobocho.hexa;

/**
 * 
 */
public class HexaGameOverState extends HexaGameState {

    /**
     * Default constructor
     */
    public HexaGameOverState(Hexa Hexa) {
        this.hexa = Hexa;
    }

    public boolean isGameOverState() {
        return true;
    }
}

