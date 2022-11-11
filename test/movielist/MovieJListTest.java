package movielist;

import list.MovieFile;
import list.MovieList;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Test that the MovieJList can display properly.
 *
 * @author William Morris
 * @version 11/4/2022
 */
public class MovieJListTest
{

  public static void main(String[] args)
  {
    MovieFile file = new MovieFile(new File("test/movielist/results.json"));
    MovieList list = null;

    try
    {
      list = new MovieList(file.getStream());
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }

    JFrame frame = new JFrame();
    frame.add(list.generateJList());
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
