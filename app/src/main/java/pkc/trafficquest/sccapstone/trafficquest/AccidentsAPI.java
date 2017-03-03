package pkc.trafficquest.sccapstone.trafficquest;

import android.app.DownloadManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Daniel Free on 11/9/2016.
 */

public interface AccidentsAPI {
    //@GET("/REST/v1/Locations")
    //Call<Destination> getCoordinates(@QueryMap Map<String, String> options);
    //@GET("/REST/v1/Traffic/Incidents/37,-105,45,-94?key=AmJHdhFiW4EQCdWrgEoTk5-vo8zW-96v2LBmeBgnc0z_FV0Ru-gZizGCLfhtRtrJ")
    //Call<RequestPackage> soontobedecided();
    @GET("/REST/v1/Traffic/Incidents/{southLat},{westLng},{northLat},{eastLng}?key=AmJHdhFiW4EQCdWrgEoTk5-vo8zW-96v2LBmeBgnc0z_FV0Ru-gZizGCLfhtRtrJ")
    //Call<RequestPackage> getIncidents(@QueryMap Map<String, String> options);
    Call<RequestPackage> getIncidents(
            @Path("southLat") double southLat,
            @Path("westLng") double westLng,
            @Path("northLat") double northLat,
            @Path("eastLng") double eastLng
    );
}

