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
		while(widgetProducerOpen.get() == true && eaten < threshold ) {
			
				boolean foundWidget = false;
				System.out.println("WidgetConsumer "+id+" looking for widgets");
				synchronized (widgetQueue) {
					System.out.println("WidgetConsumer "+id+" approaching widget container");
					if(!widgetQueue.isEmpty()) {
						widgetQueue.poll();
						foundWidget = true;
					}
				}
				if(foundWidget) {
					System.out.println("...consuming");
					eaten ++;
					try {
						Thread.sleep(new Double(1000 * Math.random()).longValue());
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					System.out.println("...consumed");
				}else {
					System.out.println("waiting for widgets");
					try {
						Thread.sleep(new Double(1000 * Math.random()).longValue());
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					System.out.println("done waiting for widgets");
				}
			
		}
		System.out.println("end WidgetConsumer "+id);
	}
}
