package controller;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for serializing and deserializing JMDb data from/into JSON
 * files using Jackson.
 * 
 * @author Matthew Potter
 * @version 12/02/2022
 */
public final class JmdbSerializer
{
  /**
   * Defines the path of the parent file where each JSON file should be located.
   * Done so to restrict the possibility of reading in data from locations
   * outside of the working directory's defined data file.
   */
  public static final String DATA_FILE_PATH = ".\\data";

  /**
   * Reads the JSON file located at the passed path using Jackson and
   * instantiates the object as one of type serialClass.
   * 
   * @param path
   *          the directory path to the file to deserialize
   * @param typeRef
   *          a Jackson TypeReference of the class that should be instantiated
   *          by deserializing the object
   * @return the instantiated Object, or null if none could be created
   */
  public static Object deserialize(String path, TypeReference<?> typeRef)
  {
    File file = new File(path);
    // attempting to read data from outside the designated data directory or no
    // data file exists, so print error and return null
    if (!file.exists() || !file.getParent().equals(DATA_FILE_PATH))
    {
      System.err.println("Attempting to deserialize a file from outside the "
          + "designated JMDb data directory, or no file at all");
      return null;
    }

    try
    {
      ObjectMapper mapper = new ObjectMapper();
      // file exists, so try to read it
      return mapper.readValue(file, typeRef);
    }
    catch (IOException e)
    {
      // file reading caused an error, so create an empty list
      System.err.println(String.format(
          "An error occurred in deserializing the object at path\n%s\n", path));
      e.printStackTrace();
      return null;
    }
  }
}
