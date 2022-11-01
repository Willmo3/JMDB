package list;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Represents a file containing a bunch of movies.
 * In our current implementation, this will be a JSON file.
 * Primary purpose of this class is to supply an InputStream to MovieList.
 *
 * @author William Morris
 * @version 11/1/2022
 */
public class MovieFile {

  private File movieFile;

  public MovieFile(File movieFile) {
    this.movieFile = movieFile;
  }

  /**
   * Gets a FileInputStream of the movie file.
   * Note that the controller would be expected to generate a MovieFile,
   * Then use this method to get a stream to provide to MovieList.
   * The point of this separation from MovieList is to reduce coupling, since MovieList never ends up actually
   * Interacting with the JSON file -- only the stream this generates.
   *
   * @throws FileNotFoundException if the file cannot be found
   * @return The FileInputStream.
   */
  public FileInputStream getStream() throws FileNotFoundException {
    return new FileInputStream(movieFile);
  }
}
