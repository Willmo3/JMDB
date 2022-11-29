package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import list.MovieList;
import mainGUI.JmdbGUI;
import media.Movie;
import search.JsonRequestor;
import watchlist.WatchListModel;

/**
 * The controller for the JMDb program. Interfaces with the GUI and Model.
 * 
 * @author Matthew Potter
 * @version 11/28/2022
 */
public class JmdbController
{
  private JmdbGUI gui;
  private WatchListModel watchListModel;

  /**
   * Empty constructor. Made for testing non-GUI function and, as such, none of
   * the GUI related methods will work.
   */
  public JmdbController()
  {
    this.gui = null;
    this.watchListModel = new WatchListModel();
  }

  /**
   * Sets the JmdbGUI that this controller is meant to update.
   * 
   * @param gui
   *          the GUI to set the current one to be
   */
  public void setGui(JmdbGUI gui)
  {
    this.gui = gui;
  }

  /**
   * Requests data from the model for startup content.
   * 
   * @return the InputStream containing the content to be shown on startup
   */
  public MovieList startupListContent()
  {
    MovieList startupList = null;
    try
    {
      FileInputStream downloadedList;
      downloadedList = new FileInputStream("ui/mainGUI/startupList.json");
      startupList = new MovieList(downloadedList);
    }
    catch (FileNotFoundException e1)
    {
      System.err.println("Startup list not found");
    }
    catch (IOException e1)
    {
      System.err.println("MovieList couldn't be made from startupList");
    }
    return startupList;
  }

  /**
   * Tells the GUI to update its movie list based on the list that is given.
   * 
   * @param list
   *          the movieList to update the search list based on
   */
  public void updateSearchList(MovieList list)
  {
    gui.updateSearchList(list.generateJList());
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
   * @return true if the movie was added, false if not
   */
  public boolean addToWatchList(Movie selectedMovie)
  {
    return watchListModel.add(selectedMovie);
  }
}
