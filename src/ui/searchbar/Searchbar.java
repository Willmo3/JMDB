package ui.searchbar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.JmdbController;
import search.JsonRequestor;

/**
 * A GUI element that allows the user to enter text in order to search for a
 * film on IMDb.
 * 
 * @author Matthew Potter
 * @version 11/03/2022
 */
public class Searchbar extends JPanel
{
  private JTextField textField;
  private JButton submit;
  private String prevText;

  /**
   * Default constructor.
   * 
   * @param controller
   *          the controller that this UI element interacts with
   */
  public Searchbar(JmdbController controller)
  {
    super();
    prevText = "";
    textField = new JTextField("", 16);
    submit = new JButton("Submit");
    submit.addActionListener((action) -> {
      if (textField.getText().equals(prevText)) {
        return;
      }
      prevText = textField.getText();
      controller.updateList(JsonRequestor.search(prevText));
    });
    this.add(textField, BorderLayout.WEST);
    this.add(submit, BorderLayout.EAST);
  }
}
