package sample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class WidgetProducer implements Runnable{
	public WidgetProducer(AtomicBoolean widgetProducerOpen, ArrayBlockingQueue<Widget> widgetQueue) {
		this.widgetQueue = widgetQueue;
		this.widgetProducerOpen = widgetProducerOpen;
	}
	private AtomicBoolean widgetProducerOpen;
	private ArrayBlockingQueue<Widget> widgetQueue;
	private int maxBatchesToMake = 2;
	
	
	@Override
	public void run() {
		System.out.println("starting shop");
		int batchesMade = 0;
		while(batchesMade < maxBatchesToMake || !widgetQueue.isEmpty() ) {
			System.out.println("WidgetProducer looking for widgets");
			synchronized(widgetQueue) {
				System.out.println("WidgetProducer approaching widget container");
				if(widgetQueue.size() == 0) {
					int currentCount = 0;
					System.out.println("...making widgets");
					
					while(widgetQueue.remainingCapacity() != 0) {
						widgetQueue.add(new Widget("batch-"+batchesMade+"__widget" + currentCount));
					}
					
					batchesMade++;
				}
			}
			
			System.out.println("tending shop");
			try {
				Thread.sleep(new Double(1000 * Math.random()).longValue());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
		}
		System.out.println("produced " + maxBatchesToMake + " batches ");
		System.out.println("closing shop");
		
		widgetProducerOpen.set(false);
	}

}
