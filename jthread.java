/*
Adam Castillo
Csci 117 lab. 
The purpose of this lab is to experiment with java threads. The threads must work 
concurrently while sharing the resources.  
*/

// first shareable resource 
class SR1{
	//  static means this is tthe only instance of this variable 
	private static int count = 3;  
	// synchronized methods provided mutual exclusion
	public synchronized void acquire(int id){
	  if(count == 0){
		try{
		wait(); //  if no items are available to use then the thread must wait 
		}
		catch(InterruptedException ex){
			// exeption caught 
		}
	  }
	  count = count - 1; //  decrease account when object acquired
	} 
	
	public synchronized void release(int id){
		count = count + 1; //  increment count when object is released 
		if (count >= 1) // notify threads when there is one or more objects available 
			notify(); 	
}
}
// shareable resource 2 same documentation from above 
class SR2{
	private static int count2 = 2; 
	public synchronized void acquire(int id){
	  if(count2 == 0){
		  	try{
		wait();
		}
		catch(InterruptedException ex){
		
		}
	  }
		  
	  count2 = count2 - 1; 
	} 
	public synchronized void release(int id){
		count2 = count2 + 1;
		if (count2 >= 1) 
			notify();
	}
}

// this class extends the thread class, when we create an instance of this class it is a thread 

class Process extends Thread{
	//  we want these to be static because we are takinng static objects in the constructor 
	 static SR1 r1;
	 static SR2 r2; 
	 private int id; //  thread pid 
	 
	public Process(int id, SR1 r1, SR2 r2){
		this.r1 = r1; //  use this to differentiate between class variable and argument 
		this.r2 = r2; 
		this.id = id; 
	}
	
	public void run(){
		try{
		Thread.sleep(3000);  // thread sleeps for 3000 nanoseconds 
		r1.acquire(id);   // tries to acquire first lock ,may wait
		System.out.println("Process_"+ id +" acquires SR" + 1);
		Thread.sleep(3000);
		r2.acquire(id); // if first lock acquired then attempt to acquirte second lock 
		System.out.println("Process_" + id + " acquires SR" +2);
		}
		 catch(InterruptedException ex){
			 //exception caught 
		 }
		System.out.println("Process_" + id + " is working"); 
		
		try{

		sleep(3000);
		r2.release(id); //  release second lock 
		System.out.println("Process_" + id + " releases SR" + 2);
		sleep(3000);
		r1.release(id); //  release first lock 
		System.out.println("Process_"+ id + " releases SR" +1);
		
		}
		catch(InterruptedException ex){
			  //  exception caught 
		 }
	}
}
//manager class
// responsible for creating and managing threads 
public class jthread{
	//  private to this class 
	// only one instance of these varible,  memory address doesnt change 
	static SR1 r11 = new SR1();
	static SR1 r12 = new SR1();
	static SR1 r13 = new SR1();
	static SR2 r21 = new SR2();
	static SR2 r22 = new SR2();
	
	public static void main(String args[]){
		// creating threads 
		Process thread1 = new Process(1,r11,r21);
		System.out.print("===== Thread for process_" + 1 +" created"+"\n");
		Process thread2 = new Process(2,r12,r22);
		System.out.print("===== Thread for process_" + 2 +" created"+"\n");
		Process thread3 = new Process(3,r13,r21);
		System.out.print("===== Thread for process_"+ 3 + " created"+"\n");
		Process thread4 = new Process(4,r11,r22);
		System.out.print("===== Thread for process_" + 4 +" created"+"\n");
		Process thread5 = new Process(5,r12,r21);
		System.out.print("===== Thread for process_"+ 5 + " created"+"\n");
		Process thread6 = new Process(6,r13,r22);
		System.out.print("===== Thread for process_" + 6 +" created"+"\n");
		// starting threads 
		thread1.start(); 
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start(); 
	}
}