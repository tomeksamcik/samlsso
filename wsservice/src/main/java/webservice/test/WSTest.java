package webservice.test;

import javax.ejb.Remote;  
import javax.jws.WebService;  
  
@Remote  
@WebService  
public interface WSTest {  
  
    public String echo(String echo);
    
    public String echoNoAuth(String echo);
    
}  