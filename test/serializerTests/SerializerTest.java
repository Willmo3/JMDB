package serializerTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.type.TypeReference;

import controller.JmdbSerializer;
import media.Movie;

/**
 * Test class for the JMDb Serializer utility class.
 * 
 * @author Matthew Potter
 * @version 12/2/2022
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SerializerTest
{
  private static String path = ".\\data\\cachedMovies.json";
  private static File testFile = new File(path);
  private static HashMap<String, Movie> testMap = new HashMap<String, Movie>();
  private static TypeReference<HashMap<String, Movie>> typeRef = new TypeReference<HashMap<String, Movie>>()
  {
  };

  @SuppressWarnings("unchecked")
  @Test
  @Order(1)
  void deserializeTest()
  {
    // cast the data which I know is a HashMap<String, Movie>
    testMap = (HashMap<String, Movie>) JmdbSerializer.deserialize(path,
        typeRef);
    assertNotNull(testMap);
    for (Movie movie : testMap.values())
    {
      System.out.println(movie.getTitle());
    }
  }
}
