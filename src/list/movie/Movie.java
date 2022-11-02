package list.movie;

/**
 * Represents a single movie as specified in the IMDB Api.
 * Note: Currently, only supports json files as described in the Title Search Results.
 * This may be changed later.
 *
 * @author Will Morirs
 * @version 11/1/2022
 */
public class Movie {
  private final String id;
  private final String imageLink;
  private final String title;
  private final String description;
  private double imdb_rating;

  public Movie(String id, String imageLink, String title, String description) {
    this.id = id;
    this.imageLink = imageLink;
    this.title = title;
    this.description = description;
  }

  /**
   * Sets the IMDB_Rating of this movie.
   *
   * @param imdb_rating New rating.
   */
  public void setRating(int imdb_rating) {
    this.imdb_rating = imdb_rating;
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
}