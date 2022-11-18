package watchListTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import media.Movie;
import watchlist.WatchListModel;

/**
 * Test class for the WatchListModel. Deletes any existing watch-list for the
 * test.
 * 
 * @author Matthew Potter
 * @version 11/17/2022
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WatchListTest
{
  /**
   * A file to test loading and saving WatchListModels.
   */
  static File testFile = new File(WatchListModel.WATCH_LIST_PATH);

  /**
   * The WatchListModel to test.
   */
  static WatchListModel model;

  /**
   * The test HashMap that stores expected data.
   */
  static HashMap<String, Movie> testMap = new HashMap<String, Movie>();

  // set up the testMap
  static
  {
    Movie silly = new Movie("12345", "Test", "Silly Movie", "2002");
    Movie inception = new Movie("tt1375666",
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnX"
            + "kFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)");
    Movie cobol = new Movie("tt1790736",
        "https://m.media-amazon.com/images/M/MV5BMjE0NGIwM2EtZjQxZi00ZTE5LWEx"
            + "N2MtNDBlMjY1ZmZkYjU3XkEyXkFqcGdeQXVyNjMwNzk3Mjk"
            + "@._V1_Ratio0.6800_AL_.jpg",
        "Inception: The Cobol Job", "(2010 Video)");

    // calling their internet fetch methods here so that save won't need to call
    // them.
    inception.getImdbRating();
    inception.getTrailerLink();
    inception.getAward();
    cobol.getImdbRating();
    cobol.getTrailerLink();
    cobol.getAward();

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
    model = new WatchListModel();
  }

  @Test
  @Order(1)
  void testAdd()
  {
    // silly movie
    assertTrue(model.add(testMap.get("12345")));
    // inception
    assertTrue(model.add(testMap.get("tt1375666")));
    // wrong movie
    assertTrue(model.add(testMap.get("tt1790736")));
    assertFalse(model.add(testMap.get("tt1790736")));

    // assert model has all added movies
    assertTrue(model.watchList().containsAll(testMap.values()));
  }

  @Test
  @Order(2)
  void testRemove()
  {
    assertTrue(model.remove(testMap.get("12345")));
    assertFalse(model.remove(testMap.get("12345")));
    testMap.remove("12345");

    // assert model has removed silly movie (key 12345)
    assertTrue(model.watchList().containsAll(testMap.values()));
    assertTrue(model.watchList().size() == testMap.values().size());
  }

  @Test
  @Order(3)
  void testSave()
  {
    model.save();
    assertTrue(testFile.exists());
  }

  @Test
  @Order(4)
  void testLoad()
  {
    // load in the watch list that was just saved
    WatchListModel loaded = new WatchListModel();

    for (Movie mov : loaded.watchList())
    {
      System.out.println(mov.getTitle());
    }
    assertTrue(loaded.watchList().size() == testMap.values().size());
    assertTrue(loaded.watchList().containsAll(testMap.values()));
  }

   @Test
   @Order(5)
   void testClear()
   {
   model.clear();
   assertTrue(model.watchList().isEmpty());
   }
}
