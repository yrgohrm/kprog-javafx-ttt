package se.yrgo.tictactoe.models;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a game of Tic Tac Toe.
 *
 * It is perhaps a bit ugly in its design since it both
 * handles the rules of the game, but also keeps time,
 * but it makes the assignment a bit easier to explain.
 *
 */
public class TicTacToe {
    private Player[][] board;
    private TimerListener listener;
    private Timer timer;
    private int seconds;

    public TicTacToe(TimerListener listener) {
        this.board = new Player[3][3];
        this.listener = listener;
    }

    public void newGame() {
        if (timer != null) {
            timer.cancel();
        }

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y] = Player.NONE;
            }
        }

        seconds = 0;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                listener.tick(seconds / 60, seconds % 60);
                seconds++;
            }
        };

        timer = new Timer(true);
        timer.schedule(timerTask, 0L, 1000L);
    }

    public boolean isEmpty(Move move) {
        return board[move.getX()][move.getY()] == Player.NONE;
    }

    public void playerMove(Move move) {
        if (isGameOver()) {
            throw new IllegalStateException("game is over");
        }

        if (!isEmpty(move)) {
            throw new IllegalArgumentException("can't place there");
        }

        board[move.getX()][move.getY()] = Player.PLAYER;

        stopTimerOnGameOver();
    }

    public Move computerMove() {
        if (isGameOver()) {
            throw new IllegalStateException("game is over");
        }

        // completely random player, this is of course very inefficient to
        // just use random in this way since we probably will try the same
        // square multiple times when the board gets full
        Move move;
        do {
             move = new Move(ThreadLocalRandom.current().nextInt(3),
                                 ThreadLocalRandom.current().nextInt(3));
        } while (!isEmpty(move));

        board[move.getX()][move.getY()] = Player.COMPUTER;

        stopTimerOnGameOver();

        return move;
    }

    public int getScore() {
        if (getWinner() == Player.PLAYER) {
            return (100 - seconds + getEmptyCount() * 20) * 314;
        }

        return 0;
    }

    public Player getWinner() {
        return findWinner();
    }

    public boolean isGameOver() {
        if (findWinner() != Player.NONE) {
            return true;
        }

        // check if board is full
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == Player.NONE) {
                    return false;
                }
            }
        }

        return true;
    }

    private void stopTimerOnGameOver() {
        Player winner = findWinner();
        if (winner != Player.NONE) {
            timer.cancel();
        }
    }

    private Player findWinner() {
        // Check rows and columns
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] != Player.NONE && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }

            if (board[0][i] != Player.NONE && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }

        // Check diagonals
        if (board[0][0] != Player.NONE && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }

        if (board[0][2] != Player.NONE && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        return Player.NONE;
    }

    private int getEmptyCount() {
        int count = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == Player.NONE) {
                    count++;
                }
            }
        }
        return count;
    }
}
