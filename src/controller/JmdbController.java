package controller;

import java.util.Collection;

import list.MovieList;
import mainGUI.JmdbGUI;
import media.Movie;
import watchlist.WatchListModel;

/**
 * The controller for the JMDb program. Interfaces with the GUI and Model.
 * 
 * @author Matthew Potter
 * @version 11/17/2022
 */
public class JmdbController
{
  private JmdbGUI gui;
  private WatchListModel watchListModel;

  /**
   * Empty constructor. Made for testing non-GUI function and so none of the GUI
   * related methods will work.
   */
  public JmdbController()
  {
    this.gui = null;
    this.watchListModel = new WatchListModel();
  }

  /**
   * Constructor for the JmdbController.
   * 
   * @param gui
   *          the gui this controls
   */
  public JmdbController(JmdbGUI gui)
  {
    this.gui = gui;
    this.watchListModel = new WatchListModel();
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

  /**
   * Gets the watchList.
   * 
   * @return the watchList
   */
  public Collection<Movie> getWatchList()
  {
    return watchListModel.watchList();
  }

  /**
   * Saves the watch list. Should be called when the watch list's GUI element is
   * exited on close of the whole program.
   */
  public void saveWatchList()
  {
    watchListModel.save();
  }

  /**
   * Adds the passed movie to the watch-list.
   * 
   * @param selectedMovie
   *          the movie to add to watch-list
   */
  public boolean addToWatchList(Movie selectedMovie)
  {
    return watchListModel.add(selectedMovie);
  }
}
