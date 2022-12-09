package media;

/**
 * Represents a movie in theaters.
 * Needed to match params present in an in-theaters search.
 *
 * @author William Morris
 * @version 12/8/2022
 */
public class TheaterMovie extends Movie {

  /**
   * Theater Movie constructor.
   *
   * @param id Movie ID.
   * @param imageLink Movie's imageLink.
   * @param title Movie's title.
   * @param description Movie description.
   */
  public TheaterMovie(String id, String imageLink, String title, String description) {
    setId(id);
    setImageLink(imageLink);
    setTitle(title);
    setDescription(description);
  }
}
