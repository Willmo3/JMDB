package ui.mainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.JmdbController;
import list.MovieList;
import media.Movie;
import ui.mediaDisplay.MediaDisplayPanel;
import ui.searchbar.Searchbar;
import javax.swing.*;
import javax.swing.SwingConstants;

/**
 * Main GUI for the JMDb program.
 * 
 * @author Sean Talbot and Matthew Potter
 * @version 11/04/2022
 */
public class JmdbGUI extends JFrame {
    private static JMenuBar mb;
    private static JMenu x;
    private static JMenuItem m1, m2, m3, m4;
    private static final long serialVersionUID = 6407031954885012174L;
    private JPanel contentPane;
    private MediaDisplayPanel selectedMoviePanel;
    private Searchbar searchbar;
    private JScrollPane scrollPane;
    private JList<Movie> jlist;

    private class DisplaySelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            contentPane.remove(selectedMoviePanel);
            selectedMoviePanel = new MediaDisplayPanel(
                    jlist.getSelectedValue());
            contentPane.add(selectedMoviePanel, BorderLayout.CENTER);
            repaint();
            pack();
        }

    }

    /**
     * Create the frame.
     */
    public JmdbGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 720);
        setMinimumSize(new Dimension(1280, 720));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel movieListLabel = new JLabel("Movie List");
        movieListLabel.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(movieListLabel, BorderLayout.NORTH);

        mb = new JMenuBar();
        x = new JMenu("Menu");
        m1 = new JMenuItem("Trailer");
        m2 = new JMenuItem("Awards");
        m3 = new JMenuItem("Cast & Crew");
        m4 = new JMenuItem("Reviews & Ratings");
        x.add(m1);
        x.add(m2);
        x.add(m3);
        x.add(m4);

        mb.add(x);
        setJMenuBar(mb);

        FileInputStream startupList;
        MovieList list = null;
        try {
            startupList = new FileInputStream("src/ui/mainGUI/startupList.json");
            list = new MovieList(startupList);
        } catch (FileNotFoundException e1) {
            System.err.println("Startup list not found");
        } catch (IOException e1) {
            System.err.println("MovieList couldn't be made from startupList");
        } finally {
            if (list == null) {
                return;
            }
        }
        jlist = list.generateJList();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(jlist);
        contentPane.add(scrollPane, BorderLayout.WEST);
        selectedMoviePanel = new MediaDisplayPanel(new Movie("tt1790736",
                "https://m.media-amazon.com/images/M/MV5BMjE0NGIwM2EtZjQxZi00ZTE5LWExN2MtNDBlMjY1ZmZkYjU3XkEyXkFqcGdeQXVyNjMwNzk3Mjk@._V1_Ratio0.6800_AL_.jpg",
                "Inception: The Cobol Job", "(2010 Video)"));
        // selectedMoviePanel.setVerticalAlignment(SwingConstants.TOP);
        // selectedMoviePanel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(selectedMoviePanel, BorderLayout.CENTER);

        jlist.getSelectionModel().addListSelectionListener(
                new DisplaySelectionListener());
    }

    public void updateList(JList<Movie> list) {
        list.getSelectionModel().addListSelectionListener(
                new DisplaySelectionListener());
        jlist = list;
        scrollPane.setViewportView(jlist);
    }

    public void buildFunctionality(JmdbController controller) {
        searchbar = new Searchbar(controller);
        add(searchbar, BorderLayout.NORTH);
    }
}