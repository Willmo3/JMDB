package mediaDisplay;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import media.RatingTypes;

/**
 * The display panel for the ratings given to Media.
 * 
 * @author Matthew Potter
 * @version 12/08/2022
 */
public class MovieRatingsDisplay extends JScrollPane
{
  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = 1388986078605588297L;

  /**
   * Constructs a MovieRatingsDisplay based on the passed list of ratings. The
   * rating list passed must have the given ratings formatted in the order of:
   * IMDb, Metacritic, the Movie Database, Rotten Tomatoes, and Film Affinity.
   * 
   * @param ratings
   *          the list of ratings this display should show
   */
  public MovieRatingsDisplay(double[] ratings)
  {
    super();
    JTextPane ratingText = new JTextPane();
    setPreferredSize(new Dimension(600, 600));
    setViewportView(ratingText);
    double imdbRating = ratings[RatingTypes.IMDB.ordinal()];
    double metacriticRating = ratings[RatingTypes.METACRITIC.ordinal()];
    double moviedbRating = ratings[RatingTypes.MOVIEDB.ordinal()];
    double rottenRating = ratings[RatingTypes.ROTTEN_TOMATOES.ordinal()];
    double filmAffRating = ratings[RatingTypes.FILM_AFFINITY.ordinal()];
    Document ratingDoc = ratingText.getDocument();
    try
    {
      if (Double.isFinite(imdbRating))
      {
        ratingDoc.insertString(ratingDoc.getLength(),
            String.format("IMDb: %2.1f/10\n", imdbRating), null);
      }
      if (Double.isFinite(metacriticRating))
      {
        ratingDoc.insertString(ratingDoc.getLength(),
            String.format("Metacritic: %.0f/100\n", metacriticRating), null);
      }
      if (Double.isFinite(moviedbRating))
      {
        ratingDoc.insertString(ratingDoc.getLength(),
            String.format("The Movie Database: %2.1f/10\n", moviedbRating),
            null);
      }
      if (Double.isFinite(rottenRating))
      {
        ratingDoc.insertString(ratingDoc.getLength(),
            String.format("Rotten Tomatoes: %.0f/100\n", rottenRating), null);
      }
      if (Double.isFinite(filmAffRating))
      {
        ratingDoc.insertString(ratingDoc.getLength(),
            String.format("Film Affinity: %2.1f/10\n", filmAffRating), null);
      }
    }
    catch (BadLocationException e)
    {
      System.err.println(
          "Tried to print to bad location in the document for Ratings");
      e.printStackTrace();
    }
  }
}
