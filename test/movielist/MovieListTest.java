package movielist;

import list.MovieFile;
import list.MovieList;
import list.movie.Movie;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for MovieList.
 *
 * @author Will Morris
 * @version 11/1/2022
 */
public class MovieListTest {
  private static MovieList list;

  static {
    MovieFile file = new MovieFile(new File("test/movielist/results.json"));
    try {
      list = new MovieList(file.getStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testListAccurate() throws IOException {
    List<Movie> movieList = list.getMovieList();
    List<Movie> expected = new ArrayList<Movie>();

    expected.add(new Movie("tt1375666",
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)"));
    expected.add(new Movie("tt1790736",
        "https://m.media-amazon.com/images/M/MV5BMjE0NGIwM2EtZjQxZi00ZTE5LWExN2MtNDBlMjY1ZmZkYjU3XkEyXkFqcGdeQXVyNjMwNzk3Mjk@._V1_Ratio0.6800_AL_.jpg",
        "Inception: The Cobol Job", "(2010 Video)"));
    expected.add(new Movie("tt5295990",
        "https://m.media-amazon.com/images/M/MV5BZGFjOTRiYjgtYjEzMS00ZjQ2LTkzY2YtOGQ0NDI2NTVjOGFmXkEyXkFqcGdeQXVyNDQ5MDYzMTk@._V1_Ratio0.6800_AL_.jpg",
        "Inception: Jump Right Into the Action", "(2010 Video)"));
    expected.add(new Movie("tt1686778", "https://imdb-api.com/images/original/nopicture.jpg",
        "Inception: 4Movie Premiere Special", "(2010 TV Movie)"));
    expected.add(new Movie("tt12960252", "https://imdb-api.com/images/original/nopicture.jpg",
        "Inception Premiere", "(2010)"));

    assertEquals(expected.size(), movieList.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), movieList.get(i));
    }
  }
}
