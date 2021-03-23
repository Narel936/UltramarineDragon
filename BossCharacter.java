import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;

public class BossCharacter 
{
	private GamePanel panel;
	//these variables determine the boss that is displayed, and what that bosses health is.
	private ImageIcon boss1 = new ImageIcon("Boss1.png");
	private double boss1HP = 15;
	private Boolean boss1Defeat = false;
	private ImageIcon boss2 = new ImageIcon("Boss2.png");
	private double boss2HP = 15;
	private Boolean boss2Defeat = false;
	private ImageIcon finalBoss = new ImageIcon("Boss3.png");
	private Boolean finalBossDefeat = false;
	private double finalBossHP = 10;
	//this variable is used to quickly and easily determine what the current boss's HP is to determine the status of the battle
	private double currentBossHP = 1;
	private int x = 300;
	private int y = 100;
	ImageIcon[] bosses = {boss1, boss2, finalBoss};
	private int bossSelection = 0;
	private Boolean nearBoss = false;
	private Boolean won = false;
	
	public BossCharacter(GamePanel p)
	{
		panel = p;
	}
	
	public void paintCharacter(Graphics g)
	{
		if(bossSelection == 0)
		{
			if(nearBoss && !boss1Defeat) 
			{
				bosses[bossSelection].paintIcon(panel, g, x, y);
			}
		}
		if(bossSelection == 1)
		{
			if(nearBoss && !boss2Defeat) 
			{
				bosses[bossSelection].paintIcon(panel, g, x, y);
			}
		}
		if(bossSelection == 2)
		{
			if(nearBoss && !finalBossDefeat) 
			{
				bosses[bossSelection].paintIcon(panel, g, x, y);
			}
		}
		
	}
	
	public void updateMovement(int xInput, int yInput)
	{
		x = x + xInput;
		y = y + yInput;
	}
	
	public int getWhichBoss(int playerX, int playerY)
	{
		if(playerX > -885 && playerY > -1700 && !boss1Defeat)
		{
			nearBoss = true;
			bossSelection = 0;
			currentBossHP = boss1HP;
		}
		if(playerX < -885 && playerY < -2000 && !boss2Defeat)
		{
			nearBoss = true;
			bossSelection = 1;
			currentBossHP = boss2HP;
		}
		if(playerX > -500 && playerY > -1700 && boss1Defeat && boss2Defeat)
		{
			nearBoss = true;
			bossSelection = 2;
			currentBossHP = finalBossHP;
		}
		return bossSelection;
	}
	
	public void setBossDefeat()
	{
		if(bossSelection == 0)
		{
			boss1Defeat = true;
			nearBoss = false;
			currentBossHP = 1;
		}
		if(bossSelection == 1)
		{
			boss2Defeat = true;
			nearBoss = false;
			currentBossHP = 1;
		}
		if(bossSelection == 2)
		{
			finalBossDefeat = true;
			nearBoss = false;
		}
	}
	
	public Boolean getWonGame()
	{
		if(finalBossDefeat)
		{
			won = true;
		}
		else
		{
			won = false;
		}
		return won;
	}
	
	public double attack(int type)
	{
		double damage = ThreadLocalRandom.current().nextInt(3, 10);
		if(type == 2)
		{
			damage = damage * 0.5 + damage;
		}
		return damage;
	}
	
	public void setHP(double attack, int type)
	{
		if(bossSelection == 0)
		{
			currentBossHP = boss2HP;
			if(type == 0)
			{
				attack = attack * 0.5 + attack;
			}
			if(type == 1)
			{
				attack = attack * 1.5 + attack;
			}
			boss1HP = boss1HP - attack;
			currentBossHP = boss1HP;
		}
		if(bossSelection == 1)
		{
			currentBossHP = boss2HP;
			if(type == 0)
			{
				attack = attack * 1.5 + attack;
			}
			if(type == 1)
			{
				attack = attack * 0.5 + attack;
			}
			boss2HP = boss2HP - attack;
			currentBossHP = boss2HP;
		}
		if(bossSelection == 2)
		{
			currentBossHP = finalBossHP;
			if(type == 0)
			{
				attack = attack * 0.5 + attack;
			}
			if(type == 1)
			{
				attack = attack * 0.5 + attack;
			}
			finalBossHP = finalBossHP - attack;
			currentBossHP = finalBossHP;
		}
	}
	
	public double getHP()
	{
		return currentBossHP;
	}
	
	public int getXCoordinate()
	{
		return x;
	}
	
	public int getYCoordinate()
	{
		return y;
	}
	
	public Boolean inBossBattle()
	{
		return nearBoss;
	}
}
