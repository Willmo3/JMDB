package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class for querying the Internet in order to receive JSON files that
 * include information about searches, ratings, and other aspects of media
 * present on IMDb, all as queried through the IMDb API.
 * 
 * @author Matthew Potter
 * @version 11/03/2022
 */
public final class JSONRequestor
{
  public static enum QueryTypes {
    TITLE, MOVIE, SERIES, NAME, ALL
  }
  
  /**
   * Queries the IMDb API for a JSON text containing a list of films that match
   * the search criteria. Searches are based on title.
   * 
   * @param queryText
   *          the text to be searched for using the IMDb API
   * @return the String representation of the JSON file received from the web
   */
  public static String search(String queryText)
  {
    return search(null, queryText);
  }

  /**
   * Queries the IMDb API for a JSON text containing a list of films that match
   * the search criteria. Searches are based on title the passed queryType.
   * Acceptable queryTypes are defined in the QueryTypes enumerated class.
   * 
   * @param queryType
   *          the type of query to base the search on
   * @param queryText
   *          the text to be searched for using the IMDb API
   * @return the String representation of the JSON file received from the web
   */
  public static String search(QueryTypes queryType, String queryText)
  {
    // formatted url based on associated queryType and queryText TODO
    String urlString = String
        .format("https://imdb-api.com/en/API/Search/k_mcx0w8kk/%s", queryText);
    HttpURLConnection connection = null;
    try
    {
      // open a new HTTP connection to get the raw data at the URL
      URL url = new URL(urlString);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type",
          "application/json; charset=utf-8");
      connection.connect();
      int responseCode = connection.getResponseCode();

      // if the request succeeded
      if (responseCode == 200 || responseCode == 201)
      {
        BufferedReader reader = new BufferedReader(
            // read the input stream of the HTTP request
            new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
        {
          builder.append(line);
        }
        reader.close();
        // returns string containing JSON information as a string
        return builder.toString();
      }
    }
    catch (MalformedURLException e)
    {
      System.err.println("Improper URL passed to requestJSON");
      e.printStackTrace(System.err);
    }
    catch (IOException e)
    {
      System.err.println(
          "url.openConnection encountered an IO exception in requestJSON");
      e.printStackTrace(System.err);
    }
    finally
    {
      if (connection != null)
      {
        connection.disconnect();
      }
    }
    return null;
  }
}
