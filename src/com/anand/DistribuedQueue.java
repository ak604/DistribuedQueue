package com.anand;

public class DistribuedQueue implements AckListener{

	public static int MAX_LIMIT=1000000;
	QueuePublisher publishser;
	
	public DistribuedQueue(QueuePublisher publishser){
		this.publishser=publishser;
	}
	
	private QueueElement[] queue;
	public int front, back;
	public int count;

	
	public Object getLock(){
		return this; // make server call to fetch global lock
	}

	void sendForApproval(QueueElement obj){
	}
	
	void registerListener(QueueListener queueListener){
	}
	
	void clone(DistribuedQueue distribuedQueue){
		initialize(distribuedQueue.queue,distribuedQueue.count,distribuedQueue.back,distribuedQueue.front,false);
	}
	
	void initialize(){
		this.initialize(null, 0, 0, 0, true);
	}
	void initialize( QueueElement[] queue, int count, int front, int back,boolean runTTLThread){
		registerListener(new QueueListenerImpl(this));
		synchronized (getLock()) {	
			this.queue = new QueueElement[MAX_LIMIT];
			if(queue!=null){
				for(int i=0;i<count;i++){
					int index = (back+i)%MAX_LIMIT;
					this.queue[index]=queue[index];
				}
			}
			this.count=count;;
			this.front=front;
			this.back=back;
			
		}
		if(runTTLThread){
			Thread thread =new Thread(new Runnable() {

				@Override
				public void run() {
					long currentTime = Utility.getCurrentTimeInSeconds();
					synchronized (getLock()) {
						for(int i=0;i<count;i++){
							int index = (back+i)%MAX_LIMIT;
							if(queue[index].ttlInSeconds+queue[index].inTime >currentTime)
								sendForApproval(queue[index]);
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}
	}
	
	void insert(QueueElement obj){
		insert(obj,false);
	}
	
	void insert(QueueElement obj,boolean local){
		synchronized (getLock()) {
			if(count==MAX_LIMIT){
				throw new RuntimeException("Queue Full : insert failed");
			}
			queue[front]=obj;
			front=(front+1)%MAX_LIMIT;
			count++;
			if(!local){
				publishser.publishInsert(obj);
			}
		}
	}
	
	Object remove(){
		return remove(false);
	}
	Object remove(boolean local){
		synchronized (getLock()) {
			if(count==0){
				throw new RuntimeException("Queue is empty , remove failed");
			}
			Object obj = queue[back];
			back=(back+1)%MAX_LIMIT;
			count--;
			publishser.publishRemove();
			return obj;
		}
	}
	
	void update(QueueElement obj){
		update(obj,false);
	}
	void update(QueueElement obj,boolean local){	
		synchronized (getLock()) {
			for(int i=0;i<count;i++){
				int index = (back+i)%MAX_LIMIT;
				if(obj.equals(queue[index]))
					queue[index]=obj;
			}
			publishser.publishUpdate(obj);
		}
	}

	void removeElement(QueueElement obj){
		removeElement(obj, false);
	}

	void removeElement(QueueElement obj,boolean local){
		
	}
	@Override
	public void onAck(QueueElement obj) {
		removeElement(obj);
	}
	
}
