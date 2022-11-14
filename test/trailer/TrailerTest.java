package trailer;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;

/**
 * Rudimentary test for the trailer button.
 * Ensures that the trailer can open links in YouTube.
 *
 * @author William Morris
 * @version 11/13/2022
 */
public class TrailerTest {

  /*
  // Valid main
  public static void main(String[] args) throws URISyntaxException {
    TrailerButton button = new TrailerButton("https://www.youtube.com/watch?v=Jvurpf91omw");
    JFrame trailerFrame = new JFrame();
    trailerFrame.add(button);
    trailerFrame.setPreferredSize(new Dimension(100, 100));
    trailerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    trailerFrame.setVisible(true);
  }*/

  public static void main(String[] args) throws URISyntaxException {
    TrailerButton button = new TrailerButton("Badlink!");
    JFrame trailerFrame = new JFrame();
    trailerFrame.add(button);
    trailerFrame.setPreferredSize(new Dimension(100, 100));
    trailerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    trailerFrame.setVisible(true);
  }
}
