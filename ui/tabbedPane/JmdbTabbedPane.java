package tabbedPane;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mainGUI.JmdbGUI;
import media.Movie;
import trailer.TrailerButton;

public class JmdbTabbedPane extends JTabbedPane {

    private static final long serialVersionUID = 974458634623052710L;
    private JmdbGUI gui;
    private JTabbedPane tabs;
    private JButton b1, b2, b3, b4, b5;

    /**
     * Constructs a MenuBar item with no controller-based functionality.
     * 
     * @param gui the gui this element will be added to
     */
    public JmdbTabbedPane(JmdbGUI gui) {
        super();
        this.gui = gui;
       
        b1 = new JButton("Display Awards");    
        b2 = new JButton("Display Rating");
        b3 = new JButton("Display Crew");
        b4 = new JButton("Open Wiki Page");
        b5 = new JButton("Open Trailer");
 
        tabs = new JTabbedPane();
        tabs.addTab("Awards", b1);
        tabs.addTab("Reviews & Ratings", b2);
        tabs.addTab("Crew", b3);
        tabs.addTab("Wikipedia Page", b4);
        tabs.addTab("Trailer", b5);
        add(tabs);
        addFunctionality();

    }
    private void addFunctionality() {
//      
//        tabs.addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                JFrame frame2 = new JFrame();
//                try {
//                    String award = gui.getSelectedMovie().getAward();
//                    JLabel label = new JLabel(award);
//                    frame2.add(label);
//                    frame2.setSize(400, 100);
//                    frame2.setLocationRelativeTo(null); // centers frame
//                    frame2.setVisible(true);
//                } catch (Exception ex) {
//                    System.out.println("Error opening award");
//                }
//            }
//        });
        
        b1.addActionListener(new ActionListener()
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
        
        b2.addActionListener(new ActionListener()
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
        
        b3.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            JFrame frame2 = new JFrame();
            try
            {
              String crew = gui.getSelectedMovie().getCrew();
              JLabel label = new JLabel(crew);
              frame2.add(label);
              frame2.setSize(400, 100);
              frame2.setLocationRelativeTo(null); // centers frame
              frame2.setVisible(true);
            }
            catch (Exception ex)
            {
              System.out.println("Error opening crew");
            }
          }
        });
        
        b4.addActionListener(new ActionListener()
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
              JFrame frame2 = new JFrame();
              JLabel label = new JLabel("No Wiki Link");
              frame2.add(label);
              frame2.setSize(400, 100);
              frame2.setLocationRelativeTo(null); // centers frame
              frame2.setVisible(true);
            }
            
          }
        });
        
        b5.addActionListener(new ActionListener()
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
    }

}
