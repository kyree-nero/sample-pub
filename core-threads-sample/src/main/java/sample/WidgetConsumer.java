package sample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class WidgetConsumer implements Runnable {
	private ArrayBlockingQueue<Widget> widgetQueue;
	private int threshold = 50;
	private String id;
	private AtomicBoolean widgetProducerOpen;
	
	public WidgetConsumer(AtomicBoolean widgetProducerOpen, ArrayBlockingQueue<Widget> widgetQueue, String id) {
		this.id = id;
		this.widgetQueue = widgetQueue;
		this.widgetProducerOpen = widgetProducerOpen;
	}
	
	
	@Override
	public void run() {
		int eaten= 0;
		while(eaten < threshold ) {
			if(widgetProducerOpen.get() == true) {
				boolean foundWidget = false;
				synchronized (widgetQueue) {
					System.out.println("WidgetConsumer "+id+" looking for widgets");
					if(!widgetQueue.isEmpty()) {
						widgetQueue.poll();
						foundWidget = true;
					}
					
					
				}
				if(foundWidget) {
					System.out.println("...consuming");
					eaten ++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}else {
					System.out.println("waiting for widgets");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
		System.out.println("end WidgetConsumer "+id);
	}
}
