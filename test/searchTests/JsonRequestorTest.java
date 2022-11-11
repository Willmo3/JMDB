package searchTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import list.MovieList;
import org.junit.jupiter.api.Test;
import search.JsonRequestor;
import search.JsonRequestor.QueryTypes;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A test class for when requesting JSON through the IMDB api.
 * 
 * @author Matthew Potter
 * @version 11/10/2022
 */
public class JsonRequestorTest
{
  // opens the testSearch.json file which is the JSON made
  // from the search query to all titles using query "inception 2010"
  static File testFile = new File("test/searchTests/testSearch.json");
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
  static String queryText = "inception 2010";

  @Test
  public void testQueryList()
  {
    assertEquals(testList.getMovieList(),
        JsonRequestor.search(queryText).getMovieList());
    assertEquals(testList.getMovieList(),
        JsonRequestor.search(QueryTypes.TITLE, queryText).getMovieList());
  }

  @Test
  public void testQueryRating()
  {
    assertEquals(88, Math.round(JsonRequestor.queryRating("tt1375666") * 10));
  }

}
