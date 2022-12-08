package tabbedPane;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import alert.AlertMessage;
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
            try
            {
              String award = gui.getSelectedMovie().getAward();
              JDialog awardDialog = new AlertMessage("Awards", award);
              awardDialog.setLocationRelativeTo(null); // centers frame
              awardDialog.setVisible(true);
            }
            catch (Exception ex)
            {
              System.out.println("Error opening award");
              JDialog warning = new AlertMessage("No Awards", "No awards available");
              warning.setLocationRelativeTo(null); // centers frame
              warning.setVisible(true);
            }
          }
        });
        
        b2.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            try
            {
              String rating = String.format("IMDb rating: %2.1f",
                  gui.getSelectedMovie().getImdbRating());
              JDialog ratingDialog = new AlertMessage("Rating", rating);
              ratingDialog.setLocationRelativeTo(null); // centers frame
              ratingDialog.setVisible(true);
            }
            catch (Exception ex)
            {
              System.out.println("Error opening rating");
              JDialog warning = new AlertMessage("No Rating", "No ratings available");
              warning.setLocationRelativeTo(null); // centers frame
              warning.setVisible(true);
            }
          }
        });
        
        b3.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            try
            {
              String crew = gui.getSelectedMovie().getCrew();
              JDialog crewMessage = new AlertMessage("Crew", crew);
              crewMessage.setLocationRelativeTo(null); // centers frame
              crewMessage.setVisible(true);
            }
            catch (Exception ex)
            {
              System.out.println("Error opening crew");
              JDialog warning = new AlertMessage("No crew", "Crew unavailable.");
              warning.setLocationRelativeTo(null);
              warning.setVisible(true);
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
              JDialog warning = new AlertMessage("No wiki", "Wiki link not found.");
              warning.setLocationRelativeTo(null);
              warning.setVisible(true);
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
              JDialog warning = new AlertMessage("No trailer", "Trailer link not found.");
              warning.setLocationRelativeTo(null);
              warning.setVisible(true);
            }
          }
        });
    }

}
