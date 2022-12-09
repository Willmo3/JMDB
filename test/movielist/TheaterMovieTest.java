package movielist;

import media.ResultTypes;
import media.TheaterMovie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a movie in-theaters is properly set up.
 *
 * @author Will Morris
 * @version 12/8/2022
 */
public class TheaterMovieTest {
  @Test
  void testConstruction() {
    TheaterMovie movie = new TheaterMovie("Test", "TestLink", "TestTitle", "TestDesc");
    assertEquals("Test", movie.getId());
    assertEquals("TestLink", movie.getImageLink());
    assertEquals("TestTitle", movie.getTitle());
    assertEquals("TestDesc", movie.getDescription());
  }
}
