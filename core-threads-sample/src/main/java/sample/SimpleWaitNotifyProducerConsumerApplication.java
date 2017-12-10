package sample;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SimpleWaitNotifyProducerConsumerApplication {
	public static void main(String[] args) {
		new SimpleWaitNotifyProducerConsumerApplication().run(30, TimeUnit.SECONDS, 50);
		
	}
	public Integer run(long timeout, TimeUnit unit, Integer amountToProduce) {
		
		System.out.println(("amountToProduce:"+amountToProduce+""));
		WidgetBuffer buffer = new WidgetBuffer(10);
		
		List<BufferedWidgetConsumer> consumers = 
				Arrays.asList("A", "B", "C", "D").stream().
				map(s -> new BufferedWidgetConsumer(buffer, s)).
				collect(Collectors.toList());
		
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		executor.execute(new BufferedWidgetProducer(buffer, amountToProduce ));
		
		consumers.stream().forEach(e -> executor.execute(e));
		
		try {
			executor.awaitTermination(timeout, unit);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Integer amountConsumed = consumers
				.stream()
				.collect(Collectors.summingInt(BufferedWidgetConsumer::getConsumed));
		System.out.println(("amountConsumed:"+amountConsumed+""));
		return amountConsumed;
	}
}
