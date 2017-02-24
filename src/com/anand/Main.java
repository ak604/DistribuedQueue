package com.anand;

public class Main {

	public static void main(String args[]){		
		DistribuedQueue queue= new DistribuedQueue(new QueuePublisherImpl());
		queue.initialize();
	}
}
