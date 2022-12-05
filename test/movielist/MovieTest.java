package movielist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import media.Movie;
import media.ResultTypes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

/**
 * Test for movie class.
 *
 * @author Will Morris, Matthew Potter
 * @version 12/04/22
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieTest
{
  private static Movie inception;
  private static Movie inceptionCopy;
  private static Movie cobol;

  static
  {
    // for coverage
    new Movie();
    new Movie("WRONG!", ResultTypes.INVALID, "WRONG!", "WRONG!", "WRONG!");
    new Movie("12345", ResultTypes.TITLE, "Test", "Silly Movie", "2002", 1.0,
        "trailer-link", "silly award", "wiki-link");

    inception = new Movie("tt1375666", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnX"
            + "kFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)", 8.8,
        "https://www.youtube.com/watch?v=Jvurpf91omw",
        "Academy Awards, USA, 2011, Winner, Oscar",
        "https://en.wikipedia.org/wiki/Inception");
    inceptionCopy = new Movie("tt1375666", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnX"
            + "kFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)", 8.8,
        "https://www.youtube.com/watch?v=Jvurpf91omw",
        "Academy Awards, USA, 2011, Winner, Oscar",
        "https://en.wikipedia.org/wiki/Inception");
    cobol = new Movie("tt1790736", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjE0NGIwM2EtZjQxZi00ZTE5LWEx"
            + "N2MtNDBlMjY1ZmZkYjU3XkEyXkFqcGdeQXVyNjMwNzk3Mjk"
            + "@._V1_Ratio0.6800_AL_.jpg",
        "Inception: The Cobol Job", "(2010 Video)", 7.5, "no link",
        "This film did not win any awards", "no link");
  }

  @Test
  @Order(1)
  void equalsAndSomeSettersTest()
  {
    assertEquals(inception, inception);
    assertEquals(inception, inceptionCopy);
    assertNotEquals(inception, null);
    assertNotEquals(inception, cobol);
    // for coverage of all branches
    inceptionCopy.setDescription("Different");
    assertNotEquals(inception, inceptionCopy);
    inceptionCopy.setTitle("Different");
    assertNotEquals(inception, inceptionCopy);
    inceptionCopy.setImageLink("Different");
    assertNotEquals(inception, inceptionCopy);
    inceptionCopy.setId("Different");
    assertNotEquals(inception, inceptionCopy);
  }

  @Test
  void gettersAndOtherSettersTest()
  {
    Movie silly = new Movie("12345", ResultTypes.TITLE, "image", "Silly Movie",
        "description", 4.4, "trailer", "award", "wiki");
    assertEquals("12345", silly.getId());
    assertEquals("image", silly.getImageLink());
    assertEquals("Silly Movie", silly.getTitle());
    assertEquals("description", silly.getDescription());
    assertEquals(4.4, silly.getImdbRating());
    assertEquals("trailer", silly.getTrailerLink());
    assertEquals("award", silly.getAward());
    assertEquals("wiki", silly.getWiki());
    assertEquals(ResultTypes.TITLE, silly.getType());
    silly.setImdbRating(0.0);
    silly.setTrailerLink("none");
    silly.setAward("none");
    silly.setWiki("none");
    silly.setType(ResultTypes.INVALID);
    assertEquals(0.0, silly.getImdbRating());
    assertEquals("none", silly.getTrailerLink());
    assertEquals("none", silly.getAward());
    assertEquals("none", silly.getWiki());
    assertEquals(ResultTypes.INVALID, silly.getType());
    silly.setWiki("");
    assertNull(silly.getWiki());
    assertNull(silly.getWiki());
  }

  /*
   * Commented out as these tests make calls to the API
  @Test
  @Order(4)
  void getInvalidRatingTest()
  {
    Movie invalid = new Movie("12345", ResultTypes.INVALID, "Test",
        "Silly Movie", "2002");
    assertEquals(0.0, invalid.getImdbRating());
  }

  @Test
  @Order(5)
  void toStringTest()
  {
    Movie silly = new Movie("12345", ResultTypes.TITLE, "Test", "Silly Movie",
        "2002");
    assertEquals("Silly Movie 2002", silly.toString());
  }

  @Test
  @Order(6)
  void trailerFromRealMovieTest()
  {
    Movie movie = new Movie("tt1375666", ResultTypes.TITLE, "TestLink",
        "Inception", "(2010)");
    String trailerLink = "https://www.youtube.com/watch?v=Jvurpf91omw";
    assertEquals(trailerLink, movie.getTrailerLink());
  }

  @Test
  @Order(7)
  void invalidTrailerTest()
  {
    Movie invalid = new Movie("12345", ResultTypes.TITLE, "Test", "Silly Movie",
        "2002");
    assertNull(invalid.getTrailerLink());
  }
  */
}
