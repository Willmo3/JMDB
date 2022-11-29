package searchbar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.JmdbController;
import search.JsonRequestor;
import search.JsonRequestor.QueryTypes;

/**
 * A GUI element that allows the user to enter text in order to search for a
 * film on IMDb.
 * 
 * @author Matthew Potter
 * @version 11/20/2022
 */
public class Searchbar extends JPanel
{
  /**
   * Generated serialVersionUID.
   */
  private static final long serialVersionUID = 5923332114032813179L;

  private String prevText;
  private QueryTypes selectedType;
  private JTextField textField;
  private JButton submit;
  private JComboBox<QueryTypes> typeSelector;

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
    selectedType = QueryTypes.TITLE;

    textField = new JTextField("", 16);
    submit = new JButton("Submit");
    // on pressing the submit button, if new text was passed, then search with
    // that query and update the GUI
    submit.addActionListener((action) -> {
      String text = textField.getText();
      if (text.isBlank() || text.equals(prevText))
      {
        return;
      }
      prevText = text;
      controller.updateSearchList(JsonRequestor.search(selectedType, prevText));
    });

    typeSelector = new JComboBox<>(QueryTypes.values());
    typeSelector.addActionListener((action) -> {
      Object source = action.getSource();
      if (source instanceof JComboBox<?>)
      {
        // convert the source of the action to a generic JComboBox so eclipse
        // doesn't yell about unchecked conversion
        JComboBox<?> selector = (JComboBox<?>) source;
        // really checking to make sure it is of QueryTypes type
        Object selectedObj = selector.getSelectedItem();
        if (selectedObj instanceof QueryTypes)
        {
          QueryTypes selected = (QueryTypes) selectedObj;
          // they chose the same type again
          if (selectedType == selected)
          {
            return;
          }
          else
          {
            // since the type was changed, they can search again even if they
            // haven't changed their query text
            selectedType = selected;
            prevText = "";
          }
        }
      }
    });
    this.add(typeSelector, BorderLayout.WEST);
    this.add(textField, BorderLayout.CENTER);
    this.add(submit, BorderLayout.EAST);
  }
}
