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
  private static Movie silly;
  private static double[] inceptionRatings = {8.8, 74, 8.4, 87, 8.0};
  private static double[] cobolRatings = {7.5, Movie.NO_RATING, Movie.NO_RATING,
      Movie.NO_RATING, Movie.NO_RATING};
  private static double[] sillyRatings = {4.4, Movie.NO_RATING, Movie.NO_RATING,
      Movie.NO_RATING, Movie.NO_RATING};

  static
  {
    // for coverage
    new Movie();
    new Movie("WRONG!", ResultTypes.INVALID, "WRONG!", "WRONG!", "WRONG!");

    inception = new Movie("tt1375666", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnX"
            + "kFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)", inceptionRatings,
        "https://www.youtube.com/watch?v=Jvurpf91omw",
        "Academy Awards, USA, 2011, Winner, Oscar",
        "https://en.wikipedia.org/wiki/Inception", true);
    inceptionCopy = new Movie("tt1375666", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnX"
            + "kFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)", inceptionRatings,
        "https://www.youtube.com/watch?v=Jvurpf91omw",
        "Academy Awards, USA, 2011, Winner, Oscar",
        "https://en.wikipedia.org/wiki/Inception", true);
    cobol = new Movie("tt1790736", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjE0NGIwM2EtZjQxZi00ZTE5LWEx"
            + "N2MtNDBlMjY1ZmZkYjU3XkEyXkFqcGdeQXVyNjMwNzk3Mjk"
            + "@._V1_Ratio0.6800_AL_.jpg",
        "Inception: The Cobol Job", "(2010 Video)", cobolRatings, "no link",
        "This film did not win any awards", "no link", true);
    silly = new Movie("12345", ResultTypes.TITLE, "image", "Silly Movie",
        "description", sillyRatings, "trailer", "award", "wiki", true);
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
  @Order(1)
  void gettersAndOtherSettersTest()
  {
    double[] otherRatings = {8.8, 74, 8.4, 87, 8.0};
    assertEquals("12345", silly.getId());
    assertEquals(ResultTypes.TITLE, silly.getType());
    assertEquals("image", silly.getImageLink());
    assertEquals("Silly Movie", silly.getTitle());
    assertEquals("description", silly.getDescription());
    assertEquals(Movie.NO_RATING, silly.getRatings()[1]);
    assertEquals(4.4, silly.getRatings()[0]);
    assertEquals("trailer", silly.getTrailerLink());
    assertEquals("award", silly.getAward());
    assertEquals("wiki", silly.getWiki());
    // want retrievedRatings true so as to not call API
    assertTrue(silly.isRetrievedRatings());
    silly.setRetrievedRatings(false);
    assertFalse(silly.isRetrievedRatings());
    silly.setRetrievedRatings(true);
    assertTrue(silly.isRetrievedRatings());
    silly.setType(ResultTypes.INVALID);
    silly.setRatings(otherRatings);
    silly.setTrailerLink("none");
    silly.setAward("none");
    silly.setWiki("none");
    assertEquals(ResultTypes.INVALID, silly.getType());
    assertEquals(8.8, silly.getRatings()[0]);
    assertEquals("none", silly.getTrailerLink());
    assertEquals("none", silly.getAward());
    assertEquals("none", silly.getWiki());
  }

  @Test
  @Order(2)
  void testNullGetters()
  {
    double[] emptyRatings = {Movie.NO_RATING, Movie.NO_RATING, Movie.NO_RATING,
        Movie.NO_RATING, Movie.NO_RATING};
    silly.setRatings(null);
    assertNull(silly.getRatings());
    silly.setRatings(emptyRatings);
    assertNull(silly.getRatings());
    silly.setTrailerLink("");
    assertNull(silly.getTrailerLink());
    assertNull(silly.getTrailerLink());
    silly.setAward("");
    assertNull(silly.getAward());
    assertNull(silly.getAward());
    silly.setWiki("");
    assertNull(silly.getWiki());
    assertNull(silly.getWiki());
  }

  @Test
  @Order(1)
  void testToString()
  {
    assertEquals("Inception (2010)", inception.toString());
  }

  /*
   * Commented out as these tests make calls to the API
   * 
   * @Test
   * 
   * @Order(4) void getInvalidRatingTest() { Movie invalid = new Movie("12345",
   * ResultTypes.INVALID, "Test", "Silly Movie", "2002"); assertEquals(0.0,
   * invalid.getImdbRating()); }
   * 
   * @Test
   * 
   * @Order(5) void toStringTest() { Movie silly = new Movie("12345",
   * ResultTypes.TITLE, "Test", "Silly Movie", "2002");
   * assertEquals("Silly Movie 2002", silly.toString()); }
   * 
   * @Test
   * 
   * @Order(6) void trailerFromRealMovieTest() { Movie movie = new
   * Movie("tt1375666", ResultTypes.TITLE, "TestLink", "Inception", "(2010)");
   * String trailerLink = "https://www.youtube.com/watch?v=Jvurpf91omw";
   * assertEquals(trailerLink, movie.getTrailerLink()); }
   * 
   * @Test
   * 
   * @Order(7) void invalidTrailerTest() { Movie invalid = new Movie("12345",
   * ResultTypes.TITLE, "Test", "Silly Movie", "2002");
   * assertNull(invalid.getTrailerLink()); }
   */
}
