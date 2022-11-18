package watchlist;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import media.Movie;

/**
 * The object that contains the information of the user's plan to watch list.
 * 
 * @author Matthew Potter
 * @version 11/17/2022
 */
public class WatchListModel
{
  /**
   * The path that should store the watch-list JSON file.
   */
  public static final String WATCH_LIST_PATH = ".\\data\\watchlist.json";

  private HashMap<String, Movie> watchList;

  /**
   * Loads the watch-list data from disk, or creates an empty watch-list if
   * there is none.
   */
  public WatchListModel()
  {
    this.watchList = load();
  }

  /**
   * Explicit value constructor. Sets the model to have the passed map of ID,
   * Movie pairs.
   * 
   * @param watchList
   *          the watch-list to initialize this model with
   */
  public WatchListModel(HashMap<String, Movie> watchList)
  {
    this.watchList = watchList;
  }

  /**
   * Loads the watch-list at WATCH_LIST_PATH's information into this
   * WatchListModel. If there is no watch-list saved at WATCH_LIST_PATH, an
   * empty one is created.
   * 
   * @return A watch-list containing the information stored at the defined path,
   *         or an empty one if no watch-list exists.
   */
  private HashMap<String, Movie> load()
  {
    ObjectMapper mapper = new ObjectMapper();
    File watchListFile = new File(WatchListModel.WATCH_LIST_PATH);
    // no file exists, so just make an empty watch-list
    if (!watchListFile.exists())
    {
      return new HashMap<String, Movie>();
    }
    // file exists, so try to read it
    try
    {
      TypeReference<HashMap<String, Movie>> typeRef = new TypeReference<HashMap<String, Movie>>()
      {
      };
      return mapper.readValue(watchListFile, typeRef);
    }
    catch (IOException e)
    {
      // file reading caused an error, so create an empty watch-list
      System.err.println("An error occurred in reading the watch-list.\n"
          + "Creating an empty watch-list");
      e.printStackTrace();
      return new HashMap<String, Movie>();
    }
  }

  /**
   * Saves the current watch-list as a JSON file at WATCH_LIST_PATH.
   */
  public void save()
  {
    File file = new File(WATCH_LIST_PATH);
    if (!file.exists())
    {
      try
      {
        file.createNewFile();
      }
      catch (IOException e)
      {
        System.err.println("Issue occurred when creating the watch-list file "
            + "during the save process.");
        return;
      }
    }
    // file exists, so write to it
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      mapper.writeValue(file, watchList);
    }
    catch (IOException e)
    {
      System.err.println(
          String.format("Watch-list data could not be written to disk"));
      e.printStackTrace();
    }
  }

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
    if (watchList.containsKey(movie.getId()))
    {
      return false;
    }
    watchList.put(movie.getId(), movie);
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
    if (!watchList.containsKey(movie.getId()))
    {
      return false;
    }
    watchList.remove(movie.getId());
    return true;
  }

  /**
   * Clears this watchList.
   */
  public void clear()
  {
    watchList.clear();
  }

  /**
   * Getter for watchList's stored movies.
   * 
   * @return the collection of movies that is the movies on the model's stored
   *         watch-list
   */
  public Collection<Movie> watchList()
  {
    return watchList.values();
  }

  /**
   * Getter for watchList.
   * 
   * @return the hashmap that defines this storage of a watch-list
   */
  public HashMap<String, Movie> getWatchList()
  {
    return watchList;
  }
}
