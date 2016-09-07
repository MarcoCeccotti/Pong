package dataObstacles;
 
import interfaces.InGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import Utils.Global;
import bubbleMaster.Start;
 
public class Bubble extends Ostacolo
{
    private float ray;
     
    private Circle ostr;
 
    private int speedX = -1, speedY = -1;
     
    private Image immagine = new Image( "./data/Image/Palla.png" );
     
    private double maxH;
    private double maxW;
     
    private boolean collided;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	
	//determina il tubo in cui e' entrata la sfera (ma non il tubo in uscita)
	private int indexTube, previousIndexTube;
	//determina se la sfera e' nel primo o secondo tubo
	private boolean primoTubo, secondoTubo;
	//determina se le velocita' della sfera sono state settate
	private boolean setSpeed;
     
    public Bubble( Ostacolo ost, GameContainer gc ) throws SlickException
        { this( (int) ost.getX(), (int) ost.getY(), (int) ost.getWidth(), ost.getMaxWidth(), gc ); }
     
    public Bubble( int x, int y, int ray, double maxW, GameContainer gc ) throws SlickException
        {       
            super( "bolla" );
             
            this.ray = ray;
            ostr = new Circle( x + ray, y + ray, ray );
            
            this.maxW = maxW;
            
            primoTubo = false;
            secondoTubo = false;
            indexTube = -1;
            previousIndexTube = -1;
        }
     
    public void draw( Graphics g ) throws SlickException
        {
    		immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2 );
    		
