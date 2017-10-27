package sample.services;

import java.util.List;

import sample.services.domain.Sample;

public interface SampleService {
	public void doStuff();
	public Long findCountInDb();
	public Long findCountInDb2();
	public List<Sample> findSamples() ;
	public Sample findSample(Long id);
	public Sample save(Sample sample);
	public void remove(Long id);
}

