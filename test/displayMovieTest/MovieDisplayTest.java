package displayMovieTest;

import java.io.IOException;
import javax.swing.JFrame;

import media.Movie;
import ui.mediaDisplay.MediaDisplayPanel;

/**
 * Test class for MovieDisplay.
 */
public class MovieDisplayTest extends JFrame
{
  public MovieDisplayTest() throws IOException
  {
    this.setVisible(true);
    this.setTitle("Movie Cover");
    this.setSize(400, 400);
    this.setContentPane(new MediaDisplayPanel(new Movie("tt1375666",
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
        "Inception", "(2010)")));
    this.revalidate();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
    try
    {
      MovieDisplayTest movie = new MovieDisplayTest();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
