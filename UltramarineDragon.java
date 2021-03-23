import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class UltramarineDragon 
{
	public static void main(String[] args)
	{
		JFrame gameFrame = new JFrame("The Ultramarine Dragon");
		gameFrame.setSize(800, 800);
		gameFrame.add(new GamePanel());
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
