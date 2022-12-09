package model;

import com.fasterxml.jackson.core.type.TypeReference;
import controller.JmdbController;
import controller.JmdbSerializer;
import list.MovieList;
import list.TheaterList;
import media.Movie;
import media.TheaterMovie;
import search.JsonRequestor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Theater List file and methods to keep it updated.
 *
 * @author William Morris
 * @version 12/8/2022
 */
public class TheaterListModel
{
  public static final String BASE_PATH = "./data/theaterlist%s.json";
  public static final TypeReference<ArrayList<String>> THEATER_REFERENCE = new TypeReference<ArrayList<String>>() {
  };

  private List<String> movieIDs;
  private JmdbController controller;

  /**
   * Constructor. Saves a new file at BASE_PATH + date if the file at PATH is too old, or if no file yet exists at path.
   * Otherwise, loads file at PATH.
   *
   * @param controller controller for this TheaterListModel. Allows access to movie cache.
   */
  public TheaterListModel(JmdbController controller)
  {
    this.controller = controller;
    String path = String.format(BASE_PATH, getDate());
    File target = new File(path);

    // If target does not exist, then that means that it's time for a new day's search.

    if (!target.exists())
    {
      MovieList list = JsonRequestor.getInTheaters();
      movieIDs = new ArrayList<>();
      for (Movie m : list.getMovieList())
      {
        movieIDs.add(m.getId());
        controller.addToCache(m);
      }
      JmdbSerializer.serialize(path, movieIDs);
    }
    else
    {
      movieIDs = (ArrayList<String>) JmdbSerializer.deserialize(path, THEATER_REFERENCE);
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

  public List<String> getMovies()
  {
    return movieIDs;
  }
}
