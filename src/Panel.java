
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel class
 * @author Immanuel Semelfort
 *
 */
public class Panel extends JPanel {
    /**
     * JPanel constructor.
     * @param cover of the movie
     * @throws IOException exception
     */
    public Panel(MovieCover cover) throws IOException {
        ImageIcon imageIcon =  new ImageIcon(cover.getUrl());
        JLabel picLabel = new JLabel();
        Image img = imageIcon.getImage();
        picLabel.setIcon(imageIcon);
        add(picLabel);
      Image newimg = img.getScaledInstance(230, 310,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        picLabel.setIcon(newIcon);
       

    }
}
