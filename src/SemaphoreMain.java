import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SemaphoreMain {

	static int numResources;
	static int freeResources;
	static int numProcess;

	static ArrayList<SemaphoreResources> sharedResources = new ArrayList<>();
	static Semaphore available = new Semaphore(1);

	public static void main(String[] args) throws IOException {

		numResources = 150;
		freeResources = numResources;
		numProcess = 40;

		for (int i = 1; i <= numResources; i++) {
			sharedResources.add(new SemaphoreResources(i));
		}
		for (int j = 1; j <= numProcess; j++) {
			new SemaphoreProcess(j);
		}
	}
}