    		if(Start.editGame == 1)
    			{	
    				if(checkInsert)
    					if(!insert)
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cr);
    					else
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cg);
    			}
		}
    
    public void updateStats()
    	{
    		ray = ray * Global.ratioH;
    		ostr.setCenterX( getX() * Global.ratioW );
    		ostr.setCenterY( getY() * Global.ratioH );
    		
    		ostr = new Circle( ostr.getCenterX(), ostr.getCenterY(), ray );
    		maxW = maxW * Global.ratioW;
    		maxH = maxH * Global.ratioH;
    	}
    
    public double getMaxWidth()
    	{ return maxW; }
     
    public Circle getArea()
        { return ostr; }
 
    public void setXY( float x, float y, String function )
        {
            if(function.compareTo( "move" ) == 0)
                ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
             
            else if(function.compareTo( "restore" ) == 0)
                ostr.setLocation( x, y );
             
            else if(function.compareTo( "setRay" ) == 0)
                ray = x;
        }
 
    public boolean contains( int x, int y )
        { return ostr.contains( x, y ); }
 
    public float getX()
        { return (int) ostr.getX(); }
 
    public float getY()
        { return (int) ostr.getY(); }
     
    private void setCenter( Shape ostr, int x, int y )
        {
            ostr.setCenterX( ostr.getCenterX() + x );
            ostr.setCenterY( ostr.getCenterY() + y );
        }
     
    public void setMaxHeight( double val )
        { maxH = val; }
     
    public double getMaxHeight()
        { return maxH; }
 
    public Shape component( String part )
        { return ostr; }
 
    public float getWidth()
        { return ray; }
 
    public float getHeight()
        { return ray; }
     
    public void setCollided( boolean val )
        { collided = val; }
     
    public boolean isCollided()
        { return collided; }
 
    public int getSpeedX()
        { return speedX; }
 
    public int getSpeedY()
        { return speedY; }
 
    public void setSpeed( Integer x, Integer y )
        {
    		if(x != null)
    			speedX = x;
    		if(y != null)
    			speedY = y;
        }
    
    public boolean getCollide()
    	{ return true; }
    
    public void setCollide( boolean val )
    	{}
    
    //determina la collisione con gli spigoli con speedX o speedY nulla
    // TODO PER LA COLLISIONE CON GLI SPIGOLI PER LE VELOCITA NULLE SI VEDRA
    private boolean collisionEdge( Ostacolo ost )
    	{
    		if(ostr.intersects( ost.component( "spigASx" ) ))
	    		{
	    			if(speedX == 0 && speedY > 0)
	    				{
	    					if(ostr.getCenterX() < ost.getX())
	    						{
	    							speedX = -1;
	    							speedY = -speedY;
	    						}
	    				}
	    			else if(speedX > 0 && speedY == 0)
	    				{
	    					if(ostr.getCenterY() < ost.getY())
	    						{
	    							speedX = -speedX;
	    							speedY = -1;
	    						}
	    				}
	    			else
	    				return false;
	    		}
    		else if(ostr.intersects( ost.component( "spigADx" ) ))
    			{
	    			if(speedX == 0 && speedY > 0)
						{
							if(ostr.getCenterX() > ost.getMaxX())
								{
									speedX = 1;
									speedY = -speedY;
								}
						}
					else if(speedX < 0 && speedY == 0)
						{
							if(ostr.getCenterY() < ost.getY())
								{
									speedX = -speedX;
									speedY = -1;
								}
						}
	    			else
	    				return false;
    			}
    		else if(ostr.intersects( ost.component( "spigBSx" ) ))
	    		{
	    			if(speedX == 0 && speedY < 0)
	    				{
	    					if(ostr.getCenterX() < ost.getX())
	    						{
	    							speedX = -1;
	    							speedY = -speedY;
	    						}
	    				}
	    			else if(speedX > 0 && speedY == 0)
	    				{
	    					if(ostr.getCenterY() > ost.getY() + ost.getHeight())
	    						{
	    							speedX = -speedX;
	    							speedY = -1;
	    						}
	    				}
	    			else
	    				return false;
	    		}
    		else if(ostr.intersects( ost.component( "spigBDx" ) ))
    			{
	    			if(speedX == 0 && speedY > 0)
						{
							if(ostr.getCenterX() > ost.getMaxX())
								{
									speedX = 1;
									speedY = -speedY;
								}
						}
					else if(speedX < 0 && speedY == 0)
						{
							if(ostr.getCenterY() > ost.getY() + ost.getHeight())
								{
									speedX = -speedX;
									speedY = -1;
								}
						}
	    			else
	    				return false;
    			}
		
	    	return false;
    	}
    
    /**determina se la sfera e' ancora nel primo tubo o se e' ancora nel secondo*/
    public void gestioneSferaInTubo()
    	{
			Ostacolo tubo = InGame.ostacoli.get( indexTube );
    		// se la sfera e' nel PRIMO tubo
    		if(primoTubo)
	    		{
    				if(tubo.getOrienting().equals( "sx" ) && ostr.getCenterX() >= tubo.getMidArea().getX())
						primoTubo = false;
    				else if(tubo.getOrienting().equals( "dx" ) && ostr.getCenterX() < tubo.getMidArea().getX())
						primoTubo = false;
    				else if(tubo.getOrienting().equals( "up" ) && ostr.getCenterY() >= tubo.getMidArea().getY())
						primoTubo = false;
    				else if(tubo.getOrienting().equals( "down" ) && ostr.getCenterY() < tubo.getMidArea().getY())
						primoTubo = false;

    				// setta la velocita' nel PRIMO tubo
        			if(!setSpeed)
	        			{
		    				if(tubo.getOrienting().equals( "sx" ) || tubo.getOrienting().equals( "dx" ))
		    					speedY = 0;
		    				else if(tubo.getOrienting().equals( "down" ) || tubo.getOrienting().equals( "up" ))
		    					speedX = 0;
		    				
		    				setSpeed = false;
	        			}
        			if(!primoTubo)
        				{
        					secondoTubo = true;
        					setSpeed = true;
        					previousIndexTube = indexTube;
        					indexTube = InGame.ostacoli.get( indexTube ).getUnion();
        					setPositionInTube( InGame.ostacoli.get( indexTube ), primoTubo );
        				}
	    		}
    		// la sfera e' nel SECONDO tubo
    		else if(secondoTubo)
    			{
    				previousIndexTube = -1;
	    			if(!(tubo.component( "rect" ).intersects( ostr )) && !(tubo.component( "latoIngresso" ).intersects( ostr )))
	    				{
	    					secondoTubo = false;
	    					primoTubo = false;
	    					indexTube = -1;
	    				}
    			}
    	}
    
    /**determina la velocita' risultante nella collisione fra sfera e altri ostacoli*/
    public void gestioneCollisioni( Ostacolo ost, boolean dritto )
    	{
	    	if(ostr.intersects( ost.component( "rect" ) ))
		        {
					// alto a sinistra
					if(speedX < 0 && speedY < 0)
						{
							if(ostr.intersects( ost.component( "spigADx" ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "spigBSx" ) ))
								speedY = -speedY;
							else if(ostr.intersects( ost.component( "spigBDx" ) ))
								{
									if(ostr.getCenterX() > ost.component( "spigBDx" ).getX() && ostr.getCenterY() > ost.component( "spigBDx" ).getY())
										{
											speedX = -speedX;
											speedY = -speedY;
										}
									else if(ostr.intersects( ost.component( "latoDx" ) ) && ostr.getCenterY() < ost.component( "spigBDx" ).getY())
			    						speedX = -speedX;
			    					else if(ostr.intersects( ost.component( "latoGiu" ) ))
			    						speedY = -speedY;
								}
							else if(ostr.intersects( ost.component( "latoDx" ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "latoGiu" ) ))
								speedY = -speedY;
						}
					// alto a destra
					else if(speedX > 0 && speedY < 0)
						{
		    				if(ostr.intersects( ost.component( "spigASx" ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "spigBDx" ) ))
								speedY = -speedY;
							else if(ostr.intersects( ost.component( "spigBSx" ) ))
								{
		    						if(ostr.getCenterX() < ost.component( "spigBSx" ).getMaxX() && ostr.getCenterY() > ost.component( "spigBSx" ).getY())
										{
											speedX = -speedX;
											speedY = -speedY;
										}
		    						else if(ostr.intersects( ost.component( "latoSx" ) ) && ostr.getCenterY() < ost.component( "spigBSx" ).getY())
			    						speedX = -speedX;
			    					else if(ostr.intersects( ost.component( "latoGiu" ) ))
			    						speedY = -speedY;
								}
							else if(ostr.intersects( ost.component( "latoSx" ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "latoGiu" ) ))
								speedY = -speedY;
						}
					// basso a destra
					else if(speedX > 0 && speedY > 0)
						{
		    				if(ostr.intersects( ost.component( "spigBSx" ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "spigADx" ) ))
								speedY = -speedY;
							else if(ostr.intersects( ost.component( "spigASx" ) ))
								{
									if(ostr.getCenterY() < ost.component( "spigASx" ).getMaxY() && ostr.getCenterX() < ost.component( "spigASx" ).getMaxX())
										{
											speedX = -speedX;
											speedY = -speedY;
										}
			    					else if(ostr.intersects( ost.component( "latoSx" ) ) && ostr.getCenterY() > ost.component( "spigASx" ).getMaxX())
			    						speedX = -speedX;
			    					else if(ostr.intersects( ost.component( "latoSu" ) ))
			    						speedY = -speedY;
								}
							else if(ostr.intersects( ost.component( "latoSx" ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "latoSu" ) ))
								speedY = -speedY;
						}
					// basso a sinistra
					else if(speedX < 0 && speedY > 0)
						{
		    				if(ostr.intersects( ost.component( "spigBDx" ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "spigASx" ) ))
								speedY = -speedY;
							else if(ostr.intersects( ost.component( "spigADx" ) ))
								{
									if(ostr.getCenterX() > ost.component( "spigADx" ).getX() && ostr.getCenterY() < ost.component( "spigADx" ).getMaxY())
										{
											speedX = -speedX;
											speedY = -speedY;
										}
			    					else if(ostr.intersects( ost.component( "latoDx"  ) ) && ostr.getCenterY() > ost.component( "spigADx" ).getMaxY())
			    						speedX = -speedX;
			    					else if(ostr.intersects( ost.component( "latoSu" ) ))
			    						speedY = -speedY;
								}
							else if(ostr.intersects( ost.component( "latoDx"  ) ))
								speedX = -speedX;
							else if(ostr.intersects( ost.component( "latoSu" ) ))
								speedY = -speedY;
						}
		    		
		    		if(secondoTubo)
		    			secondoTubo = false;
		        }
    	}
    
    /**setta la posizione della sfera all'interno dei 2 tubi e la velocita' nel secondo*/
    public void setPositionInTube( Ostacolo ost, boolean primoTubo )
    	{
    		//orientamento del tubo
			String pos = ost.getOrienting();
			
    		//la sfera e' nel PRIMO tubo
    		if(primoTubo)
    			{
	    			if(pos.equals( "sx" ))
						setXY( ost.component( "latoIngresso" ).getX() - getWidth(), ost.component( "latoIngresso" ).getY() + ost.getHeight()/2 - getWidth(), "restore" );
					else if(pos.equals( "dx" ))
						setXY( ost.component( "latoIngresso" ).getX() + getWidth(), ost.component( "latoIngresso" ).getY() + ost.getHeight()/2 - getWidth(), "restore" );
					else if(pos.equals( "down" ))
						setXY( ost.component( "latoIngresso" ).getX() + ost.getWidth()/2 - getWidth() - 4, ost.component( "latoIngresso" ).getY(), "restore" );
					else
						setXY( ost.component( "latoIngresso" ).getX() - 1 + ost.getWidth()/2 - getWidth(), ost.component( "latoIngresso" ).getY() - getWidth(), "restore" );   			
    				
    			}
    		//la sfera e' nel SECONDO tubo
    		else
    			{
	    			setXY( ost.getMidArea().getX() - getWidth(), ost.getMidArea().getY() - getWidth(), "restore" );
					
					if(ost.getOrienting().equals( "sx" ))
						{
							speedX = -1;
							speedY = 0;
						}
					else if(ost.getOrienting().equals( "dx" ))
						{
							speedX = 1;
							speedY = 0;
						}
					else if(ost.getOrienting().equals( "up" ))
						{
							speedX = 0;
							speedY = -1;
						}
					else
						{
							speedX = 0;
							speedY = 1;
						}
    			}
    	}
    
    /**gestisce collisioni fra tutti gli elementi*/
    public void checkAll( int i, Ostacolo ost )
    	{	
	    	if(!ost.getID().equals( "bolla" ))
		        {
		        	if(ost.getID().equals( "tubo" ) && !primoTubo && !secondoTubo)
		        		{
		        			//il lato di ingresso nel tubo
		        			Shape ingr = ost.component( "latoIngresso" );
		        			if(ostr.intersects( ingr ) && ostr.getCenterY() > ingr.getY() && ostr.getCenterY() < ingr.getY() + ost.getHeight())
		        				{
		    						primoTubo = true;
		        					indexTube = i;
		        					setPositionInTube( ost, primoTubo );
		        				}
		        			else if(ostr.intersects( ingr ) && ostr.getCenterX() > ost.getX() && ostr.getCenterX() < ost.getX() + ost.getWidth())
		        				{
									primoTubo = true;
		    						indexTube = i;
		    						setPositionInTube( ost, primoTubo );
		        				}
		        		}
		        	else if(primoTubo || secondoTubo)
						gestioneSferaInTubo();                        	
		        	
		        	if(!primoTubo)
		        		{
			        		if(!primoTubo && ostr.intersects( ost.component( "rect" ) ))
				        		{
					        		if(!secondoTubo || (secondoTubo && indexTube != i && previousIndexTube != i))
			        					if(speedX == 0 || speedY == 0)
			    							gestioneCollisioni( ost, true );
			        					else
			        						gestioneCollisioni( ost, false );
				        		}
		        		}
		        }
    	}
    
    public void checkBorders()
    	{
	    	if(ostr.getX() + 2*ray >= maxW)
	        	if(speedX > 0)
	        		speedX = -speedX;
	        if(ostr.getX() <= 0)
	        	if(speedX < 0)
	        		speedX = -speedX;
	        if(ostr.getY() + 2*ray >= maxH)
	        	if(speedY > 0)
	        		speedY = -speedY;
	        if(ostr.getY() <= 0)
	        	if(speedY < 0)
	        		speedY = -speedY;
    	}
 
    public void update( GameContainer gc, int delta ) throws SlickException
        {
            for(int i = 0; i < InGame.ostacoli.size(); i++)
                checkAll( i, InGame.ostacoli.get( i ) );
             
            /*controllo collisione con i bordi della schermata*/
            checkBorders();

            setCenter( ostr, (int) (speedX * Global.ratioW), (int) (speedY * Global.ratioH) );
            
            if(!primoTubo)
            	{
            		if(speedX == 0 || speedY == 0)
            			{
                        	/*controllo collisione con i bordi della schermata*/
	                        checkBorders();
	                        
	                        for(int i = 0; i < InGame.ostacoli.size(); i++)
	                        	checkAll( i, InGame.ostacoli.get( i ) );

	                        setCenter( ostr, (int) (speedX * Global.ratioW), (int) (speedY * Global.ratioH) );
            			}
            	}
        }
 
    public void setType(String type)
        {}
 
    public float getMaxX()
        { return ray*2; }
 
    public Ostacolo clone( GameContainer gc ) {
        try {
            return new Bubble( (int) ostr.getX(), (int) ostr.getY(), (int) ray, maxW, gc );
        } catch (SlickException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setInsert(boolean insert, boolean change)
		{
			checkInsert = !change;
			this.insert = insert;
		}

	public void update(GameContainer gc) throws SlickException 
		{}

    public void setOrienting( String direction )
    	{}

    public String getOrienting()
    	{ return null; }

    public void setSpigoli()
    	{}

	public int getUnion()
		{ return -1; }

	public void setUnion(int val)
		{}

	public Point getMidArea()
		{ return null; }
}