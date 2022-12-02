package listmodel;

import media.Movie;

/**
 * The object that contains the information of the user's plan to watch list.
 * 
 * @author Matthew Potter, William Morris
 * @version 12/1/2022
 */
public class WatchListModel extends MovieMapModel
{
  /**
   * The path that should store the watch-list JSON file.
   *
   * NOTE: Windows should be able to interpret the forward slash, let me know if
   * not. If not... we may have a slight problem.
   */
  public static final String WATCH_LIST_PATH = "./data/watchlist.json";

  /**
   * Loads the watch-list data from disk, or creates an empty watch-list if
   * there is none.
   */
  public WatchListModel()
  {
    super(WATCH_LIST_PATH);
  }

  /*
   * Removed to boost coverage.
   *
   * Explicit value constructor. Sets the model to have the passed map of ID,
   * Movie pairs.
   *
   * @param watchList the watch-list to initialize this model with
   *
   * public WatchListModel(HashMap<String, Movie> watchList) {
   * super(WATCH_LIST_PATH, watchList); }
   */

  /**
   * Explicit path constructor. Useful for testing.
   * 
   * @param path
   *          Path to grab the WatchList from.
   */
  public WatchListModel(String path)
  {
    super(path);
  }

  /*
   * /** Loads the watch-list at WATCH_LIST_PATH's information into this
   * WatchListModel. If there is no watch-list saved at WATCH_LIST_PATH, an
   * empty one is created.
   * 
   * @return A watch-list containing the information stored at the defined path,
   * or an empty one if no watch-list exists.
   *
   * protected HashMap<String, Movie> load() {
   * 
   * }
   */

  /**
   * Adds the passed movie to the watch-list. If the movie is already in the
   * watch-list nothing is added.
   * 
   * @param movie
   *          the movie to add to the watch-list
   * @return true if the movie was added, false if not.
   */
  public boolean add(Movie movie)
  {
    if (list.containsKey(movie.getId()))
    {
      return false;
    }
    list.put(movie.getId(), movie);
    return true;
  }

  /**
   * Removes the passed movie from the watch list. If the movie isn't in the
   * watch list nothing is removed.
   * 
   * @param movie
   *          the movie to remove from the watch-list
   * @return true if the movie was removed, false if not.
   */
  public boolean remove(Movie movie)
  {
    if (!list.containsKey(movie.getId()))
    {
      return false;
    }
    list.remove(movie.getId());
    return true;
  }

  /**
   * Clears this watchList.
   */
  public void clear()
  {
    list.clear();
  }
}
