package search;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class JSONRequestor
{
  protected List<ArrayNode> requestJSON(String queryType, String queryText)
  {
    String urlString = "http://example.com";
    HttpURLConnection connection = null;
    try
    {
      URL url = new URL(urlString);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
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
