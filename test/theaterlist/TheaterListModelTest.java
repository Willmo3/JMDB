package theaterlist;

import controller.JmdbController;
import media.Movie;
import model.TheaterListModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests that the TheaterListModel successfully loads movies.
 */
public class TheaterListModelTest {

  /**
   * Tests loading when there is no in theater list.
   * Kept for posterity.
   */
  @Test
  void testLoad() {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String dateStr = date.format(now);
    JmdbController controller = new JmdbController();

    File theaterFile = new File(String.format("./data/theaterlist%s.json", dateStr));
    TheaterListModel model = new TheaterListModel(controller);
    assertTrue(new File(String.format("data/theaterlist%s.json", dateStr)).exists());
    controller.saveData();
  }

  @Test
  void testGetList() {
    JmdbController controller = new JmdbController();
    TheaterListModel model = new TheaterListModel(controller);
    List<String> movies = model.getMovies();
    Set<String> cachedMovies = controller.getCachedMovies().keySet();
    Set<String> featuredMovies = controller.getFeaturedListModel().keySet();

    for (String m : movies) {
      assertTrue(cachedMovies.contains(m) || featuredMovies.contains(m));
    }

    controller.saveData();
  }

  @Test
  void testGetMovieList() {
    JmdbController controller = new JmdbController();
    Collection<Movie> movies = controller.getInTheatersList();
    assertFalse(movies.isEmpty());

    for (Movie m : movies) {
      System.out.println(m.getId());
    }
  }
}
