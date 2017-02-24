package com.anand;

public class Other {

	public static DistribuedQueue getQueue(){
		return null;
		
	}

	public static void main(String args[]){		
		DistribuedQueue distribuedQueue= new DistribuedQueue(new QueuePublisherImpl());
		distribuedQueue.clone(distribuedQueue);
	}
}
