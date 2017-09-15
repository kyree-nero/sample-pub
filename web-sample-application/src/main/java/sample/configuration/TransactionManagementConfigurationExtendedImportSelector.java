package sample.configuration;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class TransactionManagementConfigurationExtendedImportSelector implements ImportSelector{

	@Override
	public String[] selectImports(AnnotationMetadata arg0) {
		return new String[] {"sample.configuration.TestTransactionManagementConfiguration"};
	}

}
