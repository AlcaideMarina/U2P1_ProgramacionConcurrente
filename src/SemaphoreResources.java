import java.util.concurrent.Semaphore;

public class SemaphoreResources {

	Semaphore state;
	int id;
	
	public SemaphoreResources(int i) {
		this.state = new Semaphore(1);
		this.id = i;
	}
	
	boolean releaseSemaphoreResource() {
		
		try {
			state.release();
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	boolean acquireSemaphoreResource() {
		return state.tryAcquire();
	}
	
}
