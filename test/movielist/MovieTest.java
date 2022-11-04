package movielist;

import org.junit.jupiter.api.Test;

import media.Movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test for movie class.
 *
 * @author Will Morris
 * @version 11/4/22
 */
public class MovieTest {
  // Test that the constructor works properly and that equals works properly.
  @Test
  void constructMovie() {
    Movie movie = new Movie("tt1375666", "TestLink", "Inception", "(2010)");
    Movie otherMovie = new Movie("tt1375666", "TestLink", "Inception", "(2010)");
    Movie wrongMovie = new Movie("WRONG!", "WRONG!", "WRONG!", "WRONG!");

    assertEquals(movie, movie);
    assertEquals(movie, otherMovie);
    assertNotEquals(movie, null);
    assertNotEquals(movie, wrongMovie);
  }

  @Test
  void gettersTest() {
    Movie silly = new Movie("12345", "Test", "Silly Movie", "2002");
    assertEquals("12345", silly.getId());
    assertEquals("Test", silly.getImageLink());
    assertEquals("Silly Movie", silly.getTitle());
    assertEquals("2002", silly.getDescription());
  }

  @Test
  void toStringTest() {
    Movie silly = new Movie("12345", "Test", "Silly Movie", "2002");
    assertEquals("Silly Movie 2002", silly.toString());
  }
}
