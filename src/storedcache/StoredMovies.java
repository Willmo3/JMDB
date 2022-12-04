package storedcache;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import media.Movie;

/**
 * The object that contains a permanent cache of movie data that is updated on
 * movie selection, and written to disk on application close.
 * 
 * @author Matthew Potter
 * @version 11/30/2022
 */
public class StoredMovies
{
  /**
   * The path that should store the cache JSON file.
   */
  public static final String CACHE_FILE_PATH = "./data/cachedMovies.json";

  private HashMap<String, Movie> movieCache;

  /**
   * Constructs this StoredMovies object. Initializes the object with the loaded
   * cache data from disk, or with a new, empty cache if there isn't a cache to
   * load.
   */
  public StoredMovies()
  {
    this.movieCache = load();
  }

  /**
   * Shallow copy constructor. Sets the cache to have the passed map of ID,
   * Movie pairs.
   * 
   * @param movieCache
   *          the cache to initialize this model with
   */
  public StoredMovies(HashMap<String, Movie> movieCache)
  {
    this.movieCache = movieCache;
  }

  /**
   * Loads the movie cache at CACHE_FILE_PATH's information into this movie
   * cache. If there is nothing saved at CACHE_FILE_PATH, an empty cache is
   * created and is used for this movie cache.
   * 
   * @return A cache containing the information stored at the defined path, or
   *         an empty one if no saved cache exists.
   */
  private HashMap<String, Movie> load()
  {
    ObjectMapper mapper = new ObjectMapper();
    File cacheFile = new File(StoredMovies.CACHE_FILE_PATH);
    // no file exists, so just make an empty cache
    if (!cacheFile.exists())
    {
      return new HashMap<String, Movie>();
    }
    // file exists, so try to read it
    try
    {
      TypeReference<HashMap<String, Movie>> typeRef = new TypeReference<HashMap<String, Movie>>()
      {
      };
      return mapper.readValue(cacheFile, typeRef);
    }
    catch (IOException e)
    {
      // file reading caused an error, so create an empty cache
      System.err.println("An error occurred in reading the movie cache.\n"
          + "Creating an empty movie cache");
      e.printStackTrace();
      return new HashMap<String, Movie>();
    }
  }

  /**
   * Saves the current list of movies in the cache as a JSON file at CACHE_PATH.
   */
  public void save()
  {
    File file = new File(CACHE_FILE_PATH);
    if (!file.exists())
    {
      try
      {
        file.createNewFile();
      }
      catch (IOException e)
      {
        System.err.println("Issue occurred when creating the cache file "
            + "during the save process.");
        return;
      }
    }
    // file exists, so write to it
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      mapper.writeValue(file, movieCache);
    }
    catch (IOException e)
    {
      System.err
          .println(String.format("Cache data could not be written to disk"));
      e.printStackTrace();
    }
  }

  /**
   * Adds the passed movie to the cache. If the movie is already in the cache
   * nothing is added.
   * 
   * @param movie
   *          the movie to add to the cache
   * @return true if the movie was added, false if not.
   */
  public boolean add(Movie movie)
  {
    if (movieCache.containsKey(movie.getId()))
    {
      return false;
    }
    movieCache.put(movie.getId(), movie);
    return true;
  }

  /**
   * Removes the passed movie from the cache. If the movie isn't in the cache
   * nothing is removed.
   * 
   * @param movie
   *          the movie to remove from the cache
   * @return true if the movie was removed, false if not.
   */
  public boolean remove(Movie movie)
  {
    if (!movieCache.containsKey(movie.getId()))
    {
      return false;
    }
    movieCache.remove(movie.getId());
    return true;
  }

  /**
   * Clears this cache.
   */
  public void clear()
  {
    movieCache.clear();
  }

  /**
   * Getter for movieCache's stored movies.
   * 
   * @return the collection of movies in the cache
   */
  public Collection<Movie> cache()
  {
    return movieCache.values();
  }

  /**
   * Getter for the cache hashmap.
   * 
   * @return the hashmap that defines this cache
   */
  public HashMap<String, Movie> getMovieCache()
  {
    return movieCache;
  }
}
