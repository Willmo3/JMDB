package driver;

import java.awt.EventQueue;

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
   * Runs the JMDb program
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
          JmdbGUI gui = new JmdbGUI();
          JmdbController controller = new JmdbController(gui);
          gui.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }
}
