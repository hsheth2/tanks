package physics;

public class DeltaTimer {
	public static DeltaTimer t = new DeltaTimer();
	public static final int FPS = 60;
	
	private static final long NS_PER_FRAME = (long)1e9 / FPS;
	
	private long lag = 0; // (nanoseconds)
	private long lastTime;
	
	private DeltaTimer() {
		lastTime = System.nanoTime();
	}
	
	public void startIter() {
	    lastTime = System.nanoTime();
	}
	
	public void stopIter() {
		long newTime = System.nanoTime();
	    long delta_time = newTime - lastTime;
	    if (delta_time < NS_PER_FRAME) {
	    	long left = NS_PER_FRAME - delta_time;
			if (lag > 0) {
				if (lag > left) {
					lag -= left;
					left = 0;
				} else {
					left -= lag;
					lag = 0;
				}
			}
			
			if (left > 0) {
				try {
					Thread.sleep(0L, (int)left);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
	    } else {
	    	lag += delta_time;
	    }
	}
}
