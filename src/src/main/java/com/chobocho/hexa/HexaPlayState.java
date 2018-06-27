package com.chobocho.hexa;

public class HexaPlayState extends HexaGameState {
    private HexaBlock currentHexaBlock;
    private HexaBlock nextHexaBlock;
    private HexaBoard HexaBoard;
    private int additionalPoint = 1;
    private boolean isProcessing = false;

    public HexaPlayState(Hexa hexa, HexaBoard board) {
        this.hexa = hexa;
        this.HexaBoard = board;
        currentHexaBlock = HexaBlockFactory.create();
        nextHexaBlock = HexaBlockFactory.create();
        this.isProcessing = false;
    }

    public void init() {
        this.HexaBoard.init();
        currentHexaBlock = HexaBlockFactory.create();
        nextHexaBlock = HexaBlockFactory.create();
        additionalPoint = 1;
    }

    public void moveLeft() {
       HexaLog.d("HexaPlayState.moveLeft()");
        currentHexaBlock.moveLeft();
        if (HexaBoard.isAcceptable(currentHexaBlock) == false) {
            currentHexaBlock.moveRight();
            HexaLog.d("Not Accept");
        } else {
            HexaLog.d("Accept");
        }
    }

    public void moveRight() {
        HexaLog.d("HexaPlayState.moveRight()");
        currentHexaBlock.moveRight();
        if (HexaBoard.isAcceptable(currentHexaBlock) == false) {
            currentHexaBlock.moveLeft();
            HexaLog.d("Not Accept");
        } else {
            HexaLog.d("Accept");
        }
    }

    public void rotate() {
        HexaLog.d("HexaPlayState.rotate()");
        currentHexaBlock.rotate();
    }


    public void moveDown() {
        HexaLog.d("HexaPlayState.moveDown()");
        if (this.isProcessing) {
            return;
        }
        currentHexaBlock.moveDown();
        if (HexaBoard.isAcceptable(currentHexaBlock) == false) {
            this.isProcessing = true;
            currentHexaBlock.moveUp();
            HexaLog.d("Can not move down");
            fixCurrentBlock();
            updateBoard();
            updateBlock() ;
            this.isProcessing = false;
        } else {
            HexaLog.d("Accept");
        }
    }

    public void moveBottom() {
        HexaLog.d("HexaPlayState.moveBottom()");
        while(HexaBoard.isAcceptable(currentHexaBlock)) {
            currentHexaBlock.moveDown();
        }
        if (HexaBoard.isAcceptable(currentHexaBlock) == false) {
            currentHexaBlock.moveUp();
        }
    }

    public void fixCurrentBlock() {
        HexaBoard.addHexaBlock(currentHexaBlock);
    }

    public void updateBlock() {
        currentHexaBlock = nextHexaBlock;
        nextHexaBlock = HexaBlockFactory.create();
    }

    public boolean gameOver() {
        HexaLog.d("Check Game over!");
        return (HexaBoard.isAcceptable(currentHexaBlock) == false);
    }

    public void updateBoard() {
        int removedBlock = HexaBoard.arrange();
        int point = calculatorScore(removedBlock);
        hexa.addSore(point);
    }

    private int calculatorScore(int removedBlock) {
        if (removedBlock == 0) {
            additionalPoint = 1;
            return 0;
        }

        int lineScore = 30;
        if (removedBlock > 20) {
            removedBlock = 20;
        }
        if (removedBlock >= 4) {
            lineScore *= Math.pow(2, removedBlock);
        }
        if (additionalPoint > 100000) {
            additionalPoint = 100000;
        }
        additionalPoint += lineScore;
        HexaLog.d("calculatorScore : " + additionalPoint + " : " + removedBlock);
        return  (additionalPoint + lineScore);
    }

    public HexaBlock getCurrentHexaBlock() {
        return currentHexaBlock;
    }

    public HexaBlock getNextHexaBlock() {
        return nextHexaBlock;
    }

    public boolean isPlayState() {
        return true;
    }
}