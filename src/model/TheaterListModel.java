package model;

import com.fasterxml.jackson.core.type.TypeReference;
import controller.JmdbSerializer;
import list.MovieList;
import media.Movie;
import search.JsonRequestor;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a Theater List file and methods to keep it updated.
 *
 * @author William Morris
 * @version 12/10/2022
 */
public class TheaterListModel
{
  public static final String DATA = "./data";
  public static final String BASE_PATH = "./data/theaterlist%s.json";
  public static final TypeReference<HashMap<String, Movie>> THEATER_REFERENCE = new TypeReference<>() {
  };

  private HashMap<String, Movie> movies;

  /**
   * Constructor. Saves a new file at BASE_PATH + date if the file at PATH is too old, or if no file yet exists at path.
   * Otherwise, loads file at PATH.
   */
  public TheaterListModel()
  {
    String path = String.format(BASE_PATH, getDate());
    File target = new File(path);
    // Ensure no old files are present.
    cleanDirectory(target.getName());

    // If target does not exist, then that means that it's time for a new day's search.
    if (!target.exists())
    {
      generateList(path);
    }
    else
    {
      movies = (HashMap<String, Movie>) JmdbSerializer.deserialize(path, THEATER_REFERENCE);
      if (movies == null)
      {
        System.err.println("Unable to deserialize movie list!");
        generateList(path);
      }
    }
  }

  /**
   * Method to generate a list of movies from JSonRequestor.
   * Also serializes those movies -- since this will only be necessary when the list generator is called,
   * It is a part of this method.
   */
  private void generateList(String path)
  {
    MovieList list = JsonRequestor.getInTheaters();
    movies = new HashMap<>();

    if (list == null)
    {
      System.err.println("Warning! Unable to get in theater list.");
      return;
    }

    List<Movie> inTheaters = list.getMovieList();
    for (Movie m : inTheaters)
    {
      movies.put(m.getId(), m);
    }
    JmdbSerializer.serialize(path, movies);
  }

  /**
   * Cleans out all files other than today's file from the data directory.
   *
   * @param todayName File to not delete.
   */
  private void cleanDirectory(String todayName) {
    File directory = new File(DATA);
    File[] files = directory.listFiles();

    if (files != null) {
      for (File f : files) {
        if (f.getName().startsWith("theaterlist") && !f.getName().equals(todayName)) {
          f.delete();
        }
      }
    }
  }

  /**
   * Gets a String representation of today's date.
   * Format: year, month, day.
   *
   * @return today's date.
   */
  private String getDate()
  {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    return date.format(now);
  }

  /**
   * Gets all movies in this movie list.
   *
   * @return the collection of mvoies.
   */
  public Collection<Movie> getMovies()
  {
    return movies.values();
  }
}
