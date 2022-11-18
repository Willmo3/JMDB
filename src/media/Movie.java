package media;

import search.JsonRequestor;

/**
 * Represents a single movie as specified in the IMDB Api. Note: Currently, only
 * supports json files as described in the Title Search Results. This may be
 * changed later.
 *
 * @author Will Morris, Matthew Potter
 * @version 11/17/2022
 */
public class Movie
{
  /**
   * Sentinel value for ratings.
   */
  public static final Double DEFAULT_RATING = Double.NEGATIVE_INFINITY;

  /**
   * Sentinel string for trailer links.
   */
  public static final String DEFAULT_TRAILER = "DEFAULT_TRAILER";

  /**
   * String indicating that a queried movie has no YouTube trailer on IMDb.
   */
  public static final String NO_TRAILER = "NO_TRAILER";

  /**
   * Sentinel string for awards.
   */
  public static final String DEFAULT_AWARD = "DEFAULT_AWARD";
  private final String id;
  private final String imageLink;
  private final String title;
  private final String description;
  private double imdbRating;
  private String trailerLink;
  private String award;

  /**
   * Explicit value constructor. Sets up a Movie with the associated
   * information.
   * 
   * @param id
   *          the Movie's ID on IMDb
   * @param imageLink
   *          the link to the Movie's cover image
   * @param title
   *          the title of the Movie
   * @param description
   *          the IMDb search description of the Movie
   */
  public Movie(String id, String imageLink, String title, String description)
  {
    this.id = id;
    this.imageLink = imageLink;
    this.title = title;
    this.description = description;
    this.imdbRating = DEFAULT_RATING;
    this.trailerLink = DEFAULT_TRAILER;
    this.award = DEFAULT_AWARD;
  }

  /**
   * Explicit value constructor for every value. Sets up a Movie with the
   * associated information.
   * 
   * @param id
   *          the Movie's ID on IMDb
   * @param imageLink
   *          the link to the Movie's cover image
   * @param title
   *          the title of the Movie
   * @param description
   *          the IMDb search description of the Movie
   * @param awards
   *          the awards this Movie has won
   * @param trailer
   *          the link to the trailer of this Movie
   * @param rating
   *          the IMDb rating of this Movie
   */
  public Movie(String id, String imageLink, String title, String description,
      String awards, String trailer, double rating)
  {
    this.id = id;
    this.imageLink = imageLink;
    this.title = title;
    this.description = description;
    this.imdbRating = rating;
    this.trailerLink = trailer;
    this.award = awards;
  }

  /**
   * Getter for id.
   * 
   * @return the id
   */
  public String getId()
  {
    return id;
  }

  /**
   * Getter for imageLink.
   * 
   * @return the image link string
   */
  public String getImageLink()
  {
    return imageLink;
  }

  /**
   * Getter for title.
   * 
   * @return the title
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Getter for the search description.
   * 
   * @return the search description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Fetches the rating for a movie. Grabs from internet if one does not already
   * exist.
   *
   * @return The rating.
   */
  public double getRating()
  {
    if (imdbRating == DEFAULT_RATING)
    {
      imdbRating = JsonRequestor.queryRating(id);
    }
    if (imdbRating == DEFAULT_RATING)
    {
      System.err.println("Rating was unable to be received");
    }
    return imdbRating;
  }

  /**
   * Fetches the trailer for a movie. Grabs one from the internet if the link's
   * information has not already been retrieved from the internet.
   *
   * @return The link to the trailer on YouTube, NO_TRAILER if no trailer
   *         exists, or null if it wouldn't make sense to have a trailer in the
   *         first place e.g. a trailer for an actor.
   */
  public String getTrailer()
  {
    if (trailerLink == null || trailerLink.equals(NO_TRAILER))
    {
      return trailerLink;
    }

    if (trailerLink.isEmpty())
    {
      trailerLink = NO_TRAILER;
    }

    if (trailerLink.equals(DEFAULT_TRAILER))
    {
      trailerLink = JsonRequestor.queryTrailer(id);
    }

    return trailerLink;
  }

  /**
   * Fetches the Awards for a movie. Grabs one from the Internet if one does not
   * already exist.
   *
   * @return Awards.
   */
  public String getAwards()
  {
    if (award != null && award.equals(DEFAULT_AWARD))
    {
      award = JsonRequestor.queryAwards(id);
    }

    if (award == null)
    {
      System.err.println("award not present.");
    }

    return award;
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof Movie)
    {
      // Make sure to not use pattern variables, since some versions of java
      // will complain.
      Movie movie = (Movie) o;
      return id.equals(movie.id) && imageLink.equals(movie.imageLink)
          && title.equals(movie.title) && description.equals(movie.description);
    }

    return false;
  }

  /**
   * Returns String representation of this movie. Format: title description;
   *
   * @return The formatted String.
   */
  @Override
  public String toString()
  {
    return title + " " + description;
  }
}
