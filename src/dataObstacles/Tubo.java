package dataObstacles;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Utils.Global;
import bubbleMaster.Start;

public class Tubo extends Ostacolo{

    Image immagine;
	Image tubosx = new Image( "./data/Image/tuboSx.png" ), tubodx = new Image( "./data/Image/tuboDx.png" );
	Image tuboup = new Image( "./data/Image/tuboUp.png" ), tubodown = new Image( "./data/Image/tuboDown.png" );    

    private Color cg = new Color( 50, 170, 50, 150 ), cr = new Color( 170, 50, 50, 150 );
    
    //insert = false -> oggetto rosso | insert = true -> oggetto verde
    private boolean insert = false, checkInsert = false;
	
    //lunghezza e altezza del tubo
	float width, height;
	
	//determina il tipo di direzione del tubo
	String type = null;
	
	public Rectangle ostr;
	
	/*sottodivisione del rettangolo in lati e spigoli*/
	public Rectangle latoSu, latoGiu, latoDx, latoSx;
	public Rectangle spigASx, spigADx, spigBSx, spigBDx;
	
	private boolean collide;
	
	//indice del tubo a cui e' collegato
	private int unione;
	
	private Base base;
	private Enter enter;
	
	private int indexBase, indexEnter;
	
	private static final String ID = "tubo";
	
	public Tubo( int x, int y, String type, GameContainer gc ) throws SlickException
	{
		super( ID );
		
		this.type = type;
		
		if(type.equals( "sx" ))
			immagine = tubosx;
		else if(type.equals( "dx" ))
			immagine = tubodx;
		else if(type.equals( "down" ))
			immagine = tubodown;
		else
			immagine = tuboup;

		collide = false;
		
		unione = -1;
		
		if(type.equals( "sx" ) || type.equals( "dx" ))
			{
				width = gc.getWidth()/10;
				height = gc.getHeight()/10;
			}
		else
			{
				width = gc.getHeight()/10;
				height = gc.getWidth()/10;
			}
		
		ostr = new Rectangle( x, y, width, height );
	}

	public void draw( Graphics g ) throws SlickException
		{
            immagine.draw( ostr.getX(), ostr.getY(), width, height );
            if(Start.editGame == 1)
                if(checkInsert)
                    if(!insert)
                        immagine.draw( ostr.getX(), ostr.getY(), width, height, cr);
                    else
                        immagine.draw( ostr.getX(), ostr.getY(), width, height, cg);
		}
	
	public int getIndexBase()
		{ return indexBase; }
	
	public void setIndexBase( int val )
		{ indexBase = val; }
	
	public int getIndexEnter()
		{ return indexEnter; }
	
	public void setIndexEnter( int val )
		{ indexEnter = val; }
	
	public void deleteAreas()
		{
			base = null;
			enter = null;
		}
	
	public void setArea( GameContainer gc )
		{}
    
    public void setSpace( GameContainer gc ) throws SlickException
    	{
    		ostr = new Rectangle( getX(), getY(), width, height );
    		if(type.equals( "dx" ))
				{
					base = new Base( getX(), getY() + gc.getWidth()/160, gc.getWidth()*10/119, height - gc.getWidth()/80 );
					enter = new Enter( base.getMaxX(), getY(), width - base.getWidth(), height );
				}
			else if(type.equals( "sx" ))
				{
					enter = new Enter( getX(), getY(), width - gc.getWidth()*10/119, height );
					base = new Base( enter.getMaxX(), getY() + gc.getWidth()/160, gc.getWidth()*10/119, height - gc.getWidth()/80 );
				}
    		else if(type.equals( "up" ))
				{
					enter = new Enter( getX(), getY(), width, height - gc.getWidth()*10/119 );
					base = new Base( getX() + gc.getWidth()/160, enter.getMaxY(), width - gc.getWidth()/80, height - enter.getHeight() );
				}
			else
				{
					base = new Base( getX() + gc.getWidth()/160, getY(), width - gc.getWidth()/80, gc.getWidth()*10/119 );
					enter = new Enter( getX(), base.getMaxY(), width, height - base.getHeight() );
				}
    		base.setSpigoli();
    		enter.setSpigoli();
    		setSpigoli();
    	}
    
    public void updateStats( GameContainer gc )
    	{}
    
    public void updateValues( GameContainer gc ) throws SlickException
    	{
    		width = width * Global.ratioW;
    		height = height * Global.ratioH; 
    		setXY( getX() * Global.ratioW, getY() * Global.ratioH, "restore" );

    		ostr = new Rectangle( getX(), getY(), width, height );
    		if(type.equals( "dx" ))
				{
					base = new Base( getX(), getY() + gc.getWidth()/160, gc.getWidth()*10/119, height - gc.getWidth()/80 );
					enter = new Enter( base.getMaxX(), getY(), width - base.getWidth(), height );
				}
			else if(type.equals( "sx" ))
				{
					enter = new Enter( getX(), getY(), width - gc.getWidth()*10/119, height );
					base = new Base( enter.getMaxX(), getY() + gc.getWidth()/160, gc.getWidth()*10/119, height - gc.getWidth()/80 );
				}
			else if(type.equals( "up" ))
				{
					enter = new Enter( getX(), getY(), width, height - gc.getWidth()*10/119 );
					base = new Base( getX() + gc.getWidth()/160, enter.getMaxY(), width - gc.getWidth()/80, height - enter.getHeight() );
				}
			else
				{
					base = new Base( getX() + gc.getWidth()/160, getY(), width - gc.getWidth()/80, gc.getWidth()*10/119 );
					enter = new Enter( getX(), base.getMaxY(), width, height - base.getHeight() );
				}
    		setSpigoli();
    	}
	
