package listmodel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import media.Movie;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Represents
 *
 * @author William Morris, Matthew Potter
 * @version 12/1/2022
 */
public abstract class MovieMapModel
{
  protected HashMap<String, Movie> list;
  private final String contentPath;

  /**
   * Default ListModel constructor. Sets the path to file to provided path..
   * Additionally, initializes list to be an empty HashMap.
   *
   * @param path
   *          Path of the file to load.
   */
  public MovieMapModel(String path)
  {
    this.contentPath = path;
    list = load();
  }

  /*
   * Removed to bolster coverage.
   *
   * Explicit value constructor. Sets the model to have the passed map of ID,
   * Movie pairs.
   *
   * @param list the list to initialize this model with
   *
   * public ListModel(String path, HashMap<String, Movie> list) { this.PATH =
   * path; this.list = list; }
   */

  /**
   * Loads the provided file into a movie HashMap. If no file exists at the
   * specified path, or there are issues with file processing, a new empty list
   * is created.
   *
   * @return A list containing the information stored at the defined path.
   */
  protected HashMap<String, Movie> load()
  {
    File listFile = new File(contentPath);

    // no file exists, so make an empty watch-list
    if (!listFile.exists())
    {
      return new HashMap<>();
    }

    try
    {
      ObjectMapper mapper = new ObjectMapper();
      // file exists, so try to read it
      TypeReference<HashMap<String, Movie>> typeRef = new TypeReference<HashMap<String, Movie>>()
      {
      };
      return mapper.readValue(listFile, typeRef);
    }
    catch (IOException e)
    {
      // file reading caused an error, so create an empty list
      System.err.println("An error occurred in reading the list.\n"
          + "Creating an empty list");
      e.printStackTrace();
      return new HashMap<String, Movie>();
    }
  }

  /**
   * Saves the current list of movies as a JSON file at Path.
   */
  public void save()
  {
    File file = new File(contentPath);
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
        return;
      }
    }
    // file exists, so write to it
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      mapper.writeValue(file, list);
    }
    catch (IOException e)
    {
      System.err
          .println(String.format("List data could not be written to disk"));
      e.printStackTrace();
    }
  }

  /**
   * Getter for list's stored movies.
   *
   * @return the collection of movies that is the movies on the model's stored
   *         watch-list
   */
  public Collection<Movie> list()
  {
    return list.values();
  }

  /**
   * Getter for watchList.
   *
   * @return the hashmap that defines this storage of a watch-list
   */
  public HashMap<String, Movie> getMap()
  {
    return list;
  }
}
