package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;

import list.MovieList;
import mainGUI.JmdbGUI;
import media.Movie;
import model.TheaterListModel;

/**
 * The controller for the JMDb program. Interfaces with the GUI and Model.
 * 
 * @author Matthew Potter, William Morris
 * @version 12/03/2022
 */
public class JmdbController
{
  // Paths to the associated data JSON files in the data directory
  private static final String CACHE_FILE_PATH = "./data/cachedMovies.json";
  private static final String FEATURED_LIST_PATH = "./data/FeaturedMovieList.json";
  private static final String WATCH_LIST_PATH = "./data/watchlist.json";

  // TypeReferences to the data structures used in the application's data models
  private static final TypeReference<HashMap<String, Movie>> STORE_MOVIE_TYPE = new TypeReference<HashMap<String, Movie>>()
  {
  };
  private static final TypeReference<ArrayList<String>> WATCH_LIST_TYPE = new TypeReference<ArrayList<String>>()
  {
  };

  private JmdbGUI gui;
  // the cacheModel and featuredListModel both store Movie objects in a map
  // based on the Movie's ID.
  private HashMap<String, Movie> cacheModel;
  private HashMap<String, Movie> featuredListModel;
  // the watchListModel stores the IDs of the Movies added to the watch-list so
  // that it can be saved to disk without duplicating movies in the cache
  private ArrayList<String> watchListModel;
  // booleans denoting if the cache or watchList were updated during runtime
  private boolean cacheChanged;
  private boolean watchListChanged;
  // Model for TheaterList.
  private TheaterListModel theaterList;

  /**
   * Empty constructor. Made for testing non-GUI function and, as such, none of
   * the GUI related methods will work.
   */
  public JmdbController() {
    this.gui = null;
    cacheChanged = false;
    watchListChanged = false;
    loadSavedData();
    theaterList = new TheaterListModel();
  }

  /**
   * Sets the JmdbGUI that this controller is meant to update.
   * 
   * @param gui
   *          the GUI to set the current one to be
   */
  public void setGui(JmdbGUI gui)
  {
    this.gui = gui;
  }

  /**
   * Sets up the Model lists that the application uses with the appropriate data
   * stored in the associated file in the application's data directory.
   * 
   * Suppresses unchecked warnings as we can be sure the data is the defined
   * type as it is saved as such in the associated save methods.
   */
  @SuppressWarnings("unchecked")
  private void loadSavedData()
  {
    cacheModel = (HashMap<String, Movie>) JmdbSerializer
        .deserialize(CACHE_FILE_PATH, STORE_MOVIE_TYPE);
    featuredListModel = (HashMap<String, Movie>) JmdbSerializer
        .deserialize(FEATURED_LIST_PATH, STORE_MOVIE_TYPE);
    watchListModel = (ArrayList<String>) JmdbSerializer
        .deserialize(WATCH_LIST_PATH, WATCH_LIST_TYPE);

    // if deserialized object is null, create an empty one
    if (cacheModel == null)
    {
      cacheModel = new HashMap<String, Movie>();
    }
    if (featuredListModel == null)
    {
      featuredListModel = new HashMap<String, Movie>();
    }
    if (watchListModel == null)
    {
      watchListModel = new ArrayList<String>();
    }
  }

  /**
   * Saves all updated data. Called on exit of the application.
   */
  public void saveData()
  {
    if (cacheChanged)
    {
      JmdbSerializer.serialize(CACHE_FILE_PATH, cacheModel);
    }

    if (watchListChanged)
    {
      JmdbSerializer.serialize(WATCH_LIST_PATH, watchListModel);
    }
  }

  /**
   * Adds the passed movie to the cache if it isn't already present in the
   * cache.
   * 
   * @param movie
   *          the movie to add to the cache
   */
  public void addToCache(Movie movie)
  {
    String id = movie.getId();
    if (cacheModel.containsKey(id) || featuredListModel.containsKey(id))
    {
      return;
    }
    cacheModel.put(id, movie);
    cacheChanged = true;
  }

