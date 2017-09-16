package sample.persistence.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "SAMPLE")

public class SampleEntry {
	@Id @Column(name="ID")  @GeneratedValue(generator="seq_sample")
	@SequenceGenerator(name="seq_sample",sequenceName="SEQ_SAMPLE", allocationSize=1) private Long id;
	@Column(name="CONTENT") String content;
	@Column(name="VERSION") @Version Long version;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
}
