package controller_view;

public class Multi extends Thread {
	public static void main(String[] args) {
		Thread thread = new Multi();
		thread.start(); //start is a Thread method
	}
	@Override
	public void run() {
		// This run method is called from Thread.start
		System.out.println("This code runs in a new Thread");
	}
}
