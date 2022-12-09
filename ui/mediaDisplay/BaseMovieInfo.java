package mediaDisplay;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import alert.AlertMessage;
import media.Movie;

/**
 * The display panel that shows the information associated with a selected
 * Movie.
 * 
 * @author Immanuel Semelfort & Matthew Potter
 * @version 12/03/2022
 */
public class BaseMovieInfo extends JPanel
{
  /**
   * Generated serial UID.
   */
  private static final long serialVersionUID = 8348996138626439630L;
  private JLabel picLabel;
  private BaseMovieInfo display;

  /**
   * JPanel constructor.
   * 
   * @param media
   *          media whose information should be displayed
   * @throws IOException
   *           if the imageURL cannot be found
   */
  public BaseMovieInfo(Movie media)
  {
    super();
    display = this;
    if (media == null)
    {
      return;
    }
    ImageIcon loading = new ImageIcon("./images/RetrievingImage.png");
    picLabel = new JLabel();
    picLabel.setIcon(loading);
    buildCoverImage(media);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(picLabel);
    add(new JLabel(media.getTitle()));
    add(new JLabel(media.getDescription()));

    // add a clickable link to the Wikipedia link and YouTube trailer if a link
    // to them exists
    String wikiLink = media.getWiki();
    if (wikiLink != null)
    {
      add(new JLabel("Link to the Wikipedia page:"));
      JLabel wikiLabel = new JLabel();
      buildLinkLabel(wikiLabel, wikiLink);
      add(wikiLabel);
    }
    String trailerLink = media.getTrailerLink();
    if (trailerLink != null)
    {
      add(new JLabel("YouTube trailer:"));
      JLabel trailerLabel = new JLabel();
      buildLinkLabel(trailerLabel, trailerLink);
      add(trailerLabel);
    }
  }

  private void buildLinkLabel(JLabel label, String link)
  {
    label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    label.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        super.mouseClicked(e);
        try
        {
          Desktop.getDesktop().browse(new URL(link).toURI());
        }
        catch (IOException | URISyntaxException e1)
        {
          System.out.println("Error opening desired link:");
          System.out.println(link);
          JDialog warning = new AlertMessage("Bad Link",
              "The desired link was unable to be opened.");
          warning.setLocationRelativeTo(null);
          warning.setVisible(true);
        }
      }
    });
    label.setText(link);
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
