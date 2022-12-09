package mediaDisplay;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import media.Movie;

/**
 * The tabbed display panel for the selected Media object's data.
 * 
 * @author Immanuel Semelfort, Matthew Potter
 * @version 12/08/2022
 */
public class MediaDisplay extends JPanel
{

  private static final long serialVersionUID = 974458634623052710L;
  
  private JTabbedPane tabs;

  /**
   * Constructs a MenuBar item with no controller-based functionality.
   * 
   * @param movie
   *          the Movie whose data should be displayed
   */
  public MediaDisplay(Movie movie)
  {
    super();
    tabs = new JTabbedPane();
    tabs.addTab("Overview", new BaseMovieInfo(movie));
    tabs.addTab("Awards", new JLabel(movie.getAward()));

    double[] ratings = movie.getRatings();
    if (ratings != null)
    {
      tabs.addTab("Ratings", new MovieRatingsDisplay(ratings));
    }

    JTextPane crewLabel = new JTextPane();
    tabs.addTab("Crew", crewLabel);
    add(tabs, BorderLayout.WEST);
  }
}
