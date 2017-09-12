package sample.services;

import sample.services.domain.Sample;

public interface SampleService {
	public void doStuff();
	public Long findCountInDb();
	public Long findCountInDb2();
	public Sample findSample(Long id);
}
