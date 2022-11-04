package search;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import list.MovieList;

/**
 * Utility class for querying the Internet in order to receive JSON files that
 * include information about searches, ratings, and other aspects of media
 * present on IMDb, all as queried through the IMDb API.
 * 
 * @author Matthew Potter
 * @version 11/03/2022
 */
public final class JsonRequestor
{
  /**
   * An enum of all the acceptable Query Types that can be made in a search.
   * 
   * @author Matthew Potter
   * @version 11/03/2022
   */
  public static enum QueryTypes {
    TITLE, MOVIE, SERIES, NAME, ALL
  }
  
  /**
   * Queries the IMDb API for a JSON text containing a list of films that match
   * the search criteria. Searches are based on title.
   * 
   * @param queryText
   *          the text to be searched for using the IMDb API
   * @return a MovieList containing the movies defined by the received JSON
   */
  public static MovieList search(String queryText)
  {
    return search(QueryTypes.TITLE, queryText);
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
   * @return a MovieList containing the movies defined by the received JSON
   */
  public static MovieList search(QueryTypes queryType, String queryText)
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
        MovieList list = new MovieList(connection.getInputStream());
        connection.disconnect();
        return list;
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
  
  /**
   * Queries the IMDb web API in order to get the rating of a film
   * 
   * @param id the movie ID to get the rating of
   * @return the IMDb rating of the passed movie
   */
  public static double queryRating(String id) {
    String urlString = String
        .format("https://imdb-api.com/en/API/Ratings/k_mcx0w8kk/%s", id);
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
        ObjectMapper mapper = new ObjectMapper();
        JsonNode ratings = mapper.readTree(connection.getInputStream());
        return ratings.get("imDb").asDouble();
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
    
    return Double.NEGATIVE_INFINITY;
  }
}
