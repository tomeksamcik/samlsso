package webservice.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.picketlink.identity.federation.api.wstrust.WSTrustClient;
import org.picketlink.identity.federation.api.wstrust.WSTrustClient.SecurityInfo;
import org.picketlink.identity.federation.core.wstrust.plugins.saml.SAMLUtil;
import org.picketlink.trust.jbossws.SAML2Constants;
import org.picketlink.trust.jbossws.handler.SAML2Handler;
import org.w3c.dom.Element;

public class Run {
	
    //private static String username = "tomcat";  
    //private static String password = "tomcat";
    
	private static String username = "UserA";
    private static String password = "PassA";
    
    public static void printAssertion(Element assertion) throws Exception  {  
       TransformerFactory tranFactory = TransformerFactory.newInstance();  
       Transformer aTransformer = tranFactory.newTransformer();  
       Source src = new DOMSource(assertion);  
       Result dest = new StreamResult(System.out);  
       aTransformer.transform(src, dest);  
    }      

	public static void main(String[] args) throws Exception {
        WSTrustClient client = new WSTrustClient("PicketLinkSTS", "PicketLinkSTSPort",  
                "http://vps50444.ovh.net:8080/picketlink-sts/PicketLinkSTS",  
                new SecurityInfo(username, password));  
        System.out.println("Invoking token service to get SAML assertion for " + username);  
        final Element assertion = client.issueToken(SAMLUtil.SAML2_TOKEN_TYPE);
        System.out.println("SAML assertion for " + username + " successfully obtained!");
        Run.printAssertion(assertion);
        System.out.println("Is assertion valid? " + client.validateToken(assertion));  
		
		WSTestBeanService service = new WSTestBeanService();
		WSTest c = service.getWSTestBeanPort();
		((BindingProvider) c).getRequestContext().put(SAML2Constants.SAML2_ASSERTION_PROPERTY, assertion);
		
		SOAPHandler sh = new SOAPHandler();
		SAML2Handler saml = new org.picketlink.trust.jbossws.handler.SAML2Handler();
		List<Handler> handlerChain = new ArrayList<Handler>();
		handlerChain.add(saml);
		handlerChain.add(sh);
		((BindingProvider) c).getBinding().setHandlerChain(handlerChain);
		 
		System.out.println(c.echoNoAuth("no auth test"));
		System.out.println(c.echo("secure test"));	
	}

}
