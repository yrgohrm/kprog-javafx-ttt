package se.yrgo.tictactoe.models;

public final class Highscore implements Comparable<Highscore> {
    private String name;
    private int score;

    public Highscore(String name, int score) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }

        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Highscore highscore = (Highscore) o;

        if (score != highscore.score) return false;
        return name.equals(highscore.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + score;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%d:%s", score, name);
    }

    @Override
    public int compareTo(Highscore o) {
        return o.score - score;
    }
}
