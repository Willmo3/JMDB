package MovieCover;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Test class
 * @author Immanuel Semelfort
 *
 */
public class Test extends JFrame {
    public Test() throws IOException {
        this.setVisible(true);
        this.setTitle("Movie Cover");
        this.setSize(400, 400);
        this.setContentPane(new Panel(new MovieCover(
                "MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg")));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            Test movie = new Test();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
