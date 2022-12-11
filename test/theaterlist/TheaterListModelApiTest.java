package theaterlist;

import controller.JmdbController;
import model.TheaterListModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TheaterListModel tests that involve calling the API.
 * Avoid using this except when necessary for troubleshooting!
 *
 * @author Will Morris
 * @version 12/10/2022
 */
public class TheaterListModelApiTest
{
  private static String dateStr;
  private static final String DIR = "./data/theaterlist%s.json";

  static {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    dateStr = date.format(now);
  }

  /**
   * Ensures that the TheaterList is capable of properly loading.
   */
  @Test
  void testLoadNoMovieList() {
    File theaterList = new File(String.format(DIR, dateStr));
    theaterList.delete();
    TheaterListModel model = new TheaterListModel();
    assertTrue(new File(String.format(DIR, dateStr)).exists());
  }

  @Test
  void testLoadInvalidFile() throws IOException {
    File theaterList = new File(String.format(DIR, dateStr));
    theaterList.delete();
    theaterList.createNewFile();
    JmdbController comptroller = new JmdbController();
    assertNotNull(comptroller.getInTheatersList());
  }
}
