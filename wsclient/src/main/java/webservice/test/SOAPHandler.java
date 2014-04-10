package webservice.test;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.interceptor.Fault;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class SOAPHandler implements
	javax.xml.ws.handler.soap.SOAPHandler<SOAPMessageContext> {
	
	@Override
	public Set<QName> getHeaders() {
	return null;
	}
	
	@Override
	public void close(MessageContext mc) {
	}
	
	@Override
	public boolean handleFault(SOAPMessageContext mc) {
	return true;
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext mc) {
        try {
            Boolean outboundProperty = (Boolean)mc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            //if (!outboundProperty.booleanValue()) {
                // change mustUnderstand to false
                SOAPMessage message = mc.getMessage();
                SOAPHeader soapHeader = message.getSOAPHeader();
                Element headerElementNew = (Element)soapHeader.getFirstChild();
                
                SoapVersion soapVersion = Soap11.getInstance();                        
                Attr attr = 
                    headerElementNew.getOwnerDocument().createAttributeNS(soapVersion.getNamespace(), 
                                                                          "soap:mustUnderstand"); 
                attr.setValue("0");
                headerElementNew.setAttributeNodeNS(attr);          
            //}
        } catch (Exception e) {
            throw new Fault(e);
        }		
	
        return true;
	}
}