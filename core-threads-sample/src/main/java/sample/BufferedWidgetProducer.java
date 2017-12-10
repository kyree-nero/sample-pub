package sample;

public class BufferedWidgetProducer implements Runnable{
	public BufferedWidgetProducer(WidgetBuffer buffer) {
		this.buffer = buffer;
	}
	private WidgetBuffer buffer;
	private int threshold = 50;
	
	@Override
	public void run() {
		try {
		System.out.println("[PRODUCER] starting shop");
		buffer.setClosed(false);
		int currentCount = 0;
		while(!buffer.isClosed() && currentCount < threshold) {
			System.out.println("[PRODUCER] WidgetProducer looking for widgets");
			buffer.putWidget(new Widget("x"));	
			currentCount++;
			System.out.println("[PRODUCER] widget produced");
		}
		System.out.println("[PRODUCER] finished production...waiting for buffer to clear");
		
		while(!buffer.bufferEmpty()) {
			Thread.sleep(3000);
		}
		System.out.println("closing shop");
		buffer.setClosed(true);
		}catch(InterruptedException i) {
			Thread.currentThread().interrupt();
		}
	}

}
