package sample.websvc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import samplewkspc.sample_web_service.Sample;

@Component
public class WebSvcSampleConverter implements Converter<sample.services.domain.Sample, Sample>{
	@Override
	public Sample convert(sample.services.domain.Sample source) {
		if(source == null) {return null; } 
		Sample response = new Sample();
		response.setId(source.getId());
		response.setContent(source.getContent());
		response.setVersion(source.getVersion());
		return response;
	}
	
}
