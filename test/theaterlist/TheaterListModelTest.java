package theaterlist;

import model.TheaterListModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests that the TheaterListModel successfully loads movies.
 */
public class TheaterListModelTest {
  /**
   * Tests loading when there is no in theater list.
   */
  @Test
  void testLoadNoFileExists() {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String dateStr = date.format(now);

    // Ensure there is no file to draw from.
    File theaterFile = new File(String.format("./data/theaterlist%s.json", dateStr));
    theaterFile.delete();
    TheaterListModel model = new TheaterListModel();

    assertTrue(new File("data/theaterlist%s.json").exists());
  }
}
