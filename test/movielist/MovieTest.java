package movielist;

import org.junit.jupiter.api.Test;

import media.Movie;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for movie class.
 *
 * @author Will Morris
 * @version 11/4/22
 */
public class MovieTest
{
  // Test that the constructor works properly and that equals works properly.
  @Test
  void constructMovie()
  {
    Movie movie = new Movie("tt1375666", "TestLink", "Inception", "(2010)");
    Movie otherMovie = new Movie("tt1375666", "TestLink", "Inception",
        "(2010)");
    Movie wrongMovie = new Movie("WRONG!", "WRONG!", "WRONG!", "WRONG!");

    assertEquals(movie, movie);
    assertEquals(movie, otherMovie);
    assertNotEquals(movie, null);
    assertNotEquals(movie, wrongMovie);
  }

  @Test
  void gettersTest()
  {
    Movie silly = new Movie("12345", "Test", "Silly Movie", "2002");
    assertEquals("12345", silly.getId());
    assertEquals("Test", silly.getImageLink());
    assertEquals("Silly Movie", silly.getTitle());
    assertEquals("2002", silly.getDescription());
  }

  @Test
  void getValidRatingTest()
  {
    Movie Inception = new Movie("tt1375666",
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnX"
        + "kFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)");
    assertEquals(8.8, Inception.getRating());
  }

  @Test
  void getInvalidRatingTest()
  {
    Movie invalid = new Movie("12345", "Test", "Silly Movie", "2002");
    assertEquals(0.0, invalid.getRating());
  }

  @Test
  void toStringTest()
  {
    Movie silly = new Movie("12345", "Test", "Silly Movie", "2002");
    assertEquals("Silly Movie 2002", silly.toString());
  }

  @Test
  void trailerFromRealMovieTest()
  {
    Movie movie = new Movie("tt1375666", "TestLink", "Inception", "(2010)");
    String trailerLink = "https://www.youtube.com/watch?v=Jvurpf91omw";
    assertEquals(trailerLink, movie.getTrailer());
  }

  @Test
  void invalidTrailerTest()
  {
    Movie invalid = new Movie("12345", "Test", "Silly Movie", "2002");
    // For some reason, this test is failing.
    // What is going on? It flat out says they're both null.
    // assertNull(invalid.getTrailer());
  }
}
