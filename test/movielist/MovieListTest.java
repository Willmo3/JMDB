package movielist;

import list.MovieList;
import media.Movie;
import media.ResultTypes;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for MovieList.
 *
 * @author Will Morris, Matthew Potter
 * @version 12/04/2022
 */
public class MovieListTest
{
  private static MovieList list;

  static
  {
    FileInputStream file = null;
    try
    {
      file = new FileInputStream("test/movielist/results.json");
      list = new MovieList(file);
    }
    catch (FileNotFoundException e)
    {
      System.err.println("File not found in MovieListTest");
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      try
      {
        file.close();
      }
      catch (IOException e)
      {
        System.err.println("Input stream couldn't be closed in MovieListTest");
      }
    }
  }

  @Test
  void testListAccurate() throws IOException
  {
    List<Movie> movieList = list.getMovieList();
    List<Movie> expected = new ArrayList<Movie>();

    expected.add(new Movie("tt1375666", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkF"
            + "tZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)"));
    expected.add(new Movie("tt1790736", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjE0NGIwM2EtZjQxZi00ZTE5LWEx"
            + "N2MtNDBlMjY1ZmZkYjU3XkEyXkFqcGdeQXVyNjMwNzk3M"
            + "jk@._V1_Ratio0.6800_AL_.jpg",
        "Inception: The Cobol Job", "(2010 Video)"));
    expected.add(new Movie("tt5295990", ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BZGFjOTRiYjgtYjEzMS00ZjQ2LTkz"
            + "Y2YtOGQ0NDI2NTVjOGFmXkEyXkFqcGdeQXVyNDQ5MDYzM"
            + "Tk@._V1_Ratio0.6800_AL_.jpg",
        "Inception: Jump Right Into the Action", "(2010 Video)"));
    expected.add(new Movie("tt1686778", ResultTypes.TITLE,
        "https://imdb-api.com/images/original/nopicture.jpg",
        "Inception: 4Movie Premiere Special", "(2010 TV Movie)"));
    expected.add(new Movie("tt12960252", ResultTypes.TITLE,
        "https://imdb-api.com/images/original/nopicture.jpg",
        "Inception Premiere", "(2010)"));

    assertEquals(expected.size(), movieList.size());
    for (int i = 0; i < expected.size(); i++)
    {
      assertEquals(expected.get(i), movieList.get(i));
    }
  }

  @Test
  void searchByMovieID()
  {
    Movie beThere = new Movie("tt12960252", ResultTypes.TITLE,
        "https://imdb-api.com/images/original/nopicture.jpg",
        "Inception Premiere", "(2010)");
    assertEquals(beThere, list.queryMovie(beThere.getId()));
    assertNull(list.queryMovie("Nill!"));
    assertNull(list.queryMovie((String) null));
  }

  @Test
  void searchByMovie()
  {
    Movie beThere = new Movie("tt12960252", ResultTypes.TITLE,
        "https://imdb-api.com/images/original/nopicture.jpg",
        "Inception Premiere", "(2010)");
    assertEquals(beThere, list.queryMovie(beThere));

    Movie notThere = new Movie("tt1er2960252", ResultTypes.TITLE,
        "https://imdb-api.com/images/original/nopicture.jpg",
        "Inception Premiere", "(2010)");
    assertNull(list.queryMovie(notThere));
    assertNull(list.queryMovie((Movie) null));
  }
}
