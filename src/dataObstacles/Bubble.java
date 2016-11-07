package dataObstacles;
 
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import bubbleMaster.Start;
import interfaces.InGame;
 
public class Bubble extends Ostacolo
{
    private float ray;
     
    private Circle ostr;
 
    private float speedX, speedY;
     
    private Image immagine = new Image( "./data/Image/Palla.png" );
     
    private double maxH;
    private double maxW;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	
	//determina il tubo in cui e' entrata la sfera (ma non il tubo in uscita)
	private int indexTube;
	//determina se la sfera e' nel primo o secondo tubo
	private boolean primoTubo, secondoTubo;
	//determina se le velocita' della sfera sono state settate
	private boolean setSpeed;
	
	private float backupSpeedX, backupSpeedY;
     
    public Bubble( Ostacolo ost, GameContainer gc ) throws SlickException
        { this( (int) ost.getX(), (int) ost.getY(), (int) ost.getWidth(), ost.getMaxWidth(), gc ); }
     
    public Bubble( float x, float y, float ray, double maxW, GameContainer gc ) throws SlickException
        {       
            super( "bolla" );
             
            this.ray = ray;
            ostr = new Circle( x + ray, y + ray, ray );
            
            this.maxW = maxW;
            
            primoTubo = false;
            secondoTubo = false;
            indexTube = -1;
            
            float minSpeed = 0.4f;
            
            speedX = (float) Math.random() * 2;
            if(speedX < minSpeed)
            	speedX = minSpeed;
            speedY = (float) Math.random() * 2;
            if(speedY < minSpeed)
            	speedY = minSpeed;
            
            float sign = (float) Math.random();
            if(sign < 0.5f)
            	speedX = -speedX;
            sign = (float) Math.random();
            if(sign < 0.5f)
            	speedY = -speedY;
        }
     
    public void draw( Graphics g ) throws SlickException
        {
    		immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2 );
    		
