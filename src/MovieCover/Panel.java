package MovieCover;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel class
 */
public class Panel extends JPanel {
    /**
     * JPanel constructor.
     * 
     * @param cover of the movie
     * @throws IOException exception
     */

    public Panel(MovieCover cover) throws IOException {
        // gets the URL and creates the image
        ImageIcon imageIcon = new ImageIcon(cover.getUrl());
        JLabel picLabel = new JLabel();
        Image img = imageIcon.getImage();
        picLabel.setIcon(imageIcon);
        add(picLabel);
        // resizes the image
        Image newimg = img.getScaledInstance(230, 310,
                java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        // sets the image as an image icon
        picLabel.setIcon(newIcon);
    }

}
