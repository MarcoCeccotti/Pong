package Utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Sbarra;
import dataObstacles.Tubo;

public class Elements
{
	private ArrayList<Sfondo> sfondi;
	private ArrayList<Ostacolo> items;
	
	//il raggio delle sfere
	private int ray;
	
	private float maxH, maxW;
	private int width, height;
	
	public Elements( GameContainer gc ) throws SlickException
		{
			maxH = gc.getHeight()*100/104;
			maxW = gc.getWidth();
			width = gc.getHeight()/10;
			height = gc.getWidth()/20;
			
			ray = gc.getWidth()/32;
		
			int heightS = gc.getHeight()*10/24;
			sfondi = new ArrayList<Sfondo>();
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo.png" ), maxH, maxW, gc.getWidth()/8, heightS, width, height, "sfondo" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo2.png" ), maxH, maxW, gc.getWidth()*29/100, heightS, width, height, "sfondo2" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo3.jpg" ), maxH, maxW, gc.getWidth()*46/100, heightS, width, height, "sfondo3" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo4.jpg" ), maxH, maxW, gc.getWidth()*63/100, heightS, width, height, "sfondo4" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo5.jpg" ), maxH, maxW, gc.getWidth()*8/10, heightS, width, height, "sfondo5" ) );			

			items = new ArrayList<Ostacolo>();
			int heightObs = gc.getHeight()*10/17;
			items.add( new Sbarra( gc.getWidth()/5, heightObs + gc.getHeight()/20 - gc.getHeight()/60, "hor", gc ) );
			items.add( new Tubo( gc.getWidth()/2 - gc.getHeight()/20, heightObs, "sx", gc ) );
			items.add( new Bubble( gc.getWidth()*2/3 + gc.getHeight()/20 - gc.getHeight()/48, heightObs, ray, maxW, gc ) );
			int heightP = gc.getHeight()*3/4;
			items.add( new Player( gc.getWidth()*2/10, heightP, 1, gc, Color.red ) );
			items.add( new Player( gc.getWidth()*4/10, heightP, 2, gc, Color.blue ) );
			items.add( new Player( gc.getWidth()*6/10, heightP, 3, gc, Color.yellow ) );
			items.add( new Player( gc.getWidth()*8/10, heightP, 4, gc, Color.green ) );
		}
	
	public ArrayList<Sfondo> getSfondi()
		{ return sfondi; }
	
	public ArrayList<Ostacolo> getItems()
		{ return items; }
}
