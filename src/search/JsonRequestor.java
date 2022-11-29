package search;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * @author Matthew Potter, Will Morris
 * @version 11/11/2022
 */
public final class JsonRequestor
{
  /**
   * An enum of all the acceptable Query Types that can be made in a search.
   * 
   * @author Matthew Potter
   * @version 11/10/2022
   */
  public static enum QueryTypes
  {
    /**
     * The query for searching by title.
     */
    TITLE("Title"),
    /**
     * The query for searching by movie.
     */
    MOVIE("Movie"),
    /**
     * The query for searching by TV series.
     */
    SERIES("Series"),
    /**
     * The query for searching by the name of all people on IMDb.
     */
    NAME("Name"),
    /**
     * The query for searching into all criteria.
     */
    ALL("All");

    private final String typeText;

    QueryTypes(final String type)
    {
      this.typeText = type;
    }

    @Override
    public String toString()
    {
      return typeText;
    }
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
    // formatted url based on associated queryType and queryText
    String url = String.format(
        "https://imdb-api.com/en/API/Search%s/k_mcx0w8kk/%s", queryType,
        queryText);
    try
    {
      InputStream stream = fetch(url);
      MovieList list = new MovieList(stream);
      stream.close();
      return list;
    }
    catch (IOException e)
    {
      System.err.println(String.format(
          "Error occurred in creating the MovieList during search for %s",
          queryText));
    }
    return null;
  }

  /**
   * Queries the IMDb web API in order to get the rating of a film.
   * 
   * @param id
   *          the movie ID to get the rating of
   * @return the IMDb rating of the passed movie
   */
  public static double queryRating(String id)
  {
    String url = String
        .format("https://imdb-api.com/en/API/Ratings/k_mcx0w8kk/%s", id);
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      InputStream stream = fetch(url);
      JsonNode ratings = mapper.readTree(stream);
      stream.close();
      return ratings.get("imDb").asDouble();
    }
    catch (IOException e)
    {
      System.err.println(String.format(
          "Error occurred in reading stream in rating query for ID: %s\n", id));
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Queries the IMDb web API in order to get the trailer of a film.
   *
   * @param id
   *          the movie ID to get the rating of
   * @return the IMDb rating of the passed movie
   */
  public static String queryTrailer(String id)
  {
    String url = String
        .format("https://imdb-api.com/en/API/YouTubeTrailer/k_mcx0w8kk/%s", id);
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      InputStream stream = fetch(url);
      JsonNode ratings = mapper.readTree(stream);
      stream.close();
      return ratings.get("videoUrl").asText();
    }
    catch (IOException e)
    {
      System.err.println(String.format(
          "Error occurred in reading stream in rating query for ID: %s\n", id));
    }
    return null;
  }

  /**
   * Queries the IMDb web API in order to get the Awards of a film.
   *
   * @param id
   *          the movie ID to get the Awards of
   * @return the IMDb rating of the passed movie
   */
  public static String queryAwards(String id)
  {
    String url = String
        .format("https://imdb-api.com/en/API/Awards/k_mcx0w8kk/%s", id);
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      InputStream stream = fetch(url);
      JsonNode awards = mapper.readTree(stream);
      stream.close();

      if (awards.get("items").isEmpty())
      {
        return "This film did not win any awards";
      }
      // set up for reading in the first award at the first event
      String event, year, outcome, category, result;
      JsonNode firstEvent = awards.get("items").get(0);
      // the first award at that first event's information
      JsonNode firstAward = awards.get("items").get(0).get("outcomeItems")
          .get(0);

      // fill in the award information
      event = firstEvent.get("eventTitle").asText();
      year = firstEvent.get("eventYear").asText();
      outcome = firstAward.get("outcomeTitle").asText();
      category = firstAward.get("outcomeCategory").asText();

      result = String.format("%s, %s, %s %s", event, year, category, outcome);
      return result;
    }
    catch (IOException e)
    {
      System.err.println(String.format(
          "Error occurred in reading stream in Award query for ID: %s\n", id));
    }
    return null;
  }
  
  /**
   * Queries the IMDb web API in order to get the Wikipedia page of a film.
   *
   * @param id
   *          the movie ID to get the rating of
   * @return the IMDb Wikipedia page of the passed movie
   */
  public static String queryWiki(String id)
  {
    String url = String
        .format("https://imdb-api.com/en/API/Wikipedia/k_mcx0w8kk/%s", id);
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      InputStream stream = fetch(url);
      JsonNode wiki = mapper.readTree(stream);
      stream.close();
      return wiki.get("url").asText();
    }
    catch (IOException e)
    {
      System.err.println(String.format(
          "Error occurred in reading stream in wiki query for ID: %s\n", id));
    }
    return null;
  }


  /**
   * Connects to the passed URL through an HttpURLConnection, expecting a
   * raw-data JSON file to be present at the location in order to duplicate its
   * InputStream to be parsed for information.
   * 
   * @param url
   *          a String containing the URL web address that itself contains a
   *          raw-data JSON file to connect to
   * @return an InputStream containing the pulled JSON information
   */
  public static InputStream fetch(String url)
  {
    URL webURL;
    try
    {
      webURL = new URL(url);
      return fetch(webURL);
    }
    catch (MalformedURLException e)
    {
      System.err.println(String.format(
          "Error occurred in creating the URL to fetch for data at URL:\n%s\n",
          url));
      e.printStackTrace(System.err);
    }
    return null;
  }

  /**
   * Connects to the passed URL through an HttpURLConnection, expecting a
   * raw-data JSON file to be present at the location in order to duplicate its
   * InputStream to be parsed for information.
   * 
   * @param url
   *          the url containing a raw-data JSON file to connect to
   * @return an InputStream containing the pulled JSON information
   */
  public static InputStream fetch(URL url)
  {
    HttpURLConnection connection = null;
    try
    {
      // open a new HTTP connection to get the raw data at the URL
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type",
          "application/json; charset=utf-8");
      connection.connect();
      int responseCode = connection.getResponseCode();
      // if the request succeeded
      if (responseCode == 200 || responseCode == 201)
      {
        InputStream content = new ByteArrayInputStream(
            connection.getInputStream().readAllBytes());
        connection.disconnect();
        return content;
      }
    }
    catch (IOException e)
    {
      System.err.println("Error has occurred in fetch:\n"
          + "\tEither the connection encountered an I/O error during connection"
          + "\n\t or during the reading of the InputStream");
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
