import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PanelPacmanGame extends JPanel{
	
	private Color wallColor=Color.BLUE;
	private Color wallColor2=Color.CYAN;

	private double sizePacman=0.65;	
	private Color pacmansColor = Color.yellow;
	
	private Color ghostsColor = Color.white;
	private Color ghostScarredColor=Color.white;

	private double sizeFood=0.3; 
	private Color colorFood=Color.white; 

	private double sizeCapsule=0.7;
	private Color colorCapsule=Color.red;

	Maze m;
	
	private ArrayList<PositionAgent> pacmans_pos;
	private ArrayList<PositionAgent> ghosts_pos;
	
	
	private boolean ghostsScarred;
	

	public PanelPacmanGame(Maze maze) {
		this.m = maze;
		pacmans_pos = this.m.getPacman_start();
		ghosts_pos = this.m.getGhosts_start();
		ghostsScarred = false;		
	}

	public void paint(Graphics g){
		
		int dx=getSize().width;
		int dy=getSize().height;
		g.setColor(Color.black);
		g.fillRect(0, 0,dx,dy);

		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;
		double posx=0;
		
		for(int x=0;x<sx;x++)
		{
			double posy=0;
			for(int y=0;y<sy;y++)
			{
				if (m.isWall(x, y))
				{
					g.setColor(wallColor2);
					g.fillRect((int)posx, (int)posy, (int)(stepx+1),(int)(stepy+1));
					g.setColor(wallColor);
					double nsx=stepx*0.5;
					double nsy=stepy*0.5;
					double npx=(stepx-nsx)/2.0;
					double npy=(stepy-nsy)/2.0;
					g.fillRect((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);						
				}
				if (m.isFood(x, y))
				{
					g.setColor(colorFood);
					double nsx=stepx*sizeFood;
					double nsy=stepy*sizeFood;
					double npx=(stepx-nsx)/2.0;
					double npy=(stepy-nsy)/2.0;
					g.fillOval((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);
				}
				if (m.isCapsule(x, y))
				{
					g.setColor(colorCapsule);
					double nsx=stepx*sizeCapsule;
					double nsy=stepy*sizeCapsule;
					double npx=(stepx-nsx)/2.0;
					double npy=(stepy-nsy)/2.0;
					g.fillOval((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);
				}
				posy+=stepy;				
			}
			posx+=stepx;
		}

		
		for(int i = 0; i < pacmans_pos.size(); i++){
			PositionAgent pos = pacmans_pos.get(i);
			drawPacmans(g, pos.getX(), pos.getY(), pos.getDir(), pacmansColor);	
		}

		for(int i = 0; i < ghosts_pos.size(); i++){
			PositionAgent pos = ghosts_pos.get(i);
			if(m.estInvinsible) {
				drawGhosts(g, pos.getX(), pos.getY(), ghostScarredColor);	
			} else {
				switch(i % 4){
				case 0 :
					drawGhosts(g, pos.getX(), pos.getY(), Color.orange);						
					break;
				case 1 :
					drawGhosts(g, pos.getX(), pos.getY(), Color.red);	
					break;
				case 2 :
					drawGhosts(g, pos.getX(), pos.getY(), Color.pink);	
					break;
				default :
					drawGhosts(g, pos.getX(), pos.getY(), Color.cyan);	
				break;
				}
				
			}
		}
		
	}

	void drawPacmans(Graphics g, int px, int py, int pacmanDirection, Color color)
	{

		int dx = getSize().width;
		int dy = getSize().height;

		int sx = m.getSizeX();
		int sy = m.getSizeY();
		double stepx = dx/(double)sx;
		double stepy = dy/(double)sy;

		double posx=px*stepx;
		double posy=py*stepy;

		if(!m.estInvinsible){
		g.setColor(color);
		} else {
			g.setColor(Color.blue);
		}
		double nsx=stepx*sizePacman;
		double nsy=stepy*sizePacman;
		double npx=(stepx-nsx)/2.0;
		double npy=(stepy-nsy)/2.0;
		int sa=0;
		int fa=0;
		double temp1 = 0;
		double temp2 = 0;
		
		//sa orientation de la bouche
		//fa largeur de la bouche 
		//temp1 decalage vers la droite 
		//temp2
		if (pacmanDirection==Maze.NORTH)
		{
			sa=70; fa=-320;
			temp1 = fa/100;
			temp2 = sa/10000;
		}
		if (pacmanDirection==Maze.SOUTH)
		{
			sa=250; fa=-320;
			temp1 = -fa/70;
		//	temp2 = sa/80;
		}
		if (pacmanDirection==Maze.EAST)
		{
			sa=340; fa=-320;
			temp1 = sa/75;
			temp2 = fa/75;
		}
		if (pacmanDirection==Maze.WEST)
		{
			sa=160; fa=-320;
			temp1 = sa/30;
			temp2 = fa/30;
		}
		if (pacmanDirection==Maze.STOP)
		{
			sa=70; fa=-320;
			temp1 = fa/100;
			temp2 = sa/10000;
		}
		
		g.fillArc((int)(npx+posx),(int)(npy+posy),(int)(nsx+10),(int)nsy,sa,fa);
		g.setColor(Color.BLACK);
		g.fillOval((int)(temp1+posx+npx+nsx/5.0),(int)(temp2+npy+posy+nsy/3.0),200/m.getSizeX(),150/m.getSizeY());
		
	}

	
	void drawGhosts(Graphics g, int px, int py, Color color){


		int dx=getSize().width;
		int dy=getSize().height;

		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;

		double posx=px*stepx;
		double posy=py*stepy;

		g.setColor(color);

		double nsx=stepx*sizePacman;
		double nsy=stepy*sizePacman;
		double npx=(stepx-nsx)/2.0;
		double npy=(stepy-nsy)/2.0;

		g.fillArc((int)(posx+npx),(int)(npy+posy),(int)(nsx),(int)(nsy),0,180);
		g.fillRect((int)(posx+npx),(int)(npy+posy+nsy/2.0-1),(int)(nsx),(int)(nsy*0.666));
		g.setColor(Color.BLACK);
		g.fillOval((int)(posx+npx+nsx/5.0),(int)(npy+posy+nsy/3.0),4,4);
		g.fillOval((int)(posx+npx+3*nsx/5.0),(int)(npy+posy+nsy/3.0),4,4);

		g.setColor(Color.black);

	}



	public void setGhostsScarred(boolean ghostsScarred) {
		this.ghostsScarred = ghostsScarred;
	}
	

	public ArrayList<PositionAgent> getPacmans_pos() {
		return pacmans_pos;
	}

	public void setPacmans_pos(ArrayList<PositionAgent> pacmans_pos) {
		this.pacmans_pos = pacmans_pos;
	}

	public ArrayList<PositionAgent> getGhosts_pos() {
		return ghosts_pos;
	}

	public void setGhosts_pos(ArrayList<PositionAgent> ghosts_pos) {
		this.ghosts_pos = ghosts_pos;
	}


}
