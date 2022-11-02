package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * An object which queries the Internet for IMDb information using HTTP requests
 * to the IMDb API
 * 
 * @author Matthew Potter
 * @version 11/2/2022
 */
public class JSONRequestor
{
  
  private String stringJSON;
  
  /**
   * Initializes the values to null
   */
  public JSONRequestor()
  {
    this(null);
  }
  
  public JSONRequestor(String queryText) {
    stringJSON = requestJSON(null, queryText);
  }

  protected List<ArrayNode> getSearchedList(String queryType, String queryText)
  {
    String textJSON = requestJSON(queryType, queryText);
    return null;
  }

  private String requestJSON(String queryType, String queryText)
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
  
  public String getStringJSON()
  {
    return stringJSON;
  }
}
