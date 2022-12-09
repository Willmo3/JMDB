package searchTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import list.MovieList;
import media.RatingTypes;
import org.junit.jupiter.api.Test;
import search.JsonRequestor;
import search.JsonRequestor.QueryTypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A test class for when requesting JSON through the IMDB api.
 * 
 * @author Matthew Potter
 * @version 11/10/2022
 */
public class JsonRequestorTest
{
  /**
   * Opens the testSearch.json file which is the JSON made from the search query
   * to all titles using query "inception 2010".
   */
  static File testFile = new File("test/searchTests/testSearch.json");
  /**
   * 
   */
  static MovieList testList;
  static
  {
    try
    {
      InputStream testStream = new FileInputStream(testFile);
      testList = new MovieList(testStream);
    }
    catch (FileNotFoundException e)
    {
      System.err
          .println("Couldn't create stream for testFile in JSONRequestorTest");
    }
    catch (IOException e)
    {
      System.err.println(
          "Failed to parse the testStream into MovieList in JSONRequestorTest");
    }
  }

  /**
   * queryText used to do the search for the test JSON.
   */
  static String queryText = "inception 2010";

  /**
   * ID used to do the search for the Media's data.
   */
  static String queryID = "tt1375666";

  /**
   * Made for coverage purposes.
   */
  JsonRequestor coverage = new JsonRequestor();

  /**
   * Checks to ensure the pulled JSON file's data can be read into a MovieList
   * and hold the correct information. Technically contingent on MovieList's
   * functionality, but whatever, it works.
   */
  @Test
  void testQueryList()
  {
    assertEquals(testList.getMovieList(),
        JsonRequestor.search(queryText).getMovieList());
    assertEquals(testList.getMovieList(),
        JsonRequestor.search(QueryTypes.TITLE, queryText).getMovieList());
  }

  /**
   * Checks to ensure the pulled JSON file's rating information can be pulled
   * and hold the correct information. Contingent on Jackson reading properly,
   * but we can assume such.
   */
  @Test
  void testQueryRating()
  {
    assertEquals(88, Math.round(
        JsonRequestor.queryRating(queryID)[RatingTypes.IMDB.ordinal()] * 10));
    assertTrue(Double.isInfinite(JsonRequestor
        .queryRating("tt1790736")[RatingTypes.METACRITIC.ordinal()]));
    assertNull(JsonRequestor.queryRating("nm0000606"));
  }

  @Test
  void testQueryTrailer()
  {
    assertEquals("https://www.youtube.com/watch?v=Jvurpf91omw",
        JsonRequestor.queryTrailer(queryID));
  }

  @Test
  void testQueryAwards()
  {
    assertEquals("Academy Awards, USA, 2011, Oscar, Winner",
        JsonRequestor.queryAwards(queryID));
  }

  @Test
  void testQueryWiki()
  {
    assertEquals("https://en.wikipedia.org/wiki/Inception",
        JsonRequestor.queryWiki(queryID));
  }

  @Test
  void testQueryCrew()
  {
    assertEquals(
        "(directed by): Christopher Nolan, (written by): Christopher Nolan",
        JsonRequestor.queryCrew(queryID));
    assertEquals("Director: Ian Kirby, Writer: Jordan Goldberg",
        JsonRequestor.queryCrew("tt1790736"));
  }

}
