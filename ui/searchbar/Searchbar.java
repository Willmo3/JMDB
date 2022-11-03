package searchbar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

  /**
   * Default constructor.
   */
  public Searchbar()
  {
    super();
    textField = new JTextField("", 16);
    submit = new JButton("Submit");
    submit.addActionListener((action) -> {
      // System.out.println(textField.getText());
       JsonRequestor.search(textField.getText());
    });
    this.add(textField, BorderLayout.WEST);
    this.add(submit, BorderLayout.EAST);
  }
}
