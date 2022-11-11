package movielist;

import list.MovieFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for MovieFile.
 *
 * @author William Morris
 * @version 11/1/2022
 */
public class MovieFileTest
{
  static File movieJson;

  static
  {
    // Note: results.json must be present in this directory for tests to
    // function.
    movieJson = new File("test/movielist/results.json");
  }

  @Test
  void listParsesWithoutException()
  {
    MovieFile file = new MovieFile(movieJson);
  }

  @Test
  void getFileInputStreamTest() throws FileNotFoundException
  {
    MovieFile file = new MovieFile(movieJson);
    Scanner test = new Scanner(file.getStream());

    assertEquals("{", test.next());
  }
}
