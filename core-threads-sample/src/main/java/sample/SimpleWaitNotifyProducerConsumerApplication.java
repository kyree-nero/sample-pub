package sample;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SimpleWaitNotifyProducerConsumerApplication {
	public static void main(String[] args) {
		new SimpleWaitNotifyProducerConsumerApplication().run();
		
	}
	public Integer run() {
		WidgetBuffer buffer = new WidgetBuffer(10);
		
		List<BufferedWidgetConsumer> consumers = 
				Arrays.asList("A", "B", "C", "D").stream().
				map(s -> new BufferedWidgetConsumer(buffer, s)).
				collect(Collectors.toList());
		
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		executor.execute(new BufferedWidgetProducer(buffer));
		
		consumers.stream().forEach(e -> executor.execute(e));
		
		executor.shutdown();
		
		return consumers
				.stream()
				.collect(Collectors.summingInt(BufferedWidgetConsumer::getConsumed));
	}
}
