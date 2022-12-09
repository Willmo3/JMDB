package menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mainGUI.JmdbGUI;
import mainGUI.JmdbGUI.ListViews;
import media.Movie;

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
  private JMenuItem watch, featured, refresh;

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
    watch = new JMenuItem("Watch List");
    featured = new JMenuItem("Featured Movies");
    refresh = new JMenuItem("Refresh Selected Movie Data");
    items.add(watch);
    items.addSeparator();
    items.add(featured);
    items.addSeparator();
    items.add(refresh);
    addFunctionality();
    add(items);
  }

  /**
   * Adds functionality to the MenuBar's MenuItems whose functionality doesn't
   * rely on the controller.
   */
  private void addFunctionality()
  {

    watch.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        gui.switchListView(ListViews.WATCHLIST);
      }
    });

    featured.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent actionEvent)
      {
        gui.switchListView(ListViews.FEATURED);
      }
    });
    
    refresh.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Movie selected = gui.getSelectedMovie();
        gui.getController().refresh(selected);
      }
    });
  }
}
