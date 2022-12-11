package theaterlist;

import list.TheaterList;
import media.Movie;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for movie Theater List
 *
 * @author William Morris
 * @version 12/8/2022
 */
public class TheaterListTest {

  @Test
  void testParseTheaterMovieFile() throws IOException {
    File theaterFile = new File("test/theaterlist/theatertest.json");
    FileInputStream fis = new FileInputStream(theaterFile);
    TheaterList list = new TheaterList(fis);
    List<Movie> movies = list.getMovieList();

    assertEquals(1, movies.size());
    assertTrue(movies.get(0) instanceof Movie);
  }
}
