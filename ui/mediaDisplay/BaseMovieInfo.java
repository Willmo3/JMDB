package mediaDisplay;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
 * @version 12/09/2022
 */
public class BaseMovieInfo extends JPanel
{
  private static final long serialVersionUID = 8348996138626439630L;
  private static final ImageIcon loading = new ImageIcon(
      "./images/RetrievingImage.png");
  private static final ImageIcon noImage = new ImageIcon(
      "./images/NoImage.png");

  private JPanel wrapper;
  private JLabel picLabel;
  private Movie media;

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
    if (media == null)
    {
      return;
    }
    this.media = media;
    wrapper = new JPanel();
    picLabel = new JLabel();
    picLabel.setIcon(loading);
    buildCoverImage();
    wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
    wrapper.add(picLabel);
    wrapper.add(new JLabel(media.getTitle()));
    wrapper.add(new JLabel(media.getDescription()));

    // add a clickable link to the Wikipedia link and YouTube trailer if a link
    // to them exists
    buildWikiLink();
    buildTrailerLink();

    add(wrapper, BorderLayout.CENTER);
  }

  private void buildWikiLink()
  {
    String wikiLink = media.getWiki();
    if (wikiLink != null)
    {
      wrapper.add(new JLabel("Link to the Wikipedia page:"));
      JLabel wikiLabel = new JLabel();
      buildLinkLabel(wikiLabel, wikiLink);
      wrapper.add(wikiLabel);
    }
  }

  private void buildTrailerLink()
  {
    String trailerLink = media.getTrailerLink();
    if (trailerLink != null)
    {
      wrapper.add(new JLabel("YouTube trailer:"));
      JLabel trailerLabel = new JLabel();
      buildLinkLabel(trailerLabel, trailerLink);
      wrapper.add(trailerLabel);
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

  private void buildCoverImage()
  {
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        ImageIcon cover = fetchImage();
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
    }).start();
  }

  private ImageIcon fetchImage()
  {
    try
    {
      URL url = new URL(media.getImageLink());
      BufferedImage buf = ImageIO.read(url);
      return new ImageIcon(buf);
    }
    catch (IOException e)
    {
      return noImage;
    }
  }
}
