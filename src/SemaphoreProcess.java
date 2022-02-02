import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreProcess extends Thread {

	int id;
	ArrayList<Integer> used;

	public SemaphoreProcess(int i) {
		this.id = i;
		used = new ArrayList<Integer>();
		start();
	}

	void acquireResource() throws InterruptedException {

		SemaphoreMain.available.acquire();
		int resToAcquire = randomNumber(SemaphoreMain.numResources / 4, 1);

		if (resToAcquire <= SemaphoreMain.freeResources) {

			SemaphoreMain.freeResources = SemaphoreMain.freeResources - resToAcquire;
			int consumed = 0;

			for (int i = 0; i < resToAcquire; i++) {
				if (SemaphoreMain.sharedResources.get(i).acquireSemaphoreResource()) {
					consumed = consumed + 1;
					used.add(SemaphoreMain.sharedResources.get(i).id);
				}
			}
			System.out.println("P" + id + " ha consumido " + resToAcquire + " recursos.");
		} else {
			System.out.println(
					"P" + id + " intenta consumir " + resToAcquire + " recursos, pero no hay tantos disponibles.");
		}

		SemaphoreMain.available.release();
		System.out.println("Recursos disponibles " + SemaphoreMain.freeResources);
	}

	void releaseResource() {

		int resToRelease = randomNumber(used.size() - 1, +1);
		System.out.println("P" + id + " libera " + resToRelease + " recursos.");

		for (int i = resToRelease; i > 0; i--) {

			int indexGet = used.get(0) - 1;
			boolean confirmation = SemaphoreMain.sharedResources.get(indexGet).releaseSemaphoreResource();

			if (confirmation) {
				used.remove(0);
				SemaphoreMain.freeResources = SemaphoreMain.freeResources + 1;
				resToRelease = resToRelease - 1;
			}
		}

		System.out.println("Recursos: " + SemaphoreMain.freeResources);
	}

	@Override
	public void run() {

		while (true) {

			try {
				acquireResource();
			} catch (InterruptedException e) {
				System.out.println("Error en el run() - SemaphoreProcess: " + e.toString());
				e.printStackTrace();
			}

			int t = randomNumber(100, 10);
			try {
				sleep(t);
			} catch (Exception e) {
				System.out.println("Error en el run() - SemaphoreProcess: " + e.toString());
				e.printStackTrace();
			}

			if (used.size() > 0) {
				releaseResource();
			}
		}
	}

	int randomNumber(int n1, int n2) {
		Random rdm = new Random();
		int timing = rdm.nextInt(10) + 100;
		return timing;
	}

}
