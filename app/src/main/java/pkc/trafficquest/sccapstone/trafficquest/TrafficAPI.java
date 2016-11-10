package pkc.trafficquest.sccapstone.trafficquest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Daniel Free on 11/9/2016.
 */

public interface TrafficAPI {
    @GET("/REST/v1/Locations")
    Call<Destination> getCoordinates(@QueryMap Map<String, String> options);
}

