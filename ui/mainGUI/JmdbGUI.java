package mainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.JmdbController;
import media.Movie;
import mediaDisplay.MediaDisplayPanel;
import menubar.JmdbMenuBar;
import searchbar.Searchbar;

/**
 * Main GUI for the JMDb program.
 *
 * @author Sean Talbot, Matthew Potter, Immanuel Semelfort, William Morris
 * @version 12/03/2022
 */
public class JmdbGUI extends JFrame
{
  private static final long serialVersionUID = 6407031954885012174L;

  private JmdbController controller;
  private JPanel contentPane;
  // top-side content
  private JPanel upperButtons;
  private Searchbar searchbar;
  private JPanel selectedMoviePanel;
  private Movie selectedMovie;
  private JButton watchListButton;
  // list-associated objects
  private JPanel listPanel;
  private JLabel listLabel;
  private JList<Movie> moviesJlist;
  private ListViews currentListView;
  private DefaultListModel<Movie> searchListModel;
  private DefaultListModel<Movie> watchListModel;
  private DefaultListModel<Movie> featuredListModel;
  // private DefaultListModel<Movie> popularListModel;

  /**
   * An enumerated class of all the possible views that the List of Movies may
   * have.
   * 
   * @author Matthew Potter
   * @version 12/03/2022
   */
  public static enum ListViews
  {
    /**
     * The default view shown when doing any search.
     */
    SEARCH("Search"),

    /**
     * The view showing the user-made watch-list of Movies.
     */
    WATCHLIST("Plan To Watch List"),

    /**
     * The view shown when using the featured movies list.
     */
    FEATURED("Featured Movies");

    private final String text;

    ListViews(final String viewText)
    {
      this.text = viewText;
    }

    /**
     * Getter for the text of the list view enum.
     * 
     * @return the textual label associated with the list view
     */
    public String getText()
    {
      return text;
    }
  }

