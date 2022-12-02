package listTest;

import listmodel.WatchListModel;
import media.Movie;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for WatchList.
 *
 * @author William Morris
 * @version 12/1/2022
 */
public class WatchListTest
{

  private static final String TEST_PATH = "./data/watchlisttest.json";

  @Test
  void testListLoadsExplicitPath()
  {
    WatchListModel model = new WatchListModel(TEST_PATH);
    HashMap<String, Movie> movies = model.getMap();

    assertEquals(3, movies.size());
    assertNotNull(movies.get("tt1375666"));
    assertNotNull(movies.get("tt0898266"));
    assertNotNull(movies.get("tt1790736"));
    assertNull(movies.get("fail"));
  }

  @Test
  void testListLoadsDefaultPath()
  {
    new WatchListModel();
  }

  @Test
  void testListLoadsInvalidPath()
  {
    WatchListModel model = new WatchListModel("Invalid");
    assertEquals(0, model.getMap().size());
  }

  // Save data and delete data tests do not use the standard test file.
  // This is due to the intrusive nature of the tests.
  @Test
  void testSavesData() throws IOException
  {
    WatchListModel model = new WatchListModel("./data/watchlistadd.json");
    Movie sentiMovie = new Movie("Sentinel", "Sentinel", "Sentinel", "Sentinel",
        8, "Sentinel", "Sentinel", "800");

    model.add(sentiMovie);
    model.save();

    FileInputStream file = new FileInputStream("./data/watchlistadd.json");
    Scanner input = new Scanner(file);
    assertEquals("{\"Sentinel\":{\"id\":\"Sentinel\",\"imageLink\""
        + ":\"Sentinel\",\"title\":\"Sentinel\",\"description\":\"Sentinel\",\""
        + "imdbRating\":8.0,\"trailerLink\":\"Sentinel\",\"award\":\"Sentinel\",\"wiki\":\"800\"}}",
        input.next());
    input.close();
    file.close();

    model.remove(sentiMovie);
    model.save();

    FileInputStream emptyFile = new FileInputStream("./data/watchlistadd.json");
    Scanner emptyScanner = new Scanner(emptyFile);
    assertEquals("{}", emptyScanner.next());
  }

  // Also sees that the file will save if a path without an existing file is
  // specified.
  @Test
  void testSavesDataAfterDeletion()
  {
    String path = "./data/watchlistdelete.json";

    WatchListModel model = new WatchListModel(path);
    model.save();

    File watchList = new File(path);
    assertTrue(watchList.exists());

    watchList.delete();
    assertFalse(watchList.exists());

    model.save();
    File newWatchList = new File(path);
    assertTrue(newWatchList.exists());
  }

  @Test
  void testDoesNotAllowDuplicateMovies()
  {
    WatchListModel model = new WatchListModel(TEST_PATH);
    Movie sentiMovie = new Movie("Sentinel2", "Sentinel", "Sentinel",
        "Sentinel", 8, "Sentinel", "Sentinel", "800");

    assertTrue(model.add(sentiMovie));
    assertFalse(model.add(sentiMovie));
    assertTrue(model.remove(sentiMovie));
  }

  @Test
  void testDontDeleteNonExistentMovies()
  {
    WatchListModel model = new WatchListModel(TEST_PATH);
    assertFalse(model.remove(new Movie("InvalidMovie", "Sentinel", "Sentinel",
        "Sentinel", 8, "Sentinel", "Sentinel", "800")));
  }

  @Test
  void testClearTestWatchlist()
  {
    WatchListModel model = new WatchListModel(TEST_PATH);
    model.clear();
    assertEquals(0, model.getMap().size());
  }

  @Test
  void testWatchListValues()
  {
    WatchListModel model = new WatchListModel(TEST_PATH);
    Collection<Movie> movies = model.list();

    assertEquals(3, movies.size());
  }
}
