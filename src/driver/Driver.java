package driver;

import java.awt.EventQueue;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import controller.JmdbController;
//import mainGUI.JmdbGUI;
import mainGUI.JmdbGUI;

/**
 * Driver for the JMDb program.
 * 
 * @author Matthew Potter
 * @version 11/04/2022
 */
public class Driver
{

  /**
   * Runs the JMDb program.
   * 
   * @param args
   *          the commandline arguments
   */
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          FlatDarkLaf.setup(); // I believe this is all we need, might revisit
          JmdbGUI gui = new JmdbGUI();
          JmdbController controller = new JmdbController(gui);
          gui.setVisible(true);
          gui.setLocationRelativeTo(null); // centers main GUI window
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }
}
