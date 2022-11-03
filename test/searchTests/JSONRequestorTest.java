package searchTests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

import list.MovieList;
import search.JSONRequestor;
import search.JSONRequestor.QueryTypes;

/**
 * A test class for when requesting JSON through the IMDB api
 * 
 * @author Matthew Potter
 * @version 11/03/2022
 */
public class JSONRequestorTest
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
  public void testRequestJSON()
  {
    assertEquals(testList.getMovieList(),
        JSONRequestor.search(queryText).getMovieList());
    assertEquals(testList.getMovieList(),
        JSONRequestor.search(QueryTypes.TITLE, queryText).getMovieList());

  }

}
