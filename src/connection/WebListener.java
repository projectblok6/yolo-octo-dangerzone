package connection;

/**
* @author Arpit Mandliya
*/
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("WebListener")
public class WebListener  {
     @GET
     @Path("/generate")
      public void generateBusinessRule() {
    	 System.out.println("test");
      }
}
