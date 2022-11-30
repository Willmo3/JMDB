package storedMoviesTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import media.Movie;
import storedcache.StoredMovies;

/**
 * Test class for the StoredMovies cache. Deletes any existing cache files for
 * the test.
 * 
 * @author Matthew Potter
 * @version 11/30/2022
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoredMoviesTest
{
  /**
   * A file to test loading and saving WatchListModels.
   */
  static File testFile = new File(StoredMovies.CACHE_FILE_PATH);

  /**
   * The StoredMovies cache to test.
   */
  static StoredMovies cache;

  /**
   * The test HashMap that stores expected data.
   */
  static HashMap<String, Movie> testMap = new HashMap<String, Movie>();

  // set up the testMap
  static
  {
    Movie silly = new Movie("12345", "Test", "Silly Movie", "2002", 1.0,
        "trailer-link", "silly award", "wiki-link");
    Movie inception = new Movie("tt1375666",
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnX"
            + "kFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)", 8.8,
        "https://www.youtube.com/watch?v=Jvurpf91omw",
        "Academy Awards, USA, 2011, Winner, Oscar",
        "https://en.wikipedia.org/wiki/Inception");
    Movie cobol = new Movie("tt1790736",
        "https://m.media-amazon.com/images/M/MV5BMjE0NGIwM2EtZjQxZi00ZTE5LWEx"
            + "N2MtNDBlMjY1ZmZkYjU3XkEyXkFqcGdeQXVyNjMwNzk3Mjk"
            + "@._V1_Ratio0.6800_AL_.jpg",
        "Inception: The Cobol Job", "(2010 Video)", 7.5, "no link",
        "This film did not win any awards", "no link");

    testMap.put(silly.getId(), silly);
    testMap.put(inception.getId(), inception);
    testMap.put(cobol.getId(), cobol);
  }

  static
  {
    // clear any present watch-list in order to start from same place in all
    // test runs
    if (testFile.exists())
    {
      testFile.delete();
    }
    cache = new StoredMovies();
  }

  @Test
  @Order(1)
  void testAdd()
  {
    // silly movie
    assertTrue(cache.add(testMap.get("12345")));
    // inception
    assertTrue(cache.add(testMap.get("tt1375666")));
    // wrong movie
    assertTrue(cache.add(testMap.get("tt1790736")));
    assertFalse(cache.add(testMap.get("tt1790736")));

    // assert model has all added movies
    assertTrue(cache.cache().containsAll(testMap.values()));
  }

  @Test
  @Order(2)
  void testRemove()
  {
    assertTrue(cache.remove(testMap.get("12345")));
    assertFalse(cache.remove(testMap.get("12345")));
    testMap.remove("12345");

    // assert model has removed silly movie (key 12345)
    assertTrue(cache.cache().containsAll(testMap.values()));
    assertTrue(cache.cache().size() == testMap.values().size());
  }

  @Test
  @Order(3)
  void testSave()
  {
    cache.save();
    assertTrue(testFile.exists());
  }

  @Test
  @Order(4)
  void testLoad()
  {
    // load in the watch list that was just saved
    StoredMovies loaded = new StoredMovies();

    for (Movie mov : loaded.cache())
    {
      System.out.println(mov.getTitle());
    }
    assertTrue(loaded.cache().size() == testMap.values().size());
    assertTrue(loaded.cache().containsAll(testMap.values()));
  }
  
  @Test
  @Order(5)
  void testCopyConstructor()
  {
    StoredMovies other = new StoredMovies(cache.getMovieCache());
    assertTrue(other.getMovieCache().equals(cache.getMovieCache()));
  }

  @Test
  @Order(6)
  void testClear()
  {
    cache.clear();
    assertTrue(cache.cache().isEmpty());
  }
}
