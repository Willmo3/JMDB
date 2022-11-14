package trailer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Button for a MovieTrailer.
 * Opens a trailer link in the web browser.
 *
 * @author William Morris
 * @version 11/13/2022
 */
public class TrailerButton extends JButton {
  private URI link;
  private Desktop desktop;

  public TrailerButton(String strLink) throws URISyntaxException {
    this.link = new URI(strLink);
    if (Desktop.isDesktopSupported()) {
      desktop = Desktop.getDesktop();
    }

    setText("View Trailer In Browser");

    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        openPage();
      }
    });
  }

  /**
   * Opens a webpage using the user's default browser.
   */
  public void openPage() {
    if (desktop == null) {
      throw new IllegalStateException("No desktop present");
    }

    try {
      desktop.browse(link);
    } catch (IOException E) {
      System.err.println("Invalid link!");
    }
  }
}
