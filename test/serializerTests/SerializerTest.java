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
  private static String path = "./data/SerializerTestFile.json";
  private static String savePath = "./data/SerializerSaveFile.json";
  private static String invalidPath = "./test/serializerTests/InvalidFile.json";
  private static String emptyPath = "./data/EmptyFile.json";
  private static File saveFile = new File(savePath);
  private static HashMap<String, Movie> testMap = new HashMap<String, Movie>();
  private static TypeReference<HashMap<String, Movie>> typeRef = new TypeReference<HashMap<String, Movie>>()
  {
  };

  static
  {
    // for coverage
    new JmdbSerializer();
    // clear any present saved test files in order to start from same place in
    // all test runs
    if (saveFile.exists())
    {
      saveFile.delete();
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  @Order(1)
  void deserializeTest()
  {
    assertNull(JmdbSerializer.deserialize(invalidPath, typeRef));
    assertNull(JmdbSerializer.deserialize(emptyPath, typeRef));
    // cast the data which I know is a HashMap<String, Movie>
    testMap = (HashMap<String, Movie>) JmdbSerializer.deserialize(path,
        typeRef);
    assertNotNull(testMap);
    for (Movie movie : testMap.values())
    {
      System.out.println(movie.getTitle());
    }
  }

  @Test
  @Order(2)
  void serializeTest()
  {
    assertFalse(JmdbSerializer.serialize(invalidPath, testMap));
    assertTrue(JmdbSerializer.serialize(savePath, testMap));
    assertTrue(JmdbSerializer.serialize(savePath, testMap));
  }

  @SuppressWarnings("unchecked")
  @Test
  @Order(3)
  void deserializeTest2()
  {
    // cast the data which I know is a HashMap<String, Movie>
    testMap = (HashMap<String, Movie>) JmdbSerializer.deserialize(savePath,
        typeRef);
    assertNotNull(testMap);
    for (Movie movie : testMap.values())
    {
      System.out.println(movie.getTitle());
    }
  }
}