	public void setType( String type )
		{ this.type = type; }

	public float getX()
		{ return (int)ostr.getX(); }

	public float getY()
		{ return ostr.getY(); }
	
	public float getWidth()
		{ return ostr.getWidth(); }
	
	public float getHeight()
		{ return ostr.getHeight(); }
	
	public boolean contains( int x, int y )
		{ return ostr.contains( x, y ); }
	
	public boolean getInsert()
		{ return insert; }

    public void setInsert(boolean insert, boolean change)
        {
            checkInsert = !change;
            this.insert = insert;
        }
	
	public void setXY( float x, float y, String function )
		{ 
			if(function.compareTo( "move" ) == 0)
				ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
			
			else if(function.compareTo( "restore" ) == 0)
				ostr.setLocation( x, y );
		}
	
	public Ostacolo getBase()
		{ return base; }
	
	public Ostacolo getEnter()
		{ return enter; }
	
	public String getDirection()
		{ return type; }
	
	public Rectangle component( String part )
		{
			if(part.equals( "latoSu" ))
				return latoSu;
			else if(part.equals( "latoGiu" ))
				return latoGiu;
			else if(part.equals( "latoSx" ))
				return latoSx;
			else if(part.equals( "latoDx" ))
				return latoDx;
			else if(part.equals( "spigASx" ))
				return spigASx;
			else if(part.equals( "spigADx" ))
				return spigADx;
			else if(part.equals( "spigBSx" ))
				return spigBSx;
			else if(part.equals( "spigBDx" ))
				return spigBDx;
			else if(part.equals( "rect" ))
				return ostr;
			else if(part.equals( "latoIngresso" ))
				if(type.equals( "sx" ))
					return latoSx;
				else if(type.equals( "dx" ))
					return latoDx;
				else if(type.equals( "up" ))
					return latoSu;
				else if(type.equals( "down" ))
					return latoGiu;
			
			return null;
		}

	public float getMaxX()
		{ return ostr.getMaxX(); }

	public void update(GameContainer gc) throws SlickException 
		{}
	
	public void setMaxHeight( double val )
		{}
	
	public double getMaxHeight()
		{ return 0; }
	
	public Shape getArea()
		{ return ostr; }

	public Ostacolo clone( GameContainer gc ) {		
		try {
			return new Tubo( (int) getX(), (int) getY(), type, gc );
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setCollided( boolean val )
		{}
	
	public boolean isCollided()
		{ return true; }
	
	public boolean getCollide()
		{ return collide; }

	public void setCollide( boolean val )
		{ collide = val; }

	public void update(GameContainer gc, int delta) throws SlickException 
		{}

	public double getMaxWidth()
		{ return 0; }

	private void modificaArea( int type, GameContainer gc ) throws SlickException
	    {
			float tmp;
		
	        //se la modifica e' da orizzontale a verticale
	        if(type == 1)
    	        {
	        		tmp = width;
	                width = height;
	                height = tmp;
	                
                    setSpace( gc );
    	        }
	        //se la modifica e' da verticale a orizzontale
	        else
    	        {
		        	tmp = width;
	                width = height;
	                height = tmp;
	                
                    setSpace( gc );
    	        }
	    }
	
	public void setOrienting( GameContainer gc ) throws SlickException
        {
	        if(type.equals( "sx" ))
	            {
	                type = "up";
	                immagine = tuboup;
	                modificaArea( 1, gc );
	            }
	        else if(type.equals( "up" ))
	            {
	                type = "dx";
	                immagine = tubodx;
	                modificaArea( 2, gc );
	            }
	        else if(type.equals( "dx" ))
	            {
	                type = "down";
	                immagine = tubodown;
	                modificaArea( 1, gc );
	            }
	        else
	            {
	                type = "sx";
	                immagine = tubosx;
	                modificaArea( 2, gc );
	            }
        }

	public String getOrienting()
        { return type; }

	public void setSpigoli()
        {
            /*creazione lati*/
            latoSu = new Rectangle( ostr.getX() + 1, ostr.getY(), ostr.getWidth() - 2, 1 );
            latoGiu = new Rectangle( ostr.getX() + 1, ostr.getY() + ostr.getHeight() - 1, ostr.getWidth() - 2, 1 );
            latoSx = new Rectangle( ostr.getX(), ostr.getY() + 1, 1, ostr.getHeight() - 2 );
            latoDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + 1, 1, ostr.getHeight() - 2 );
            
            /*creazione spigoli*/
            spigASx = new Rectangle( ostr.getX(), ostr.getY(), 1, 1 );
            spigADx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY(), 1, 1 );
            spigBSx = new Rectangle( ostr.getX(), ostr.getY() + ostr.getHeight() - 1, 1, 1 );
            spigBDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + ostr.getHeight() - 1, 1, 1 );
        }
	
	public void setUnion( int val )
		{ unione = val; }
	
	public int getUnion()
		{ return unione; }
	
	//calcola il punto a meta' dell'oggetto per generare la linea che unisce i tubi connessi
	public Point getMidArea()
		{ return new Point( getX() + width/2, getY() + height/2 ); }
	
	public void setWidth( float val )
		{ width = val; }
	
	public void setHeight( float val )
		{ height = val; }
}