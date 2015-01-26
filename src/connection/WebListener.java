package connection;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("WebListener")
public class WebListener {
	@GET
	@Path("/generate")
	@Produces(MediaType.TEXT_PLAIN)
	public String generateBusinessRule() {
		WebController webController = new WebController();;
		return webController.generateBusinessRules();
	}
}