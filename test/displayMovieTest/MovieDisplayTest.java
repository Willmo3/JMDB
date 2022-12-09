package displayMovieTest;

import java.io.IOException;
import javax.swing.JFrame;

import media.Movie;
import media.ResultTypes;
import mediaDisplay.BaseMovieInfo;

/**
 * Test class for MovieDisplay.
 */
public class MovieDisplayTest extends JFrame
{
  /**
   * Generated serial UID.
   */
  private static final long serialVersionUID = 3702168543757074945L;

  /**
   * Tests the MovieDisplay by validating it.
   * 
   * @throws IOException
   *           if one occurs
   */
  public MovieDisplayTest() throws IOException
  {
    this.setVisible(true);
    this.setTitle("Movie Cover");
    this.setSize(400, 400);
    this.setContentPane(new BaseMovieInfo(new Movie("tt1375666",
        ResultTypes.TITLE,
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)")));
    this.revalidate();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Displays the test MovieDisplay for visual testing.
   * 
   * @param args
   *          arguments
   */
  public static void main(String[] args)
  {
    try
    {
      new MovieDisplayTest();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }

}
