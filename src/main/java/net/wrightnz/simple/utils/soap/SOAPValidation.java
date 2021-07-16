package net.wrightnz.simple.utils.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.dom.DOMSource;

public final class SOAPValidation {

    public static void validate(String xmlResponse) throws ParserConfigurationException, IOException, SAXException, SOAPException {
        InputStream is = new ByteArrayInputStream(xmlResponse.getBytes());
        //convert inputStream to Source
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document document = builder.parse(is);
        DOMSource domSource = new DOMSource(document);

        //create new SOAPMessage instance
        MessageFactory mf = MessageFactory.newInstance(javax.xml.soap.SOAPConstants.SOAP_1_1_PROTOCOL);
        SOAPMessage message = mf.createMessage();
        SOAPPart part = message.getSOAPPart();

        //Set the xml in SOAPPart
        part.setContent(domSource);
        message.saveChanges();

        // Access the body
        SOAPBody body = message.getSOAPPart().getEnvelope().getBody();

        // For you to access the values inside body, you need to access the node
        // childs following the structures. example
        body.getFirstChild();
        QName bodyQName = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body");
        assertEquals(bodyQName, body.getElementQName());
    }

}
