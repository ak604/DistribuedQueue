package com.anand;

public class Client {
	
	public static DistribuedQueue getDistribuedQueue(){
		DistribuedQueue queue= new DistribuedQueue(new QueuePublisherImpl());
		queue.initialize();
		return queue;
		
	}
	
	public static void main(String args[]){		
		DistribuedQueue queue= Client.getDistribuedQueue();
		queue.insert(new QueueElement(100,Utility.getCurrentTimeInSeconds(), 1));
	}
	
}
