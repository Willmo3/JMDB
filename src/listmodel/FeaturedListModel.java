package listmodel;

/**
 * Featured Movie List Model.
 * Encapsulates a file containing a list of featured movies.
 *
 * @author William Morris
 * @version 12/1/2022
 */
public class FeaturedListModel extends ListModel {

  private static final String FEATURED_LIST_PATH = "./data/FeaturedMovieList.json";

  /**
   * Default Featured List Model constructor.
   * Creates a list from the default featured movie list path.
   *
   */
  public FeaturedListModel() {
    super(FEATURED_LIST_PATH);
  }
}
