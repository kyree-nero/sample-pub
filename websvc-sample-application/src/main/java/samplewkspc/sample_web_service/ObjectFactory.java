//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.08 at 06:35:21 PM EDT 
//


package samplewkspc.sample_web_service;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the samplewkspc.sample_web_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: samplewkspc.sample_web_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindSampleRequest }
     * 
     */
    public FindSampleRequest createFindSampleRequest() {
        return new FindSampleRequest();
    }

    /**
     * Create an instance of {@link FindSampleResponse }
     * 
     */
    public FindSampleResponse createFindSampleResponse() {
        return new FindSampleResponse();
    }

    /**
     * Create an instance of {@link Sample }
     * 
     */
    public Sample createSample() {
        return new Sample();
    }

}
