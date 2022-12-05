package driver;

import java.awt.EventQueue;

import com.formdev.flatlaf.FlatDarkLaf;
import controller.JmdbController;
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
          FlatDarkLaf.setup();
          JmdbController controller = new JmdbController();
          JmdbGUI gui = new JmdbGUI(controller);
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
