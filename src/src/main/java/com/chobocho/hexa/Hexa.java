package com.chobocho.hexa;


public class Hexa implements IHexa {
    public static final int EMPTY = 0;

    private int score;
    private int level;
    private int speed;

    private HexaIdleState idleState;
    private HexaPlayState playState;
    private HexaPauseState pauseState;
    private HexaGameOverState gameOverState;

    private HexaBoard board;
    private HexaGameState gameState;

    private IHexaObserver observer;

    public Hexa(int width, int height) {
        HexaLog.d("Create new Hexa : " + width + " x " + height);

        board = new HexaBoard(width, height, this);

        idleState = new HexaIdleState(this);
        pauseState = new HexaPauseState(this);
        playState = new HexaPlayState(this, this.board);
        gameOverState = new HexaGameOverState(this);
    }

    public void init() {
        HexaLog.d("Hexa.Init()");
        score = 0;
        level = 1;
        gameState = idleState;
        board.init();
        gameState.update();
    }

    public void play() {
        HexaLog.d("Hexa.play()");
        score = 0;
        level = 1;
        setState(playState);
        gameState.init();
        gameState.update();
    }

    public void pause() {
        HexaLog.d("Hexa.pause()");
        setState(pauseState);
        gameState.update();
    }

    public void resume() {
        HexaLog.d("Hexa.resume()");
        setState(playState);
        gameState.update();
    }

    public void moveLeft() {
        gameState.moveLeft();
        gameState.update();
    }

    public void moveRight() {
        gameState.moveRight();
        gameState.update();
    }

    public void moveDown() {
        if (gameState.gameOver()) {
            setState(gameOverState);
        } else {
            gameState.moveDown();
        }
        gameState.update();
    }

    public void rotate() {
        gameState.rotate();
        gameState.update();
    }

    public void update() {
        if (observer != null) {
            observer.update();
        }
    }

    public void moveBottom() {
        gameState.moveBottom();
        gameState.update();
    }

    public void setState(HexaGameState state) {
        this.gameState = state;
    }

    public IHexaObserver getObserver() {
        return this.observer;
    }

    public int getWidth() {
        return board.getWidth();
    }
    public int getHeight() {
        return board.getHeight();
    }
    public int getScore() { return this.score; }
    public int addSore(int score) { return this.score += score; }
    public int[][] getBoard() {
        return board.getBoard();
    }
    public HexaBlock getCurrentBlock() { return gameState.getCurrentHexaBlock(); }
    public HexaBlock getNextBlock() { return gameState.getNextHexaBlock(); }
    public void register(IHexaObserver observer) {
        HexaLog.d("Registered view!");
        this.observer = observer;
    }

    public boolean isIdleState() { return gameState.isIdleState(); }
    public boolean isGameOverState() { return gameState.isGameOverState(); }
    public boolean isPlayState() { return gameState.isPlayState(); }
    public boolean isPauseState() { return gameState.isPauseState(); }
}