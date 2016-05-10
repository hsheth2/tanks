package physics;

public class DeltaTimer {
	private final long MS_PER_FRAME = 17;
	
	private long lag = 0; // (ns)
	private long lastTime;
	
	public DeltaTimer() {
		lastTime = System.nanoTime();
	}
	
	public void startIter() {
	    
	}
	
	public void stopIter() {
		// TODO sleep
		long newTime = System.nanoTime();
	    int delta_time = (int) ((newTime - lastTime) / 1000000);
	    lastTime = newTime;
	}
}
