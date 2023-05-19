package se.yrgo.tictactoe.models;

import java.util.Objects;

public class Move {
    private int x;
    private int y;

    public Move(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            throw new IllegalArgumentException("out of bounds");
        }

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Move{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return x == move.x && y == move.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
