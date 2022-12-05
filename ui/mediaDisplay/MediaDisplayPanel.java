package mediaDisplay;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import media.Movie;

/**
 * The display panel that shows the information associated with a selected
 * Movie.
 * 
 * @author Immanuel Semelfort & Matthew Potter
 * @version 12/03/2022
 */
public class MediaDisplayPanel extends JPanel
{
  /**
   * Generated serial UID.
   */
  private static final long serialVersionUID = 8348996138626439630L;
  private JLabel picLabel;
  private MediaDisplayPanel display;

  /**
   * JPanel constructor.
   * 
   * @param media
   *          media whose information should be displayed
   * @throws IOException
   *           if the imageURL cannot be found
   */
  public MediaDisplayPanel(Movie media)
  {
    display = this;
    if (media != null)
    {
      ImageIcon loading = new ImageIcon("./images/RetrievingImage.png");
      picLabel = new JLabel();
      picLabel.setIcon(loading);
      buildCoverImage(media);
      JPanel content = new JPanel();
      content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
      content.add(picLabel);
      content.add(new JLabel(media.getTitle()));
      content.add(new JLabel(media.getDescription()));
      content.add(new JLabel(
          String.format("IMDb rating: %2.1f", media.getImdbRating())));
      add(content, BorderLayout.CENTER);
    }
  }

  private void buildCoverImage(Movie media)
  {
    new Thread(() -> {
      ImageIcon cover = fetchImage(media);
      if (cover != null)
      {
        Image image = cover.getImage();
        // resize the image to a consistent size
        Image scaledImage = image.getScaledInstance(230, 310,
            java.awt.Image.SCALE_SMOOTH);
        cover = new ImageIcon(scaledImage);
      }
      picLabel.setIcon(cover);
      display.repaint();
    }).start();
  }

  private ImageIcon fetchImage(Movie media)
  {
    try
    {
      URL url = new URL(media.getImageLink());
      BufferedImage buf = ImageIO.read(url);
      return new ImageIcon(buf);
    }
    catch (MalformedURLException e)
    {
      System.err.println("Image Address is improper in MediaDisplayPanel");
    }
    catch (IOException e)
    {
      // System.err.println("Image Address is improper in MediaDisplayPanel");
    }
    return null;
  }

}
