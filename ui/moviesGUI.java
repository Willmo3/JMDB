package movies.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingConstants;

public class moviesGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6407031954885012174L;
	private JPanel contentPane;
	private JLabel selectedMovieLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					moviesGUI frame = new moviesGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public moviesGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel movieListLabel = new JLabel("Movie List");
		movieListLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(movieListLabel, BorderLayout.NORTH);
		
		
		DefaultListModel<String> first = new DefaultListModel<>();

		for (int i = 0; i < 100; i++) {
			first.addElement("Movie " + i);		
		}
		
		JList<String> list = new JList<String>(first);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		contentPane.add(scrollPane, BorderLayout.WEST);
		
		selectedMovieLabel = new JLabel("Movie");
		selectedMovieLabel.setVerticalAlignment(SwingConstants.TOP);
		selectedMovieLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(selectedMovieLabel, BorderLayout.CENTER);
		
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel movie = (ListSelectionModel)e.getSource();  // just string object right now
				selectedMovieLabel = new JLabel(movie.toString());
				
				
			}
		});
	}
}
