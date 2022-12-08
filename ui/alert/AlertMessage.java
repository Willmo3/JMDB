package alert;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a small JDialog window that notifies the user of minor alerts.
 *
 * @author William Morris
 * @version 12/8/2022
 */
public class AlertMessage extends JDialog
{
  /**
   * AlertMessage constructor. Takes a title and centers a message in the JDialog.
   *
   * @param title Title of the JDialog.
   * @param message Message to display in the JDialog.
   */
  public AlertMessage(String title, String message) {
    setTitle(title);
    JLabel messageLabel = new JLabel();
    messageLabel.setText(message);
    // Centers text in Jlabel.
    messageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    messageLabel.setVerticalTextPosition(SwingConstants.CENTER);
    // Centers JLabel in dialog.
    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    messageLabel.setVerticalAlignment(SwingConstants.CENTER);
    add(messageLabel);
    setSize(new Dimension(400, 100));
  }
}
