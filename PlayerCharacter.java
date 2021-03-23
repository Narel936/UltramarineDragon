import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

public class PlayerCharacter implements KeyListener {

	private GamePanel panel;
	private ImageIcon player = new ImageIcon("sizecheck.png");
	private ImageIcon playerRight = new ImageIcon("sizecheckRight.png");
	private ImageIcon[] playerDirection = {player, playerRight};
	private int x = -300;
	private int y = -2700;
	private int xVel = 0;
	private int yVel = 0;
	private double HP = 50;
	private int travelled = 0;
	private Boolean movementAllowed = true;
	private int directionSelection = 0;
	
	public PlayerCharacter(GamePanel p)
	{
		panel = p;
	}


	public void paintCharacter(Graphics g)
	{
		updateMovement();
		playerDirection[directionSelection].paintIcon(panel, g, 300, 300);
	}

	private void updateMovement()
	{
		if(movementAllowed)
		{
			x = x + xVel;
			y = y + yVel;
			travelled = travelled + Math.abs(xVel) + Math.abs(yVel);
			if(x > 165)
			{
				x = 165;
				xVel = 0;
			}
			else if (x < -3035)
			{
				x = -3035;
				xVel = 0;
			}
			  
			if(y > 165)
			{
				y = 170;
			    yVel = 0;
			}
			else if(y < -3040)
			{
				y = -3040;
				yVel = 0;
			}
		}
		
	}
	
	//for controlling where on the map the player is
	public int getXCoordinate()
	{
		return x;
	}
	public int getYCoordinate()
	{
		return y;
	}
	
	public int getXVel()
	{
		return xVel;
	}
	public int getYVel()
	{
		return yVel;
	}
	
	
	//for displaying where the character is on the screen
	public void setXCoordinate(int input)
	{
		x = input;
		//updateMovement();
	}
	public void setYCoordinate(int input)
	{
		y = input;
		//updateMovement();
	}
	
	public void setMovement(Boolean movement)
	{
		movementAllowed = movement;
	}
	
	//used for when Character gets hit in battle
	public void setHP(double num)
	{
		HP = HP - num;
	}
	public double getHP()
	{
		return HP;
	}
	
	//used to see how far the player has gone, and to generate another enemy once it has been a certain distance
	public int getTravelled()
	{
		return travelled;
	}
	public void setTravelled()
	{
		travelled = 0;
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			xVel = -15; 
			directionSelection = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			xVel = 15;
			directionSelection = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
		{
			yVel = 15;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
		{
			yVel = -15;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		xVel = 0;
		yVel = 0;
	}

	public void keyTyped(KeyEvent e)
	{

	}

}
