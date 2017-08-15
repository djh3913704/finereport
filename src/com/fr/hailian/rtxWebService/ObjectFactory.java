
package com.fr.hailian.rtxWebService;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fr.hailian.rtxWebservice package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fr.hailian.rtxWebservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendMessageToUser }
     * 
     */
    public SendMessageToUser createSendMessageToUser() {
        return new SendMessageToUser();
    }

    /**
     * Create an instance of {@link SendMessageToUserResponse }
     * 
     */
    public SendMessageToUserResponse createSendMessageToUserResponse() {
        return new SendMessageToUserResponse();
    }

    /**
     * Create an instance of {@link SendIMMessageToUser }
     * 
     */
    public SendIMMessageToUser createSendIMMessageToUser() {
        return new SendIMMessageToUser();
    }

    /**
     * Create an instance of {@link SendIMMessageToUserResponse }
     * 
     */
    public SendIMMessageToUserResponse createSendIMMessageToUserResponse() {
        return new SendIMMessageToUserResponse();
    }

    /**
     * Create an instance of {@link SendSMSMessageToUser }
     * 
     */
    public SendSMSMessageToUser createSendSMSMessageToUser() {
        return new SendSMSMessageToUser();
    }

    /**
     * Create an instance of {@link SendSMSMessageToUserResponse }
     * 
     */
    public SendSMSMessageToUserResponse createSendSMSMessageToUserResponse() {
        return new SendSMSMessageToUserResponse();
    }

    /**
     * Create an instance of {@link CancelMessage }
     * 
     */
    public CancelMessage createCancelMessage() {
        return new CancelMessage();
    }

    /**
     * Create an instance of {@link CancelMessageResponse }
     * 
     */
    public CancelMessageResponse createCancelMessageResponse() {
        return new CancelMessageResponse();
    }

}
