package se.yrgo.tictactoe.models;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscores {
    private static final int MAX_SIZE = 10;
    private static Path highscorePath = Path.of("highscores.db");
    private List<Highscore> scores;

    public Highscores() {
        this.scores = new ArrayList<>(10);

        try {
            if (!Files.exists(highscorePath)) {
                Files.createFile(highscorePath);
            }

            List<String> lines = Files.readAllLines(highscorePath);
            lines.forEach(line -> {
                String[] scoreAndName = line.split(":", 2);
                if (scoreAndName.length == 2) {
                    int score = Integer.parseInt(scoreAndName[0]);
                    String name = scoreAndName[1];
                    this.scores.add(new Highscore(name, score));
                }
            });

            this.scores.sort(null);
        }
        catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * Returns true if a score would end up on the highscore list if added.
     *
     * @param score the score to check for eligibility
     * @return true if eligible to enter the highscore, false if not
     */
    public boolean isEligible(int score) {
        return scores.size() < MAX_SIZE || score > scores.get(scores.size()-1).getScore();
    }

    /**
     * Adds a score to the highscore if it is high enough.
     *
     * @param name the name of the entry
     * @param score the score
     * @return true if the entry was added to the highscore, false otherwise
     */
    public boolean addHighscore(String name, int score) {
        if (isEligible(score)) {
            if (scores.size() == MAX_SIZE) {
                scores.remove(scores.size() - 1);
            }

            scores.add(new Highscore(name, score));
            scores.sort(null);
            saveHighscores();
            return true;
        }

        return false;
    }

    /**
     * Get an unmodifiable list of all the entries on the highscore list.
     * @return a list of highscore entries, in descending order
     */
    public List<Highscore> getHighscores() {
        return Collections.unmodifiableList(scores);
    }

    private void saveHighscores() {
        StringBuilder builder = new StringBuilder();
        for (Highscore score : scores) {
            builder.append(score.toString());
            builder.append("\n");
        }

        try {
            Files.writeString(highscorePath, builder.toString());
        }
        catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
