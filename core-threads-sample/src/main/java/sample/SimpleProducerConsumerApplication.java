package sample;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SimpleProducerConsumerApplication {

	
	
	public static void main(String[] args) {
		new SimpleProducerConsumerApplication().run();
		
	}
	public void run() {
		AtomicBoolean widgetProducerOpen = new AtomicBoolean(true);
		ArrayBlockingQueue<Widget> widgetQueue = new ArrayBlockingQueue<Widget>(10);
		
		List<WidgetConsumer> consumers = 
				Arrays.asList("A", "B", "C", "D").stream().
				map(s -> new WidgetConsumer(widgetProducerOpen, widgetQueue, s)).
				collect(Collectors.toList());
		
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		executor.execute(new WidgetProducer(widgetProducerOpen, widgetQueue));
		
		consumers.stream().forEach(e -> executor.execute(e));
		
		executor.shutdown();
		
		
	}
	
	
	

}
