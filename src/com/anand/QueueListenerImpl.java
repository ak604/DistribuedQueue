package com.anand;

public class QueueListenerImpl implements  QueueListener{

	DistribuedQueue distQueue;
	
	public QueueListenerImpl(DistribuedQueue distQueue){
		this.distQueue= distQueue;
	}
	@Override
	public void onInsert(QueueElement element) {
		distQueue.insert(element,true);
	}

	@Override
	public void onRemove() {
		distQueue.remove(true);		
	}

	@Override
	public void onRemoveElement(QueueElement element) {
		distQueue.removeElement(element,true);
	}

	@Override
	public void onUpdate(QueueElement element) {
		distQueue.update(element,true);
		
	}
}
