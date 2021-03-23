import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.util.concurrent.ThreadLocalRandom;


public class Enemy {
	
	private GamePanel panel;
	private ImageIcon enemy1 = new ImageIcon("slime.png");
	private ImageIcon enemy2 = new ImageIcon("lizard.png");
	private ImageIcon enemy3 = new ImageIcon("falcon.png");
	private double HP = ThreadLocalRandom.current().nextInt(5, 15);
	private int x = ThreadLocalRandom.current().nextInt(100, 600);
	private int y = ThreadLocalRandom.current().nextInt(100, 600);
	private int count = 0;
	int randomXVel;
	int randomYVel;
	private double damage = 0;
	ImageIcon[] enemies = {enemy1, enemy2, enemy3};
	
	public int enemyChoice = ThreadLocalRandom.current().nextInt(0, 3);
	
	
	//assigns x and y coordinates for each enemy
	
	public Enemy(GamePanel p)
	{
		panel = p;
		x = ThreadLocalRandom.current().nextInt(100, 600);
		y = ThreadLocalRandom.current().nextInt(100, 600);
	}
	
	public void paintCharacter(Graphics g)
	{
		enemies[enemyChoice].paintIcon(panel, g, x, y);
		
		count ++;
		if(count > 5)
		{
			count = 0;
		}
	}
	
	public void updateMovement(int xInput, int yInput)
	{
		x = x + xInput;
		y = y + yInput;
	}
	
	public double attack(int type)
	{
		damage = ThreadLocalRandom.current().nextInt(1, 3);
		if(type == 2)
		{
			damage = damage * 0.5 + damage;
		}
		return damage;
	}
	
	public int getXCoordinate()
	{
		return x;
	}
	public int getYCoordinate()
	{
		return y;
	}
	
	public void setHP(double attack, int type)
	{
		if(enemyChoice == 0)
		{
			if(type == 0)
			{
				attack = attack * 0.5 + attack;
			}
			if(type == 1)
			{
				attack = attack * 1.5 + attack;
			}
		}
		if(enemyChoice == 1)
		{
			if(type == 0)
			{
				attack = attack * 1.5 + attack;
			}
			if(type == 1)
			{
				attack = attack * 0.5 + attack;
			}	
		}
		if(enemyChoice == 2)
		{
			if(type == 0)
			{
				attack = attack * 0.5 + attack;
			}
			if(type == 1)
			{
				attack = attack * 0.5 + attack;
			}
		}
		HP = HP - attack;
	}
	
	public void regenerateEnemyStats()
	{
		x = ThreadLocalRandom.current().nextInt(100, 600);
		y = ThreadLocalRandom.current().nextInt(100, 600);
		enemyChoice = ThreadLocalRandom.current().nextInt(0, 3);
		
	}
	
	public void regenerateEnemyHP()
	{
		HP = ThreadLocalRandom.current().nextInt(5, 15);
	}
	
	public double getHP()
	{
		return HP;
	}
}
