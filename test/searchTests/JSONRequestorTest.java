package searchTests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

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
  static File searchJSON = new File("test/searchTests/testSearch.json");
  static String searchJSONText = "";
  static
  {
    Scanner fileReader = null;
    try
    {
      fileReader = new Scanner(searchJSON);
    }
    catch (FileNotFoundException e)
    {
      System.err.println("Cannot find searchJSON in test");
    }
    finally
    {
      if (fileReader != null)
      {
        while (fileReader.hasNextLine())
        {
          searchJSONText += fileReader.nextLine();
        }
        if (searchJSONText == "")
        {
          System.err.println("JSON text is empty");
        }
        fileReader.close();
      }
    }
  }
  static String queryText = "inception 2010";

  @Test
  public void testRequestJSON()
  {
    assertEquals(searchJSONText, JSONRequestor.search(queryText));
    assertEquals(searchJSONText,
        JSONRequestor.search(QueryTypes.TITLE, queryText));

  }

}
