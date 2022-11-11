package media;

import search.JsonRequestor;

/**
 * Represents a single movie as specified in the IMDB Api.
 * Note: Currently, only supports json files as described in the Title Search Results.
 * This may be changed later.
 *
 * @author Will Morris, Matthew Potter
 * @version 11/10/2022
 */
public class Movie {
  public static final Double DEFAULT_RATING = Double.NEGATIVE_INFINITY;
  public static final String DEFAULT_TRAILER = "DEFAULT_TRAILER";
  private final String id;
  private final String imageLink;
  private final String title;
  private final String description;
  private String trailerLink;
  private double imdb_rating;

  public Movie(String id, String imageLink, String title, String description) {
    this.id = id;
    this.imageLink = imageLink;
    this.title = title;
    this.description = description;
    this.imdb_rating = DEFAULT_RATING;
    this.trailerLink = DEFAULT_TRAILER;
  }

  public String getId() {
    return id;
  }

  public String getImageLink() {
    return imageLink;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Fetches the rating for a movie.
   * Grabs from internet if one does not already exist.
   *
   * @return The rating.
   */
  public double getRating() {
    if (imdb_rating == DEFAULT_RATING) {
      imdb_rating = JsonRequestor.queryRating(id);
    }
    if (imdb_rating == DEFAULT_RATING) {
      System.err.println("Rating was unable to be received");
    }
    return imdb_rating;
  }

  /**
   * Fetches the trailer for a movie.
   * Grabs one from the internet if one does not already exist.
   *
   * @return Link to the trailer.
   */
  public String getTrailer() {
    if (trailerLink != null && trailerLink.equals(DEFAULT_TRAILER)) {
      trailerLink = JsonRequestor.queryTrailer(id);
    }

    if (trailerLink == null) {
      System.err.println("Trailer link not present.");
    }

    return trailerLink;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Movie) {
      // Make sure to not use pattern variables, since some versions of java will complain.
      Movie movie = (Movie) o;
      return id.equals(movie.id) && imageLink.equals(movie.imageLink) && title.equals(movie.title)
          && description.equals(movie.description);
    }

    return false;
  }

  /**
   * Returns String representation of this movie.
   * Format: title description;
   *
   * @return The formatted String.
   */
  @Override
  public String toString() {
    return title + " " + description;
  }
}
