package Utils;

public class Global
{
	// determinano la lunghezza e l'altezza dello schermo di default
	private static final float Width = 800;
	private static final float Height = 600;
	
	// lunghezza e altezza dello schermo attuale
	public static final int W = 800;
	public static final int H = 600;
	
	// numero di frame da eseguire
	public static final int FRAME = 90;
	
	// il rapporto fra altezza e lunghezza attuali con quelli di default
	public static float ratioW, ratioH;
	
	// determina se disegnare il countdown iniziale
	public static boolean drawCountdown;
	
	// numero di vite del personaggio
	public static int lifes = 6;
	
	// determina se il gioco e' in una partita
	public static boolean inGame = false;
	
	// calcola il rapporto fra altezza e lunghezza attuali con quelli di default
	public static void computeRatio( float width, float height )
		{
			ratioW = width/Width;
			ratioH = height/Height;
		}
}