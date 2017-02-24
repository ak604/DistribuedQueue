package com.anand;

public interface QueueListener {

	void onInsert(QueueElement element);
	void onRemove();
	void onRemoveElement(QueueElement element);
	void onUpdate(QueueElement element);
}
