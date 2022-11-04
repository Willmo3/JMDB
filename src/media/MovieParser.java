package media;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class that creates a list of Movie objects from a JSON String
 * defining such objects.
 * 
 * @author Matthew Potter
 * @version 11/03/2022
 */
public final class MovieParser
{
  /**
   * Converts the JSON string containing film information given a search and
   * returns a List of the Movies that were included in the search.
   * 
   * @param jsonString
   *          the JSON string to be turned into a list
   * @return the list of movies that the received JSON string defined
   */
  public static List<Movie> parseMovies(String jsonString)
  {
    ObjectMapper mapper = new ObjectMapper();
    List<Movie> movieList = new ArrayList<Movie>();
    try
    {
      movieList = mapper.readValue(jsonString, new TypeReference<List<Movie>>()
      {
      });
    }
    catch (JsonProcessingException e)
    {
      System.err.println(
          "Exception occurred when mapping the JSON to a list of movies");
      e.printStackTrace();
      return new ArrayList<Movie>();
    }
    return null;
  }
}
