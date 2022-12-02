package menubar;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mainGUI.JmdbGUI;
import mainGUI.JmdbGUI.ListViews;
import media.Movie;
import trailer.TrailerButton;

/**
 * The MenuBar for the JMDb GUI.
 * 
 * @author Immanuel Semelfort, Matthew Potter
 * @version 11/18/2022
 */
public class JmdbMenuBar extends JMenuBar
{
  /**
   * Generated Serial Version UID.
   */
  private static final long serialVersionUID = 974458634623052710L;
  private JmdbGUI gui;
  private JMenu items;
  private JMenuItem trailer, awards, watch, reviews, wiki, featured;

  /**
   * Constructs a MenuBar item with no controller-based functionality.
   * 
   * @param gui
   *          the gui this element will be added to
   */
  public JmdbMenuBar(JmdbGUI gui)
  {
    super();
    this.gui = gui;
    items = new JMenu("Menu");
    trailer = new JMenuItem("Trailer");
    awards = new JMenuItem("Awards");
    watch = new JMenuItem("Watch List");
    reviews = new JMenuItem("Reviews & Ratings");
    wiki = new JMenuItem("Wikipedia Page");
    featured = new JMenuItem("Featured Movies");
    items.add(trailer);
    items.addSeparator();
    items.add(awards);
    items.addSeparator();
    items.add(watch);
    items.addSeparator();
    items.add(reviews);
    items.addSeparator();
    items.add(wiki);
    items.addSeparator();
    items.add(featured);
    addFunctionality();
    add(items);
  }

  /**
   * Adds functionality to the MenuBar's MenuItems whose functionality doesn't
   * rely on the controller.
   */
  private void addFunctionality()
  {
    trailer.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Movie movie = gui.getSelectedMovie();
        if (movie == null)
        {
          System.out.println("Movie is null, please select a movie.");
          return;
        }
        try
        {
          TrailerButton trailerBtn = new TrailerButton(movie.getTrailerLink());
          trailerBtn.openPage();
        }
        catch (Exception ex)
        {
          System.out.println("Error opening trailer");
          System.out.println(movie.getTrailerLink());
        }
      }
    });
    
    awards.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFrame frame2 = new JFrame();
        try
        {
          String award = gui.getSelectedMovie().getAward();
          JLabel label = new JLabel(award);
          frame2.add(label);
          frame2.setSize(400, 100);
          frame2.setLocationRelativeTo(null); // centers frame
          frame2.setVisible(true);
        }
        catch (Exception ex)
        {
          System.out.println("Error opening award");
        }
      }
    });
    
    watch.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        gui.switchListView(ListViews.WATCHLIST);
      }
    });

    featured.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent)
      {
        gui.switchListView(ListViews.FEATURED);
      }
    });
    
    reviews.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFrame frame2 = new JFrame();
        try
        {
          String rating = String.format("IMDb rating: %2.1f",
              gui.getSelectedMovie().getImdbRating());

          JLabel label = new JLabel(rating);
          frame2.add(label);
          frame2.setSize(200, 100);
          frame2.setLocationRelativeTo(null); // centers frame
          frame2.setVisible(true);
        }
        catch (Exception ex)
        {
          System.out.println("Error opening rating");
        }
      }
    });
    
    wiki.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Movie movie = gui.getSelectedMovie();
        if (movie == null)
        {
          System.out.println("Movie is null, please select a movie.");
          return;
        }
            try {
                Desktop.getDesktop().browse(new URL(movie.getWiki()).toURI());
        }
        catch (Exception ex)
        {
          System.out.println("Error opening wiki");
          System.out.println(movie.getWiki());
        }
        
      }
    });
  }
}
