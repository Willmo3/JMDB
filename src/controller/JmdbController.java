package controller;

import list.MovieList;
//import mainGUI.JmdbGUI;
import ui.mainGUI.JmdbGUI;

/**
 * The controller for the JMDb program. Interfaces with the GUI and Model.
 * 
 * @author Matthew Potter
 * @version 11/04/2022
 */
public class JmdbController
{
  private JmdbGUI gui;

  /**
   * Constructor for the JmdbController.
   * 
   * @param gui
   *          the gui this controls
   */
  public JmdbController(JmdbGUI gui)
  {
    this.gui = gui;
    gui.buildFunctionality(this);
  }

  /**
   * Tells the GUI to update its movie list based on the list that is given.
   * 
   * @param list
   *          the movieList to update the search list based on
   */
  public void updateList(MovieList list)
  {
    gui.updateList(list.generateJList());
  }
}
