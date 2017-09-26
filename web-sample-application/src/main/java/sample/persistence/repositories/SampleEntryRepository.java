package sample.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.persistence.entities.SampleEntry;

public interface SampleEntryRepository extends JpaRepository<SampleEntry, Long>{
	public SampleEntry findByIdAndVersion(Long id, Long version);
}
