package sample.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.persistence.dao.SampleDao;
import sample.persistence.entities.SampleEntry;
import sample.persistence.repositories.SampleEntryRepository;
import sample.services.domain.Sample;

@Service
public class SampleServiceImpl implements SampleService {

	@Autowired SampleDao dao;
	@Autowired SampleEntryRepository sampleEntryRepository;
	
	@Override
	public void doStuff() {
		
	}

	@Override
	public Long findCountInDb() {
		return dao.findSampleCount();
	}

	@Override
	public Long findCountInDb2() {
		return sampleEntryRepository.count();
	}

	@Override
	@Transactional()
	public Sample findSample(Long id) {
		
		SampleEntry sampleEntry =  sampleEntryRepository.getOne(id);
		Sample sample = new Sample();
		sample.setContent(sampleEntry.getContent());
		sample.setId(sampleEntry.getId());
		return sample;
		
	}
	
	

}
