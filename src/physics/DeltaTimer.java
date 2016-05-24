package physics;

import java.util.concurrent.TimeUnit;

public class DeltaTimer {
	public static final int FPS = 300;
	
	private static final long NS_PER_FRAME = (long)1e9 / FPS;
	
	public long frameCount = 0;
	
	private long lag = 0; // (nanoseconds)
	private long lastTime;
	
	public DeltaTimer() {
	}
	
	public void startIter() {
	    lastTime = System.nanoTime();
	    frameCount++;
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
					TimeUnit.NANOSECONDS.sleep(left);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
	    } else {
	    	lag += delta_time;
	    }
	}
}
