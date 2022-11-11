package media;

import search.JsonRequestor;

/**
 * Represents a single movie as specified in the IMDB Api. Note: Currently, only
 * supports json files as described in the Title Search Results. This may be
 * changed later.
 *
 * @author Will Morris, Matthew Potter
 * @version 11/10/2022
 */
public class Movie
{
  private final String id;
  private final String imageLink;
  private final String title;
  private final String description;
  private double imdbRating;

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
    this.imdbRating = Double.NEGATIVE_INFINITY;
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
   * Getter for rating. As rating is not instantly viewable on a search, any
   * movie that doesn't have a rating set must query the IMDb API to get it.
   * 
   * @return the id as queried from the IMDb API based on the Movie's ID
   */
  public double getRating()
  {
    if (imdbRating == Double.NEGATIVE_INFINITY)
    {
      imdbRating = JsonRequestor.queryRating(id);
    }
    if (imdbRating == Double.NEGATIVE_INFINITY)
    {
      System.err.println("Rating was unable to be received");
    }
    return imdbRating;
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
