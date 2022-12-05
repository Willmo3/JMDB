package controller;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

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
  public static final String DATA_FILE_PATH = "./data";

  /**
   * Reads the JSON file located at the passed path which must point to a file
   * located in the working directory's data directory using Jackson and
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
    String parentPath = file.getParent().replace("\\", "/");
    // attempting to read data from outside the designated data directory or no
    // data file exists, so print error and return null
    if (!file.exists() || !parentPath.equals(DATA_FILE_PATH))
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
    catch (MismatchedInputException e)
    {
      // occurs when blank file
      return null;
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

  /**
   * Serializes the passed object into a JSON file located at the passed path
   * which must point to either a file or pathname in the working directory's
   * data directory using Jackson.
   * 
   * @param path
   *          the path to the location which the object should be serialized to
   * @param serialObject
   *          the object to serialize
   * @return true if the save was successful, false if not
   */
  public static boolean serialize(String path, Object serialObject)
  {
    File file = new File(path);
    String parentPath = file.getParent().replace("\\", "/");
    if (!parentPath.equals(DATA_FILE_PATH))
    {
      System.err.println("Attempting to serialize to a file outside of the "
          + "designated JMDb data directory");
      return false;
    }
    if (!file.exists())
    {
      try
      {
        file.createNewFile();
      }
      catch (IOException e)
      {
        System.err.println("Issue occurred when creating the list file "
            + "during the save process.");
        return false;
      }
    }
    // file exists, so write to it
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      mapper.writeValue(file, serialObject);
      return true;
    }
    catch (IOException e)
    {
      System.err
          .println(String.format("List data could not be written to disk"));
      e.printStackTrace();
    }
    return false;
  }
}
