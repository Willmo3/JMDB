package list;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import media.Movie;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single list of movies derived from a search.
 * NOTE: Movies are currently only compatible with searches by TITLE.
 * That is, they don't have fields for things like reviews.
 * This should be changed later, but it will require a more in-depth knowledge of how we'll access that data.
 *
 * @author William Morris
 * @version 11/4/2022
 */
public class MovieList {

  private List<Movie> movieList;

  /**
   * Parses a MovieList from an InputStream.
   * Note that this assumes the InputStream contains json-formatted data.
   *
   * @param source Input Stream from which to derive the list.
   * @throws IOException If errors in parsing list.
   */
  public MovieList(InputStream source) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode results = mapper.readTree(source).get("results");
    movieList = new ArrayList<Movie>();

    for (JsonNode movie : results) {
      movieList.add(new Movie(movie.get("id").asText(), movie.get("image").asText(),
          movie.get("title").asText(), movie.get("description").asText()));
    }
  }

  /**
   * Query a movie based on String MovieId.
   * Searches this list using linear search.
   * Returns a movie with the provided movieId, or null if none found.
   *
   * @param movieId Movie ID to search for.
   * @return Found movie or null.
   */
  public Movie queryMovie(String movieId) {
    if (movieId == null) {
      return null;
    }

    for (Movie movie: movieList) {
      if (movie.getId().equals(movieId)) {
        return movie;
      }
    }

    return null;
  }

  /**
   * Query a movie based on a movie object.
   * Searches this list using linear search.
   * Returns a movie equal to the movie object., or null if none found.
   *
   * @param toFind Movie ID to search for.
   * @return Found movie or null.
   */
  public Movie queryMovie(Movie toFind) {
    if (toFind == null) {
      return null;
    }

    for (Movie movie: movieList) {
      if (movie.equals(toFind)) {
        return movie;
      }
    }

    return null;
  }

  /**
   * Generates a JList from this list of movies.
   *
   * @return the JList.
   */
  public JList<Movie> generateJList() {
    // Note that the list is being copied into an array manually
    // Because java has complained about casting ArrayList.toArray() to Movie[].
    int size = movieList.size();
    Movie[] model = new Movie[size];

    for (int i = 0; i < size; i++) {
      model[i] = movieList.get(i);
    }

    return new JList<Movie>(model);
  }

  public List<Movie> getMovieList() {
    return movieList;
  }
}
