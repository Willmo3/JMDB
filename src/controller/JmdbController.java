package controller;

import list.MovieList;
import mainGUI.JmdbGUI;

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
   * @param gui the gui this controls
   */
  public JmdbController(JmdbGUI gui) {
    this.gui = gui;
    gui.buildFunctionality(this);
  }
  
  public void updateList(MovieList list) {
    gui.updateList(list.generateJList());
  }
}
