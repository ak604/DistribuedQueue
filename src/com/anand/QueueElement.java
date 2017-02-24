package com.anand;

public class QueueElement<T> {
	long ttlInSeconds;
	long inTime;
	int id;
	
	T data;

	public QueueElement(long ttlInSeconds, long inTime, int id){
		this.ttlInSeconds=ttlInSeconds;
		this.inTime=inTime;
		this.id=id;
	}
	@Override
	public boolean equals(Object obj) {
		return this.id==((QueueElement)obj).id;
	}

	
}
