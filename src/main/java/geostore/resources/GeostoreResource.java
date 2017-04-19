package geostore.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by endamccormack on 19/04/2017.
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GeostoreResource {

    @GET
    public String quick() {
        return "Hello";
    }

}