  /**
   * The selection listener that updates the display of the film.
   * 
   * @author Matthew Potter
   * @version 12/03/2022
   */
  private class DisplaySelectionListener implements ListSelectionListener
  {
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
      selectedMovie = moviesJlist.getSelectedValue();
      remove(selectedMoviePanel);
      selectedMoviePanel = new MediaDisplayPanel(selectedMovie);
      add(selectedMoviePanel, BorderLayout.CENTER);
      repaint();
      pack();
      if (selectedMovie != null && currentListView != ListViews.WATCHLIST)
      {
        controller.addToCache(selectedMovie);
      }
    }
  }

  /**
   * Listener for the watch-list button. If the watch-list is not the current
   * view the button adds the selected movie to the watch-list, else it removed
   * the selected movie from the watch-list.
   * 
   * @author Matthew Potter
   * @version 12/03/2022
   */
  private class WatchListButtonListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (selectedMovie == null)
      {
        return;
      }
      // if the current view is the watchlist, the selected movie should be
      // removed if possible.
      if (currentListView == ListViews.WATCHLIST)
      {
        if (controller.removeFromWatchList(selectedMovie))
        {
          watchListModel.removeElement(selectedMovie);
        }
      }
      // if the current view isn't the watchlist, the selected movie should be
      // added to the watchlist if possible.
      else
      {
        if (controller.addToWatchList(selectedMovie))
        {
          watchListModel.addElement(selectedMovie);
        }
      }
    }
  }

  /**
   * Instantiate the GUI, building all necessary components and making it
   * visible.
   * 
   * @param controller
   *          the controller this GUI sends messages to
   */
  public JmdbGUI(JmdbController controller)
  {
    // set the controller
    this.controller = controller;
    controller.setGui(this);

    // generic content stuff
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1280, 720);
    setMinimumSize(new Dimension(1280, 720));
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout(0, 0));

    // movie list stuff
    listPanel = new JPanel();
    moviesJlist = new JList<Movie>();
    moviesJlist.addListSelectionListener(new DisplaySelectionListener());
    JScrollPane listScrollPane = new JScrollPane();
    listScrollPane.setViewportView(moviesJlist);
    listLabel = new JLabel();
    listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
    listPanel.add(listLabel);
    listPanel.add(listScrollPane);
    add(listPanel, BorderLayout.WEST);

    // menu bar stuff
    JmdbMenuBar menu = new JmdbMenuBar(this);
    setJMenuBar(menu);

    buildModels();
    buildStartupDisplay();
    buildButtons();

    // make the JMDb application save data on exit
    addWindowListener(new java.awt.event.WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        super.windowClosing(e);
        controller.saveData();
      }
    });

    this.setVisible(true);
  }

  private void buildModels()
  {
    searchListModel = new DefaultListModel<Movie>();
    featuredListModel = new DefaultListModel<Movie>();
    featuredListModel.addAll(controller.startupListContent());
    watchListModel = new DefaultListModel<Movie>();
    watchListModel.addAll(controller.getWatchList());
  }

  /**
   * Builds the relevant pieces for displaying the startup content.
   */
  public void buildStartupDisplay()
  {
    selectedMoviePanel = new JPanel();
    // set the startup model to be displayed on the JList
    currentListView = ListViews.FEATURED;
    listLabel.setText(currentListView.getText());
    moviesJlist.setModel(featuredListModel);
    repaint();
    moviesJlist.getSelectionModel()
        .addListSelectionListener(new DisplaySelectionListener());
  }

  /**
   * Creates any button graphical object along with their associated listeners.
   * This includes the searchbar object as it has an associated button.
   */
  private void buildButtons()
  {
    upperButtons = new JPanel();
    watchListButton = new JButton("Add to Plan to Watch List");
    watchListButton.addActionListener(new WatchListButtonListener());
    searchbar = new Searchbar(controller);
    upperButtons.add(searchbar, BorderLayout.CENTER);
    upperButtons.add(watchListButton, BorderLayout.EAST);
    add(upperButtons, BorderLayout.NORTH);
  }

  /**
   * Updates the searchListModel to contain the passed list of movies and sets
   * the current view to be SEARCH.
   *
   * @param list
   *          the collection of Movies included in the recent search
   */
  public void updateSearchList(Collection<Movie> list)
  {
    if (!searchListModel.isEmpty())
    {
      searchListModel.clear();
    }
    searchListModel.addAll(list);
    switchListView(ListViews.SEARCH);
  }

  /**
   * Switches the view of the list according to the passed view. Given that some
   * methods may toggle the list, the view should update the list view depending
   * on current state as well as passed view.
   * 
   * @param view
   *          the requested view to update according to
   */
  public void switchListView(ListViews view)
  {
    listLabel.setText(view.getText());
    switch (view)
    {
      case SEARCH:
        currentListView = ListViews.SEARCH;
        watchListButton.setText("Add to Plan to Watch List");
        moviesJlist.setModel(searchListModel);
        break;
      case WATCHLIST:
        // go back to the search display if the watch-list is currently shown
        if (currentListView == ListViews.WATCHLIST)
        {
          switchListView(ListViews.SEARCH);
          break;
        }
        currentListView = ListViews.WATCHLIST;
        watchListButton.setText("Remove from Plan to Watch List");
        moviesJlist.setModel(watchListModel);
        break;
      case FEATURED:
        // go back to the search display if the featured list is currently shown
        if (currentListView == ListViews.FEATURED)
        {
          switchListView(ListViews.SEARCH);
          break;
        }
        currentListView = ListViews.FEATURED;
        watchListButton.setText("Add to Plan to Watch List");
        moviesJlist.setModel(featuredListModel);
        break;
      default:
        System.err.println("Impossible list view requested");
        break;
    }
  }

  /**
   * Getter for selectedMovie.
   * 
   * @return the currently selected movie
   */
  public Movie getSelectedMovie()
  {
    return selectedMovie;
  }
}
