package list;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import media.Movie;
import media.ResultTypes;
import media.TheaterMovie;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * List of movies in theaters.
 * Effectively a standard movie list, but has a different constructor to ensure standard movie compliance.
 *
 * @author William Morris
 * @version 12/8/2022
 */
public class TheaterList extends MovieList {

  /**
   * Parses a TheaterList from an InputStream. Note that this assumes the
   * InputStream contains json-formatted data.
   *
   * @param source Input Stream from which to derive the list.
   * @throws IOException If errors in parsing list.
   */
  public TheaterList(InputStream source) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode results = mapper.readTree(source).get("items");
    movieList = new ArrayList<Movie>();

    for (JsonNode movie : results)
    {
      // Ensures that description of TheaterMovies matches other movies.
      String description = String.format("(%s)", movie.get("year").asText());
      movieList.add(
          new TheaterMovie(movie.get("id").asText(), movie.get("image").asText(),
              movie.get("title").asText(), description));
    }
    source.close();
  }
}
