package game;

import controller.JmdbController;
import media.Movie;
import search.JsonRequestor;

import javax.swing.*;
import java.util.Arrays;

/**
 * This class is used to play the Guess the Rating game.
 * It will be called from the dropdown menu in the main GUI.
 * When it is clicked, it will open a new small window with a movie poster that is fetched from the currently
 * selected movie in the main GUI. The user will then be prompted to guess the rating of the movie with two
 * options, one being the correct rating and the other being a random rating. The user will then be told if
 * they were correct or not.
 *
 * @author Austin Perdue
 * @version 12/9/2022
 */
public class GuessTheRating {
    // make a random decimal rating 1-10
    private int randomRating;
    private int correctRating;
    private String movieLink;
    private Movie movie;
    private String movieTitle;



    public GuessTheRating(Movie movie) {
        this.movieLink = movie.getImageLink();
        this.randomRating = (int) (Math.random() * 8) + 3;
        this.correctRating = (int) movie.imdbRating();
        this.movie = movie;
        this.movieTitle = movie.getTitle();
    }

    public void runGame() {
        // create a new JFrame for the game to be played in and draw it to the screen
        JFrame gameFrame = new JFrame("Guess the rating");
        gameFrame.setSize(250, 250);
        // center the window on the screen
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        // create a new JPanel for the game to be played in and draw it to the screen
        JPanel gamePanel = new JPanel();
        gameFrame.add(gamePanel);

        // add a panel for the title of the movie and add it to the center of the screen
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(movieTitle);
        titlePanel.add(titleLabel);
        gamePanel.add(titlePanel);


        // create another panel below the title panel to hold the two buttons
        JPanel buttonPanel = new JPanel();
        gamePanel.add(buttonPanel);

        // create a button for the correct rating
        JButton correctButton = new JButton("" + correctRating);
        buttonPanel.add(correctButton);

        // create a button for the random rating
        JButton randomButton = new JButton("" + randomRating);
        buttonPanel.add(randomButton);

        // make the buttons bigger and centered
        correctButton.setPreferredSize(new java.awt.Dimension(100, 50));
        randomButton.setPreferredSize(new java.awt.Dimension(100, 50));
        correctButton.setHorizontalAlignment(SwingConstants.CENTER);
        randomButton.setHorizontalAlignment(SwingConstants.CENTER);





        // add another panel with a label inside of it called result that will be used to display the result of the game
        // make the result text below the buttons in the center of the window
        JPanel resultPanel = new JPanel();
        JLabel result = new JLabel();
        resultPanel.add(result);
        gamePanel.add(resultPanel);


        // if the user clicks the correct button, display a message saying they were correct
        correctButton.addActionListener(e -> {
            result.setText("Correct!");

            // after the user selects a button, close the window in 3 seconds
            Timer timer = new Timer(2000, e1 -> gameFrame.dispose());
            timer.setRepeats(false);
            timer.start();
        });

        // if the user clicks the random button, display a message saying they were incorrect
        randomButton.addActionListener(e -> {
            result.setText("Incorrect!");

            // after the user selects a button, close the window in 3 seconds
            Timer timer = new Timer(2000, e1 -> gameFrame.dispose());
            timer.setRepeats(false);
            timer.start();
        });


    }
}
