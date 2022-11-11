package ui.mediaDisplay;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import media.Movie;

/**
 * The display panel that shows the information associated with a selected
 * Movie.
 * 
 * @author Immanuel Semelfort & Matthew Potter
 * @version 11/10/2022
 */
public class MediaDisplayPanel extends JPanel
{
  private JLabel picLabel;

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
    if (media != null)
    {
      picLabel = new JLabel();
      buildCoverImage(media);
      add(picLabel, BorderLayout.WEST);
      add(new JLabel(media.getTitle()), BorderLayout.NORTH);
      add(new JLabel(media.getDescription()), BorderLayout.NORTH);
      add(new JLabel(String.format("IMDb rating: %2.1f", media.getRating())),
          BorderLayout.SOUTH);
    }
  }

  private void buildCoverImage(Movie media)
  {
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
      System.err.println("Image Address is improper in MediaDisplayPanel");
    }
    return null;
  }

}
