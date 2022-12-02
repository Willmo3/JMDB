package mainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.JmdbController;
import list.MovieList;
import media.Movie;
import mediaDisplay.MediaDisplayPanel;
import menubar.JmdbMenuBar;
import searchbar.Searchbar;
import listmodel.AddToWatchButton;

import javax.swing.SwingConstants;

/**
 * Main GUI for the JMDb program.
 *
 * @author Sean Talbot, Matthew Potter, Immanuel Semelfort, William Morris
 * @version 11/28/2022
 */
public class JmdbGUI extends JFrame
{
  private static final long serialVersionUID = 6407031954885012174L;

  private JmdbController controller;
  private JPanel contentPane;
  private JPanel upperButtons;
  private MediaDisplayPanel selectedMoviePanel;
  private Movie selectedMovie;
  private Searchbar searchbar;
  private JScrollPane scrollPane;
  private JmdbMenuBar menu;
  private JList<Movie> jlist;
  private JList<Movie> watchList;
  private JList<Movie> featuredMovieList;
  private AddToWatchButton add;
  private boolean watchListShown;
  private boolean featuredListShown;

  /**
   * An enumerated class of all the possible views that the List of Movies may
   * have.
   * 
   * @author Matthew Potter
   * @version 11/20/2022
   */
  public static enum ListViews
  {
    /**
     * The default view shown when doing any search.
     */
    SEARCH,

    /**
     * The view showing the user-made watch-list of Movies.
     */
    WATCHLIST,

    /**
     * The view shown when using the featured movies list.
     */
    FEATURED;
  }

  /**
   * The selection listener that updates the display of the film.
   * 
   * @author Matthew Potter
   * @version 11/18/2022
   */
  private class DisplaySelectionListener implements ListSelectionListener
  {
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
      if (watchListShown)
      {
        selectedMovie = watchList.getSelectedValue();
      }
      else if (featuredListShown)
      {
        selectedMovie = featuredMovieList.getSelectedValue();
      }
      else
      {
        selectedMovie = jlist.getSelectedValue();
      }
      contentPane.remove(selectedMoviePanel);
      selectedMoviePanel = new MediaDisplayPanel(selectedMovie);
      contentPane.add(selectedMoviePanel, BorderLayout.CENTER);
      repaint();
      pack();
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
    JLabel movieListLabel = new JLabel("Movie List");
    movieListLabel.setHorizontalAlignment(SwingConstants.LEFT);
    contentPane.add(movieListLabel, BorderLayout.NORTH);
    watchListShown = false;

    // menu bar stuff
    menu = new JmdbMenuBar(this);
    setJMenuBar(menu);

    buildStartupDisplay();
    buildFunctionality();
    this.setVisible(true);
  }

  /**
   * Builds the relevant pieces for displaying the startup content.
   */
  public void buildStartupDisplay()
  {
    MovieList startupList = controller.startupListContent();
    if (startupList == null)
    {
      return;
    }
    jlist = startupList.generateJList();
    scrollPane = new JScrollPane();
    scrollPane.setViewportView(jlist);
    contentPane.add(scrollPane, BorderLayout.WEST);
    // set the startup display to be the first movie in the startup list
    jlist.setSelectedIndex(0);
    selectedMovie = jlist.getSelectedValue();
    selectedMoviePanel = new MediaDisplayPanel(selectedMovie);
    contentPane.add(selectedMoviePanel, BorderLayout.CENTER);
    repaint();
    jlist.getSelectionModel()
        .addListSelectionListener(new DisplaySelectionListener());
  }

  /**
   * Creates any objects that interact functionally with the controller.
   */
  public void buildFunctionality()
  {
    upperButtons = new JPanel();

    Collection<Movie> wList = controller.getWatchList();
    watchList = new JList<Movie>(wList.toArray(new Movie[wList.size()]));
    watchList.addListSelectionListener(new DisplaySelectionListener());
    // add the movie to the watch-list and update the display if it was changed
    add = new AddToWatchButton(controller);
    add.addActionListener((action) -> {
      if (controller.addToWatchList(selectedMovie))
      {
        // updates the JList that is displayed to show new addition
        watchList = new JList<Movie>(wList.toArray(new Movie[wList.size()]));
        watchList.addListSelectionListener(new DisplaySelectionListener());
      }
    });

    // Initializes Featured Movie List.
    Collection<Movie> fList = controller.getFeaturedMovieList();
    featuredMovieList = new JList<Movie>(fList.toArray(new Movie[fList.size()]));
    featuredMovieList.addListSelectionListener(new DisplaySelectionListener());

    searchbar = new Searchbar(controller);

    upperButtons.add(searchbar, BorderLayout.CENTER);
    upperButtons.add(add, BorderLayout.EAST);
    add(upperButtons, BorderLayout.NORTH);

    // make the JMDb application save data on exit
    addWindowListener(new java.awt.event.WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        super.windowClosing(e);
        controller.saveWatchList();
      }
    });
  }

  /**
   * Updates the displayed list of selectable nodes to display the passed list.
   *
   * @param list
   *          the movie list to update the UI with the information of
   */
  public void updateSearchList(JList<Movie> list)
  {
    list.getSelectionModel()
        .addListSelectionListener(new DisplaySelectionListener());
    jlist = list;
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
    switch (view)
    {
      case SEARCH:
        // set all booleans for toggle-able views to false
        watchListShown = false;
        featuredListShown = false;
        scrollPane.setViewportView(jlist);
        break;
      case WATCHLIST:
        // toggle watch-list display
        if (!watchListShown)
        {
          watchListShown = true;
          scrollPane.setViewportView(watchList);
        }
        else
        {
          switchListView(ListViews.SEARCH);
        }
        break;
      case FEATURED:
        if (!featuredListShown)
        {
          featuredListShown = true;
          scrollPane.setViewportView(featuredMovieList);
        }
        else
        {
          switchListView(ListViews.SEARCH);
        }
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
