package mediaDisplay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import cast.Job;
import cast.Worker;
import media.Movie;
import media.ResultTypes;
import search.JsonRequestor;

/**
 * The tabbed display panel for the selected Media object's data.
 * 
 * @author Immanuel Semelfort, Matthew Potter
 * @version 12/08/2022
 */
public class MediaDisplay extends JPanel
{
  private static final long serialVersionUID = 974458634623052710L;

  private class CrewWriter implements Runnable
  {
    /**
     * Queries the API for cast/crew list, and prints it to the crewText
     * JTextPane if the list is available. Otherwise, it removes the tab.
     */
    @Override
    public void run()
    {
      crew = JsonRequestor.queryCrew(movie.getId());
      // no Cast/Crew, so remove the tab
      if (crew == null)
      {
        tabs.remove(tabs.indexOfTab("Cast/Crew"));
        return;
      }

      Document crewDoc = crewText.getDocument();
      for (Job job : crew)
      {
        try
        {
          crewDoc.insertString(crewDoc.getLength(), job.getJob() + "\n", null);
          for (Worker worker : job.getWorkers())
          {
            crewDoc.insertString(crewDoc.getLength(),
                "\t" + worker.toString() + "\n", null);
          }
        }
        catch (BadLocationException e)
        {
          System.err.println(
              "Tried to print to bad location in the document for Cast/Crew");
          e.printStackTrace();
        }
      }
    }
  }

  private Movie movie;
  private JTabbedPane tabs;
  private JTextPane awardText;
  private JTextPane crewText;
  private List<Job> crew;

  /**
   * Constructs a MenuBar item with no controller-based functionality.
   * 
   * @param movie
   *          the Movie whose data should be displayed
   */
  public MediaDisplay(Movie movie)
  {
    super();
    this.movie = movie;
    tabs = new JTabbedPane();
    tabs.addTab("Overview", new BaseMovieInfo(movie));

    buildRatingsTab();
    buildAwardsTab();
    // if the movie could have a Cast/Crew associated with it, add the Cast/Crew
    // tab and create the thread that gets that information
    if (movie.getType() == ResultTypes.TITLE
        || movie.getType() == ResultTypes.SERIES)
    {
      crewText = new JTextPane();
      JScrollPane crewScroll = new JScrollPane(crewText);
      crewScroll.setPreferredSize(new Dimension(600, 600));
      // crewText.setEditable(false);
      tabs.addTab("Cast/Crew", crewScroll);
      new Thread(new CrewWriter()).start();
    }
    // thread the addition of the Cast/Crew tab
    add(tabs, BorderLayout.WEST);
  }

  private void buildAwardsTab()
  {
    // awards tab. May implement larger list of awards later, but right now the
    // scroll pane and stuff is for visual consistency
    awardText = new JTextPane();
    awardText.setText("Fetching Award");
    JScrollPane awardScroll = new JScrollPane(awardText);
    awardScroll.setPreferredSize(new Dimension(600, 600));
    tabs.addTab("Awards", awardScroll);
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        String award = movie.getAward();
        awardText.setText(award);
      }
    }).start();
  }

  private void buildRatingsTab()
  {
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        double[] ratings = movie.getRatings();
        if (ratings != null)
        {
          tabs.insertTab("Ratings", null, new MovieRatingsDisplay(ratings),
              null, 1);
        }
      }
    }).start();
  }

}
