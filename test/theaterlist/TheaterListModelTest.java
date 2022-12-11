package theaterlist;

import media.Movie;
import model.TheaterListModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that the TheaterListModel successfully loads movies.
 */
public class TheaterListModelTest {

  private static String dateStr;
  private static final String DIR = "./data/theaterlist%s.json";

  static {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    dateStr = date.format(now);
  }
  /**
   * Tests loading when there is no in theater list.
   */
  @Test
  void testLoad() {
    TheaterListModel model = new TheaterListModel();
    assertTrue(new File(String.format(DIR, dateStr)).exists());
  }

  @Test
  void testGetList() {
    TheaterListModel model = new TheaterListModel();
    Collection<Movie> movies = model.getMovies();
    assertFalse(movies.isEmpty());

    for (Movie m : movies) {
      assertNotNull(m);
    }
  }

  @Test
  void testCleanDirectory() throws IOException {
    File badFile = new File(String.format(DIR, "2022-12-09"));
    File goodFile = new File(String.format(DIR, dateStr));
    badFile.createNewFile();
    TheaterListModel model = new TheaterListModel();
    assertFalse(badFile.exists());
    assertTrue(goodFile.exists());
  }
}
