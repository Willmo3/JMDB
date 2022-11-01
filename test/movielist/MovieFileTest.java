package movielist;

import list.MovieList;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieListTest {
  static File movieJson;

  static {
    // Note: results.json must be present in this directory for tests to function.
    movieJson = new File("test/movielist/results.json");
  }

  @Test
  void listParsesWithoutException() {
    MovieList list = new MovieList(movieJson);
  }
}