    		if(Start.editGame == 1)
    			{	
    				if(checkInsert)
    					if(!insert)
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cr );
    					else
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cg );
    			}
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
    
    public void setPrimoTubo( boolean val )
    	{ primoTubo = val; }
    
    public boolean getPrimoTubo()
    	{ return primoTubo; }
    
    public void setIndexTube( int val )
    	{ indexTube = val; }
    
    public int getIndexTube()
    	{ return indexTube; }
 
    public boolean contains( int x, int y )
        { return ostr.contains( x, y ); }
 
    public float getX()
        { return (int) ostr.getX(); }
 
    public float getY()
        { return (int) ostr.getY(); }
     
    private void setCenter( Shape ostr, float x, float y )
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
        { return 2*ray; }
 
    public float getHeight()
        { return 2*ray; }
 
    public float getSpeedX()
        { return speedX; }
 
    public float getSpeedY()
        { return speedY; }
 
    public void setSpeed( float x, float y )
        {
    		speedX = x;
    		speedY = y;
        }
    
    public boolean getCollide()
    	{ return true; }
    
    public void setCollide( boolean val )
    	{}
    
    public void setArea()
    	{ ostr = new Circle( ostr.getCenterX(), ostr.getCenterY(), ray ); }
    
    public void setMaxWidth( float val )
    	{ maxW = val; }
    
    /**determina la velocita' risultante nella collisione fra sfera e altri ostacoli*/
    public void gestioneCollisioni( Ostacolo ost )
    	{
    		// TODO MIGLIORARE (SE POSSIBILE) IL RIMBALZO CON GLI SPIGOLI
    		// E' BUONO GIA COSI, MA IN ALCUNI CASI NON MI CONVINCE
    	
			// alto a sinistra || in alto
			if((speedX < 0 && speedY < 0) || (speedX == 0 && speedY < 0))
				{
					if(ostr.intersects( ost.component( "spigBSx" ) ))
						{
							speedY = -speedY;
							if(speedX == 0)
								if(ostr.getCenterX() < ost.component( "spigBSx" ).getX())
									speedX = -1;
						}
					else if(ostr.intersects( ost.component( "spigADx" ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( "spigBDx" ) ))
						{
							if(ostr.getCenterX() > ost.component( "spigBDx" ).getX() && ostr.getCenterY() > ost.component( "spigBDx" ).getY())
								{
									float tmp = speedX;
									speedX = -speedY;
									speedY = -tmp;
									if(speedX == 0)
										speedX = 1;
								}
							else if(speedX == 0)
								speedY = -speedY;
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
			// alto a destra || a destra
			else if((speedX > 0 && speedY < 0) || (speedX > 0 && speedY == 0))
				{
					if(ostr.intersects( ost.component( "spigBDx" ) ))
						speedY = -speedY;
					else if(ostr.intersects( ost.component( "spigASx" ) ))
						{
							speedX = -speedX;
							if(speedY == 0)
								if(ostr.getCenterY() < ost.component( "spigASx" ).getY())
									speedY = -1;
						}
					else if(ostr.intersects( ost.component( "spigBSx" ) ))
						{
    						if(ostr.getCenterX() < ost.component( "spigBSx" ).getMaxX() && ostr.getCenterY() > ost.component( "spigBSx" ).getY())
								{
    								float tmp = speedX;
    								speedX = speedY;
    								speedY = tmp;
    								if(speedY == 0)
    									speedY = 1;
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
			// basso a destra || in basso
			else if((speedX > 0 && speedY > 0) || (speedX == 0 && speedY > 0))
				{
    				if(ostr.intersects( ost.component( "spigBSx" ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( "spigADx" ) ))
						{
							speedY = -speedY;
							if(speedX == 0)
								if(ostr.getCenterX() > ost.component( "spigADx" ).getMaxY())
									speedX = 1;
						}
					else if(ostr.intersects( ost.component( "spigASx" ) ))
						{
							if(ostr.getCenterY() < ost.component( "spigASx" ).getMaxY() && ostr.getCenterX() < ost.component( "spigASx" ).getMaxX())
								{
									float tmp = speedX;
									speedX = -speedY;
									speedY = -tmp;
									if(speedX == 0)
										speedX = -1;
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
			// basso a sinistra || a sinistra
			else if((speedX < 0 && speedY > 0) || (speedX < 0 && speedY == 0))
				{
    				if(ostr.intersects( ost.component( "spigBDx" ) ))
    					{
							speedX = -speedX;
							if(speedY == 0)
								if(ostr.getCenterY() > ost.component( "spigBDx" ).getY())
									speedY = 1;
    					}
					else if(ostr.intersects( ost.component( "spigASx" ) ))
						speedY = -speedY;
					else if(ostr.intersects( ost.component( "spigADx" ) ))
						{
							if(ostr.getCenterX() > ost.component( "spigADx" ).getX() && ostr.getCenterY() < ost.component( "spigADx" ).getMaxY())
								{
									float tmp = speedX;
									speedX = speedY;
									speedY = tmp;
									if(speedY == 0)
										speedY = -1;
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
    
    /**setta la posizione della sfera all'interno dei 2 tubi e la velocita' nel secondo*/
    public void setPositionInTube( Ostacolo ost, boolean primoTubo )
    	{
    		//orientamento del tubo
			String pos = ost.getOrienting();
			// spigolo di riferimento per l'ingresso
			Shape ingr = ost.component( "spigASx" );
			
    		//la sfera e' nel PRIMO tubo
    		if(primoTubo)
    			{
	    			if(pos.equals( "sx" ))
						setXY( ostr.getX(), ingr.getY() + ost.getHeight()/2 - getHeight()/2, "restore" );
					else if(pos.equals( "dx" ))
						setXY( ostr.getX(), ingr.getY() + ost.getHeight()/2 - getHeight()/2, "restore" );
					else if(pos.equals( "down" ))
						setXY( ingr.getX() + ost.getWidth()/2 - getWidth()/2, ostr.getY(), "restore" );
					else
						setXY( ingr.getX() + ost.getWidth()/2 - getWidth()/2, ostr.getY(), "restore" );
    			}
    		//la sfera e' nel SECONDO tubo
    		else
    			{
	    			setXY( ost.getMidArea()[0] - getWidth()/2, ost.getMidArea()[1] - getHeight()/2, "restore" );
					
					if(ost.getOrienting().equals( "sx" ))
						{
							speedX = -backupSpeedX;
							speedY = 0;
						}
					else if(ost.getOrienting().equals( "dx" ))
						{
							speedX = backupSpeedX;
							speedY = 0;
						}
					else if(ost.getOrienting().equals( "up" ))
						{
							speedX = 0;
							speedY = -backupSpeedY;
						}
					else
						{
							speedX = 0;
							speedY = backupSpeedY;
						}
    			}
    	}
    /**determina se la sfera e' ancora nel primo tubo o se e' ancora nel secondo*/
    public void gestioneSferaInTubo()
    	{
			Ostacolo tubo = InGame.ostacoli.get( indexTube );
    		// se la sfera e' nel PRIMO tubo
    		if(primoTubo)
	    		{
    				if(tubo.getOrienting().equals( "sx" ) && ostr.getCenterX() >= tubo.getMidArea()[0])
						primoTubo = false;
    				else if(tubo.getOrienting().equals( "dx" ) && ostr.getCenterX() < tubo.getMidArea()[0])
						primoTubo = false;
    				else if(tubo.getOrienting().equals( "up" ) && ostr.getCenterY() >= tubo.getMidArea()[1])
						primoTubo = false;
    				else if(tubo.getOrienting().equals( "down" ) && ostr.getCenterY() < tubo.getMidArea()[1])
						primoTubo = false;

    				// setta la velocita' nel PRIMO tubo
        			if(!setSpeed)
	        			{
    		    			backupSpeedX = Math.abs( speedX );
    		    			backupSpeedY = Math.abs( speedY );
        				
		    				if(tubo.getOrienting().equals( "sx" ) || tubo.getOrienting().equals( "dx" ))
		    					speedY = 0;
		    				else if(tubo.getOrienting().equals( "down" ) || tubo.getOrienting().equals( "up" ))
		    					speedX = 0;
		    				
		    				setSpeed = true;
	        			}
        			if(!primoTubo)
        				{
        					secondoTubo = true;
        					setSpeed = true;
        					indexTube = InGame.ostacoli.get( indexTube ).getUnion();
        					setPositionInTube( InGame.ostacoli.get( indexTube ), primoTubo );
        				}
	    		}
    		// la sfera e' nel SECONDO tubo
    		else if(secondoTubo)
    			{
	    			if(!tubo.component( "rect" ).intersects( ostr ))
	    				{
	    					secondoTubo = false;
	    					primoTubo = false;
	    					indexTube = -1;
	    				}
    			}
    	}
    
    /**gestisce collisioni fra tutti gli elementi*/
    public void checkAll( int i, Ostacolo ost )
    	{
    		if(ost.getID().equals( "tubo" ))
    			{					
					if(!primoTubo)
    					{
							// il lato di ingresso nel tubo
							Shape ingr = ost.component( "latoIngresso" );
	    					// se la sfera ha colliso con l'ingresso di un tubo
    						if((secondoTubo && i!= indexTube) || !secondoTubo)
		    					if(checkEnter( ingr, ((Tubo) ost) ))
		    						{
		    							indexTube = i;
		    							primoTubo = true;
		    							secondoTubo = false;
			        					setPositionInTube( ost, primoTubo );
		    						}
    					}
    				if(primoTubo || secondoTubo)
    					gestioneSferaInTubo();
    			}
        	
			if(secondoTubo)
				{
					if(indexTube != i && !ost.getID().equals( "base" ) && !ost.getID().equals( "enter" ))
						if(ostr.intersects( ost.component( "rect" ) ))
							gestioneCollisioni( ost );
				}
			else if(!primoTubo && !ost.getID().equals( "tubo" ))
				if(ostr.intersects( ost.component( "rect" ) ))
					gestioneCollisioni( ost );
    	}
    
    public boolean checkEnter( Shape ingr, Tubo ost )
    	{
    		String dir = ost.getDirection();

    		if(dir.equals( "sx" ) || dir.equals( "dx" ))
	    		{
	    			if(!ostr.intersects( ost.component( "latoSu" ) ) && !ostr.intersects( ost.component( "latoGiu" ) ))
			    		if((ostr.intersects( ingr ) && ostr.getCenterY() > ingr.getY() && ostr.getCenterY() < ingr.getY() + ost.getHeight())
						|| (ostr.intersects( ingr ) && ostr.getCenterX() > ost.getX() && ostr.getCenterX() < ost.getX() + ost.getWidth()))
			    			return true;
	    		}
    		else if(dir.equals( "up" ) || dir.equals( "down" ))
    			{
	    			if(!ostr.intersects( ost.component( "latoSx" ) ) && !ostr.intersects( ost.component( "latoDx" ) ))
			    		if((ostr.intersects( ingr ) && ostr.getCenterY() > ingr.getY() && ostr.getCenterY() < ingr.getY() + ost.getHeight())
						|| (ostr.intersects( ingr ) && ostr.getCenterX() > ost.getX() && ostr.getCenterX() < ost.getX() + ost.getWidth()))
			    			return true;
    			}
    		
    		return false;
    	}
 
    public void update( GameContainer gc, int delta ) throws SlickException
        {
            for(int i = 0; i < InGame.ostacoli.size(); i++)
            	if(!InGame.ostacoli.get( i ).getID().equals( "bolla" ))
            		checkAll( i, InGame.ostacoli.get( i ) );
             
            /*controllo collisione con i bordi della schermata*/
            checkBorders();

            setCenter( ostr, speedX, speedY );
            
            if(!primoTubo)
            	{
            		if(speedX == 0 || speedY == 0)
            			{
                        	/*controllo collisione con i bordi della schermata*/
	                        checkBorders();
	                        
	                        for(int i = 0; i < InGame.ostacoli.size(); i++)
	                        	if(!InGame.ostacoli.get( i ).getID().equals( "bolla" ))
	                        		checkAll( i, InGame.ostacoli.get( i ) );

	                        setCenter( ostr, speedX, speedY );
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
 
    public void setType(String type)
        {}
 
    public float getMaxX()
        { return ray*2; }
 
    public Ostacolo clone( GameContainer gc ) {
        try
	        {        	
	            Bubble b = new Bubble( (int) ostr.getX(), (int) ostr.getY(), (int) ray, maxW, gc );
	            b.setSpeed( speedX, speedY );
	            return b;
	        }
        catch (SlickException e)
	        {
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

    public void setOrienting( GameContainer gc )
    	{}

    public String getOrienting()
    	{ return null; }

    public void setSpigoli()
    	{}

	public int getUnion()
		{ return -1; }

	public void setUnion(int val)
		{}

	public float[] getMidArea()
		{ return null; }
	
	public void setWidth( float val )
		{ ray = val; }
	
	public void setHeight( float val )
		{ ray = val; }
    
    public void setArea( GameContainer gc )
    	{ ostr = new Circle( ostr.getCenterX(), ostr.getCenterY(), ray ); }

	public float getRotate()
		{ return 0; }

	public void setRotate( float val )
		{}

	public boolean contains( Shape shape )
		{
			if(shape.getY() >= ostr.getY())
				if(shape.getY() + shape.getHeight() <= ostr.getY() + ostr.getHeight())
					if(shape.getX() >= ostr.getX())
						if(shape.getX() + shape.getHeight() <= ostr.getX() + ostr.getWidth())
							return true;
		
			return false;
		}
}