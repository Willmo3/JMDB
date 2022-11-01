package list;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import list.movie.Movie;

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
 * @version 11/1/2022
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

  public List<Movie> getMovieList() {
    return movieList;
  }
}
