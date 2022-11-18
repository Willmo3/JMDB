package watchlist;

import controller.JmdbController;
import media.Movie;

import javax.swing.*;
import java.awt.*;

/**
 * Button to add a movie to the watch list.
 *
 * @author William Morris, Matthew Potter
 * @version 11/18/2022
 */
public class AddToWatchButton extends JButton
{
  private JmdbController controller;
  /**
   * AddToWatchButton constructor.
   * Takes in a controller so that items may be added to watch list.
   *
   * @param controller Controller to use.
   */
  public AddToWatchButton(JmdbController controller)
  {
    this.controller = controller;
    setText("Add to Watch List");
    setPreferredSize(new Dimension(100, 100));
  }
}
