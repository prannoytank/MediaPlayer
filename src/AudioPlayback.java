import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;


public enum AudioPlayback {
	INSTANCE;
	private AdvancedPlayer ap;
	
	public void setAdvancedPlayer(AdvancedPlayer ap)
	{
		this.ap = ap;
	}
	
	public AdvancedPlayer getAdvancedPlayer()
	{
		return this.ap;
	}
	
	public void play()
	{
		try {
			this.ap.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		this.ap.stop();
	}
}
