package geostore.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import geostore.dao.GeostoreDao;
import geostore.model.GeostoreItem;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by endamccormack on 19/04/2017.
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GeostoreResource {

    private final GeostoreDao geostoreDao;
    ObjectMapper mapper = new ObjectMapper();
    private final static Logger LOGGER = Logger.getLogger(GeostoreResource.class.getName());

    public GeostoreResource(GeostoreDao geostoreDao) {
        this.geostoreDao = geostoreDao;
    }

    @GET
    @Path("/prisons-within-local-authorities")
    @Produces(MediaType.APPLICATION_JSON)
    public String quick() throws IOException {
        JSONObject json = new JSONObject(IOUtils.toString(new URL("https://prison.discovery.openregister.org/records.json"), Charset.forName("UTF-8")));

        Iterator<?> keys = json.keys();

        List<JSONObject> prisons = new ArrayList<>();

        while (keys.hasNext()) {
            String key = (String)keys.next();
            JSONObject prison = json.getJSONObject(key);

            try {
                String addressId = prison.getJSONArray("item").getJSONObject(0).getString("address");

                JSONObject address = new JSONObject(IOUtils.toString(new URL("https://address.discovery.openregister.org/record/" + addressId + ".json"),
                        Charset.forName("UTF-8")));
                LOGGER.info(address.toString());

                String point = address.getJSONObject(address.keys().next()).getJSONArray("item").getJSONObject(0).getString("point");

                String[] points = point.replace("[", "").replace("]", "").split(", ");


                GeostoreItem gs = geostoreDao.getGeostoreItem("POINT(" + points[0] + " " + points[1] + ")");

                if(gs != null) {
                    prison.append("local-authority", new JSONObject(mapper.writeValueAsString(gs)));
                    prison.append("point", point);
                    prisons.add(prison);
                }
            }
            catch (Exception e){
                LOGGER.info(e.getStackTrace().toString());
            }

        }

        return new JSONArray(prisons).toString();
    }

}
