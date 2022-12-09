package mediaDisplay;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import media.RatingTypes;

/**
 * The display panel for the ratings given to Media.
 * 
 * @author Matthew Potter
 * @version 12/08/2022
 */
public class MovieRatingsDisplay extends JPanel
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
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    double imdbRating = ratings[RatingTypes.IMDB.ordinal()];
    double metacriticRating = ratings[RatingTypes.METACRITIC.ordinal()];
    double moviedbRating = ratings[RatingTypes.MOVIEDB.ordinal()];
    double rottenRating = ratings[RatingTypes.ROTTEN_TOMATOES.ordinal()];
    double filmAffRating = ratings[RatingTypes.FILM_AFFINITY.ordinal()];
    if (Double.isFinite(imdbRating))
    {
      add(new JLabel(String.format("IMDb: %2.1f/10", imdbRating)));
    }
    if (Double.isFinite(metacriticRating))
    {
      add(new JLabel(String.format("Metacritic: %.0f/100", metacriticRating)));
    }
    if (Double.isFinite(moviedbRating))
    {
      add(new JLabel(
          String.format("The Movie Database: %2.1f/10", moviedbRating)));
    }
    if (Double.isFinite(rottenRating))
    {
      add(new JLabel(String.format("Rotten Tomatoes: %.0f/100", rottenRating)));
    }
    if (Double.isFinite(filmAffRating))
    {
      add(new JLabel(String.format("Film Affinity: %2.1f/10", filmAffRating)));
    }
  }
}
