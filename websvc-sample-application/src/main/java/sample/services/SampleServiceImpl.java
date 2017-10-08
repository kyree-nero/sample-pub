package sample.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Sample findSample(Long id) {
		SampleEntry sampleEntry =  sampleEntryRepository.findOne(id);
		Sample sample = new Sample();
		sample.setContent(sampleEntry.getContent());
		sample.setId(sampleEntry.getId());
		sample.setVersion(sampleEntry.getVersion());
		return sample;
		
	}
	
	@Override
	
	public List<Sample> findSamples() {
		
		List<SampleEntry> sampleEntries =  sampleEntryRepository.findAll();
		List<Sample> response = new ArrayList<Sample>();
		for(SampleEntry entry:sampleEntries) {
			Sample sample = new Sample();
			sample.setContent(entry.getContent());
			sample.setId(entry.getId());
			sample.setVersion(entry.getVersion());
			response.add(sample);
		}
		return response;
		
	}
	

	@Override
	@Transactional
	public Sample save(Sample sample) {
		SampleEntry sampleEntry = null;
		if(sample.getId() == null) {
			sampleEntry = new SampleEntry();
			
		}else {
			sampleEntry =  sampleEntryRepository.findByIdAndVersion(sample.getId(), sample.getVersion());
			if(sampleEntry == null) {
				throw new IllegalArgumentException("SampleEntry not found");
			}
		}
		sampleEntry.setContent(sample.getContent());
		sampleEntry = sampleEntryRepository.save(sampleEntry);
		Sample sampleResponse = new Sample();
		sampleResponse.setId(sampleEntry.getId());
		sampleResponse.setContent(sampleEntry.getContent());
		sampleResponse.setVersion(sampleEntry.getVersion());
		return sampleResponse;
	}

	@Override
	@Transactional
	public void remove(Long id) {
		sampleEntryRepository.delete(id);
		
	}
	
	
	
}
