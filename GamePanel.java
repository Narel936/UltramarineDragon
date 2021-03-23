import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, MouseListener, KeyListener
{
	private PlayerCharacter player = new PlayerCharacter(this);
	private Enemy enemy = new Enemy(this);
	private BossCharacter bosses = new BossCharacter(this);
	private int currentBoss = -1;
	private Timer t = new Timer(50, this);
	private Color textColor = new Color(0, 0, 0);
	private Color HPDisplay = new Color(255, 0, 0);
	private int xHPBoxLocation = 605;
	private Font gameFont = new Font ("sansserif", Font.PLAIN, 30);
	private Font introFont = new Font("sansserif", Font.PLAIN, 18);
	private Font introFontItalic = new Font("sansserif", Font.ITALIC, 18);
	private Font gameWon = new Font("sansserif", Font.PLAIN, 50);
	private Boolean fight = false;
	private Boolean battleCompleted = false;
	private Boolean enemyRegenerated = false;
	private ImageIcon worldMap = new ImageIcon("WorldMap.png");
	private Boolean tutorialComplete = false;
	private int textScroll = 0;
	private Boolean bossBattle = false;
	
	
	public GamePanel()
	{
		addMouseListener(this);
		addKeyListener(player);
		addKeyListener(this);
		setFocusable(true);
		t.start();
	}

	public void paintComponent(Graphics g)
	{	

		
		worldMap.paintIcon(this, g, player.getXCoordinate(), player.getYCoordinate());
		g.setColor(Color.white);
		if(!tutorialComplete)
		{
			player.setMovement(false);
			tutorial(g);
			player.paintCharacter(g);
		}
		else if(player.getHP() <= 0)
		{
			g.setColor(textColor);
			g.fillRect(0, 0, 3000, 3000);
			g.setColor(Color.white);
			g.setFont(gameFont);
			g.drawString("You died.", 300, 330);
			g.drawRect(250, 350, 250, 50);
			g.fillRect(250, 350, 250, 50);
			g.setColor(textColor);
			g.setFont(introFont);
			g.drawString("Click to start over", 300, 380);
		}
		else if(!bosses.getWonGame())
		{
			if(battle() == true)
			{
				player.setMovement(false);
				g.drawRoundRect(5, 700, 100, 50, 5, 5);
				g.fillRoundRect(5, 700, 100, 50, 5, 5);
				g.drawRoundRect(130, 700, 100, 50, 5, 5);
				g.fillRoundRect(130, 700, 100, 50, 5, 5);
				g.drawRoundRect(260, 700, 100, 50, 5, 5);
				g.fillRoundRect(260, 700, 100, 50, 5, 5);
				g.drawRoundRect(5, 5, 700, 50, 5, 5);
				g.fillRoundRect(5, 5, 700, 50, 5, 5);
				g.setFont(gameFont);
				g.setColor(textColor);
				g.drawString("attack", 10, 725);
				g.drawString("magic", 140, 725);
				g.drawString("defend", 270, 725);
				g.drawString("Oh no! An enemy!", 45, 45);
			}
			else if(battleCompleted == true)
			{
				g.setColor(Color.black);
				g.drawRoundRect(5, 700, 100, 50, 5, 5);
				g.fillRoundRect(5, 700, 100, 50, 5, 5);
				g.drawRoundRect(5, 5, 700, 100, 5, 5);
				g.fillRoundRect(5, 5, 700, 50, 5, 5);
				worldMap.paintIcon(this, g, player.getXCoordinate(), player.getYCoordinate());
				player.setMovement(true);
				player.setTravelled();
				enemy.regenerateEnemyHP();
				battleCompleted = false;
			}
			if(battleCompleted == false && player.getTravelled() >= 750)
			{
				if(enemyRegenerated == false)
				{
					enemy.regenerateEnemyStats();
					enemyRegenerated = true;
				}
				
				enemy.paintCharacter(g);
			}
			if(!battle())
			{
				enemy.updateMovement(player.getXVel(), player.getYVel());
			}
			
			//boss battle
			currentBoss = bosses.getWhichBoss(player.getXCoordinate(), player.getYCoordinate());
			if(bosses.inBossBattle())
			{
				bosses.updateMovement(player.getXVel(), player.getYVel());
				if(bosses.getXCoordinate() >= 180 && bosses.getXCoordinate() <= 420 && bosses.getYCoordinate() >= 200 && bosses.getYCoordinate() <= 300)
				{
					bossBattle = true;
					player.setMovement(false);
					g.drawRoundRect(5, 700, 100, 50, 5, 5);
					g.fillRoundRect(5, 700, 100, 50, 5, 5);
					g.drawRoundRect(130, 700, 100, 50, 5, 5);
					g.fillRoundRect(130, 700, 100, 50, 5, 5);
					g.drawRoundRect(260, 700, 100, 50, 5, 5);
					g.fillRoundRect(260, 700, 100, 50, 5, 5);
					g.drawRoundRect(5, 5, 700, 50, 5, 5);
					g.fillRoundRect(5, 5, 700, 50, 5, 5);
					g.setFont(gameFont);
					g.setColor(textColor);
					g.drawString("attack", 10, 725);
					g.drawString("magic", 140, 725);
					g.drawString("defend", 270, 725);
					if(currentBoss == 0 || currentBoss == 1)
					{
						g.drawString("Oh no! A boss working with Dakkanri!!", 45, 45);
					}
					else if(currentBoss == 2)
					{
						g.drawString("It's Dakkanri!! Here's our chance to restore order!!", 20, 45);
					}
					if(bosses.getHP() <= 0)
					{
						bossBattle = false;
						player.setMovement(true);
						player.setHP(-20);
						bosses.setBossDefeat();
						g.clearRect(0, 600, 100, 100);
						g.clearRect(0, 10, 800, 50);
						worldMap.paintIcon(this, g, player.getXCoordinate(), player.getYCoordinate());
					}
					
				}
			}
			drawHP(g);
			bosses.paintCharacter(g);
			player.paintCharacter(g);
		}
		else if(bosses.getWonGame())
		{
			g.setColor(textColor);
			g.fillRect(0, 0, 3000, 3000);
			g.setColor(Color.white);
			g.setFont(gameWon);
			g.drawString("You won the game!!", 150, 330);
			g.setFont(introFontItalic);
			g.drawString("Credits to Zayden Narel.", 280, 700);
			g.drawString("Many thanks to Prof M. Cohen, David Eve, and Dr. Xin Shen", 150, 730);
		}
	}
	
	//determines if the player is on top of an enemy and controls the battle from there
	public Boolean battle()
	{
		if(enemy.getXCoordinate() >= 180 && enemy.getXCoordinate() <= 420 && 
				enemy.getYCoordinate() >= 200 && enemy.getYCoordinate() <= 300 && enemyRegenerated == true)
		{
			fight = true;
		}
		
		if(enemy.getHP() <= 0)
		{
			enemyRegenerated = false;
			fight = false;
			battleCompleted = true;
		}
		//System.out.println("Enemy X:" + enemy.getXCoordinate() + " Enemy Y:" + enemy.getYCoordinate());
		return fight;
	}
	
	//draws the player's HP
	private void drawHP(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRoundRect(600, 700, 200, 50, 5, 5);
		for(int i = 0; i <= player.getHP(); i = i + 5)
		{
			g.setColor(HPDisplay);
			g.fillRect(xHPBoxLocation, 730, 15, 15);
			g.setColor(textColor);
			g.drawRect(xHPBoxLocation, 730, 15, 15);
			g.setFont(gameFont);
			g.drawString("Your HP:", 605, 725);
			xHPBoxLocation = xHPBoxLocation + 15;
			if(i >= player.getHP()-5)
			{
				xHPBoxLocation = 605;
			}
		}
	}
	
	//gives the starting tutorial
	private Boolean tutorial(Graphics g)
	{
		g.drawRoundRect(5, 5, 700, 50, 5, 5);
		g.fillRoundRect(5, 5, 700, 50, 5, 5);
		g.setColor(textColor);
		String[] introParagraph = setUpIntroArray();
		
		if(textScroll < 8)
		{
			g.setFont(introFont);
			g.drawString(introParagraph[textScroll], 30, 35);
			g.setColor(new Color(0, 0, 200));
			g.setFont(introFontItalic);
			g.drawString("Click to continue", 550, 55);
			g.drawPolygon(new int[] {690, 697, 705}, new int[] {45, 55, 45}, 3);
			g.fillPolygon(new int[] {690, 697, 705}, new int[] {45, 55, 45}, 3);
		}
		else if(textScroll >= 8)
		{
			g.setFont(gameFont);
			g.drawString("to move, use the WASD keys or the arrow keys.", 30, 35);
			player.setMovement(true);
		}
		
		if(tutorialComplete)
		{
			g.setColor(Color.black);
			g.drawRoundRect(5, 5, 700, 50, 5, 5);
			g.fillRoundRect(5, 5, 700, 50, 5, 5);
			worldMap.paintIcon(this, g, player.getXCoordinate(), player.getYCoordinate());
		}
		
		return tutorialComplete;
	}
	
	private String[] setUpIntroArray()
	{
		
		String[] introParagraph = new String[8];
		introParagraph[0] = "For a long time now, all kinds of dragons have lived in peace. Once the eastern and";
		introParagraph[1] = "western dragons settled their differences through a very long war, there had been";
		introParagraph[2] = "no trouble for generations. Now Dakkanri, a western dragon, has decided for";
		introParagraph[3] = "herself that the respect for the western drangon has been lost. In her eyes, they";
		introParagraph[4] = "are to be feared and hold power over the other dragons, creating fear in everyone";
		introParagraph[5] = "like they once did. She has started gaining gollowers, and using her magic to ";
		introParagraph[6] = "control lesser life forms as her personal army. You, as a young dragon, have ";
		introParagraph[7] = "decided to keep the balance with all dragons as it should be. ";
		return introParagraph;
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			if(textScroll >= 8 && player.getTravelled() > 200) 
			{
				tutorialComplete = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			if(textScroll >= 8 && player.getTravelled() > 200) 
			{
				tutorialComplete = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
		{
			if(textScroll >= 8 && player.getTravelled() > 200) 
			{
				tutorialComplete = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
		{
			if(textScroll >= 8 && player.getTravelled() > 200) 
			{
				tutorialComplete = true;
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e)
	{

	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!tutorialComplete && e.getX() > 0 && e.getX() < 800 && e.getY() > 0 && e.getY() < 800)
		{
			textScroll++;
		}
		
		//regular attack option in battle
		if(e.getX() > 5 && e.getX() < 100 && e.getY() > 700 && e.getY() < 750 && (bossBattle == true || battle() == true))
		{
			if(battle())
			{
				enemy.setHP(2, 0);
				player.setHP(enemy.attack(0));
			}
			if(bossBattle)
			{
				bosses.setHP(2, 0);
				player.setHP(bosses.attack(0));
			}
		}
		
		//magic attack option in battle
		if(e.getX() > 130 && e.getX() < 230 && e.getY() > 700 && e.getY() < 750 && (bossBattle == true || battle() == true))
		{
			if(battle())
			{
				enemy.setHP(2, 0);
				player.setHP(enemy.attack(1));
			}
			if(bossBattle)
			{
				bosses.setHP(2, 0);
				player.setHP(bosses.attack(1));
			}
		}
		
		//defend option in battle
		if(e.getX() > 260 && e.getX() < 360 && e.getY() > 700 && e.getY() < 750 && (bossBattle == true || battle() == true))
		{
			if(battle())
			{
				enemy.setHP(2, 0);
				player.setHP(enemy.attack(2));
			}
			if(bossBattle)
			{
				bosses.setHP(2, 0);
				player.setHP(bosses.attack(2));
			}
		}
		
		//controls reset of player when game over happens
		if(player.getHP() <= 0 && e.getX() > 250 && e.getX() < 500 && e.getY() > 345 && e.getY() < 400)
		{
			player.setHP(-50);
			player.setXCoordinate(-300);
			player.setYCoordinate(-2700);
			bosses.setHP(-30, 2);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

}
