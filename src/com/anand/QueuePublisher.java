package com.anand;

public interface QueuePublisher {
	
	void publishInsert(QueueElement element);
	void publishRemove();
	void publishRemoveElement(QueueElement element);
	void publishUpdate(QueueElement element);
	
}
