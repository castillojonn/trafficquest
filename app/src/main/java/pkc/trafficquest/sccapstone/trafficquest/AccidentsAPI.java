package pkc.trafficquest.sccapstone.trafficquest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Daniel Free on 11/9/2016.
 */

public interface AccidentsAPI {
    //@GET("/REST/v1/Locations")
    //Call<Destination> getCoordinates(@QueryMap Map<String, String> options);
    @GET("/REST/v1/Traffic/Incidents/37,-105,45,-94?key=AmJHdhFiW4EQCdWrgEoTk5-vo8zW-96v2LBmeBgnc0z_FV0Ru-gZizGCLfhtRtrJ")
    Call<RequestPackage> soontobedecided();
}

