package chapter16;

import java.lang.Thread;

public class Philosophers extends Thread{
	
	
	int id;
	PhilosopherFork fork;
	
	public Philosophers(int id, PhilosopherFork fork){
		this.id = id;
		this.fork = fork;
	}
	
	public void run(){
		
		while(true){
			
			thinking();
			fork.takeForks(id);
			eating();
			fork.putForks(id);
			
		}
		
	}
	
	public void thinking(){
		System.out.println("philosopher " + id + " is thinking");
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void eating(){
		System.out.println("philosopher " + id + " is eating");
		
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] argv){
		
		PhilosopherFork fork = new PhilosopherFork(5);
		new Philosophers(0, fork).start();
		new Philosophers(1, fork).start();
		new Philosophers(2, fork).start();
		new Philosophers(3, fork).start();
		new Philosophers(4, fork).start();
		
	}



}
