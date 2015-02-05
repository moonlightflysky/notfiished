package chapter16;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherFork {
	
	private int[] state;
	private Semaphore[] s;
	private Lock lock;
	int N;
	
	private int THINKING = 0;
	private int HUNGRY = 1;
	private int EATING = 2;
	
	public PhilosopherFork(int n){
		this.N = n;
		state = new int[N];
		s = new Semaphore[N];
		for (int i = 0; i < N; i++){
			s[i] = new Semaphore(0);
		}
		lock = new ReentrantLock();
	}
	
	public void takeForks(int i){
		lock.lock();
		state[i] = HUNGRY;
		test_take_left_right_forks(i);
		
		lock.unlock();
		try {
			s[i].acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void test_take_left_right_forks(int i){
		
		if (state[i] == HUNGRY && state[i] != EATING && state[(i + 1) % N] != EATING){
			state[i] = EATING;
			System.out.println("philosopher " + i + " get forks ");
			s[i].release();
		}
		
		
	}
	
	public void putForks(int i){
		lock.lock();
		System.out.println("philosopher " + i + " put forks back");
		state[i] = THINKING;
		test_take_left_right_forks(i);
		test_take_left_right_forks((i + 1) % N);
		lock.unlock();
		
	}
	
}
