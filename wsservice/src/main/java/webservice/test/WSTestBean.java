package webservice.test;  
  
import javax.annotation.Resource;  
import javax.annotation.security.RolesAllowed;  
import javax.ejb.Stateless;  
import javax.jws.WebMethod;  
import javax.jws.WebService;  
import javax.xml.ws.WebServiceContext;  
  
import org.jboss.ejb3.annotation.SecurityDomain;  
import org.jboss.ws.api.annotation.EndpointConfig;  
  
@Stateless  
@WebService(
        portName = "WSTestBeanPort",
        serviceName = "WSTestBeanService",
        targetNamespace = "http://test.webservice/")
@EndpointConfig(configFile = "jaxws-endpoint-config.xml", configName = "SAML WSSecurity Endpoint")
@SecurityDomain("sts")  
public class WSTestBean implements WSTest {  
      
    @Resource  
    WebServiceContext wsCtx;  
  
    @WebMethod  
    @RolesAllowed("testRole")  
    public String echo(String echo) {  
        System.out.println("WSTest: " + echo);  
        System.out.println("Principal: " + wsCtx.getUserPrincipal());
        if (wsCtx.getUserPrincipal() != null) {
        	System.out.println("Principal.getName(): " + wsCtx.getUserPrincipal().getName());
        }
        System.out.println("isUserInRole('testRole'): " + wsCtx.isUserInRole("testRole"));
        return "Hello " + echo;
    }
    
    @WebMethod    
    public String echoNoAuth(String echo) {  
        System.out.println("WSTest: " + echo);  
        System.out.println("Principal: " + wsCtx.getUserPrincipal());
        if (wsCtx.getUserPrincipal() != null) {
        	System.out.println("Principal.getName(): " + wsCtx.getUserPrincipal().getName());
        }
        System.out.println("isUserInRole('testRole'): " + wsCtx.isUserInRole("testRole"));
        return "Hello " + echo;
    }      
    
}  