  /**
   * Refreshes the cached data of the passed movie.
   * 
   * @param selected
   *          the Movie whose data should be refreshed
   */
  public void refresh(Movie selected)
  {
    Movie savedMovie;
    if (cacheModel.containsKey(selected.getId()))
    {
      savedMovie = cacheModel.get(selected.getId());
      cacheChanged = true;
    }
    else
    {
      return;
    }
    // make API query data be unset so the next get call updates their values
    savedMovie.setRetrievedRatings(false);
    savedMovie.setTrailerLink(Movie.DEFAULT_TRAILER);
    savedMovie.setAward(Movie.DEFAULT_AWARD);
    savedMovie.setWiki(Movie.DEFAULT_WIKI);
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        // call all of the newly-unset API queries to refresh their values
        savedMovie.getRatings();
        savedMovie.getTrailerLink();
        savedMovie.getAward();
        savedMovie.getWiki();
        cacheModel.replace(selected.getId(), savedMovie);
      }
    }).start();
  }

  /**
   * Adds the passed movie to the watch-list if it isn't already present in the
   * watch-list.
   * 
   * @param selectedMovie
   *          the movie to add to watch-list
   * @return true if the movie was added, false if not
   */
  public boolean addToWatchList(Movie selectedMovie)
  {
    String id = selectedMovie.getId();
    // if the Movie is already in the watchListModel
    boolean prevAdded = watchListModel.contains(id);
    if (!prevAdded)
    {
      watchListModel.add(id);
      watchListChanged = true;
    }
    return !prevAdded;
  }

  /**
   * Removes the passed movie from the watch-list if it is present in the list.
   * 
   * @param selectedMovie
   *          the movie to remove from the watch-list
   * @return true if the movie was removed, false if not
   */
  public boolean removeFromWatchList(Movie selectedMovie)
  {
    String id = selectedMovie.getId();
    boolean removed = watchListModel.remove(id);
    if (removed)
    {
      watchListChanged = true;
    }
    return removed;
  }

  /**
   * Requests data from the API for startup content. Startup content is the list
   * of Movies that are currently in theaters. If this request cannot be made,
   * defaults to returning the Featured Movies list. (Currently only returns the
   * featured movie list)
   * 
   * @return the collection containing the Movies to be listed on startup
   */
  public Collection<Movie> startupListContent()
  {
    return featuredListModel.values();
  }

  /**
   * Messages the GUI to update its search list based on the MovieList that is
   * passed.
   * 
   * @param list
   *          the movieList to update the search list based on
   */
  public void updateSearchList(MovieList list)
  {
    gui.getMoviesJlist().setValueIsAdjusting(true);
    gui.updateSearchList(list.getMovieList());
    gui.getMoviesJlist().setValueIsAdjusting(false);
  }

  /**
   * Gets the Collection of Movies that are indicated by the stored ID values in
   * the watchListModel. As each movie in the watch-list must have been selected
   * and therefore cached before being added, the watch-list can search the
   * cacheModel for the Movies to populate the Collection with.
   * 
   * @return the Collection of Movie objects in the watch-list
   */
  public Collection<Movie> getWatchList()
  {
    ArrayList<Movie> watchList = new ArrayList<Movie>();
    for (String id : watchListModel)
    {
      // if the movie in the watchlist isn't in the cache, it must be in the
      // featured movies list, the cache was cleared, or some error occurred
      // Movies won't be added to the list if they aren't present in either list
      if (!watchList.add(cacheModel.get(id)))
      {
        watchList.add(featuredListModel.get(id));
      }
    }
    return watchList;
  }

  /**
   * In Theater List getter.
   * For each ID in TheaterList, adds a movie object to the return value.
   * Then, returns new collection of all movies.
   *
   * @return Collection of all movies in theaters.
   */
  public Collection<Movie> getInTheatersList()
  {
    return theaterList.getMovies();
  }

  /**
   * Gets the featured movie list.
   *
   * @return the featured movie list.
   */
  public Collection<Movie> getFeaturedMovieList()
  {
    return featuredListModel.values();
  }

  /**
   * Getter for the map of featured movies.
   * 
   * @return the hashmap of ID, Movie pairs that make up the featured list model
   */
  public HashMap<String, Movie> getFeaturedListModel()
  {
    return featuredListModel;
  }

  /**
   * Getter for the cached movies.
   * 
   * @return the hashmap of ID, Movie pairs that make up the cache
   */
  public HashMap<String, Movie> getCachedMovies()
  {
    return cacheModel;
  }
}
