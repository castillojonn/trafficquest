package pkc.trafficquest.sccapstone.trafficquest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pkcho on 11/24/2016.
 */

public class AccidentsFeedParser {
    public static ArrayList<Accidents> parseFeed(String Content)  {
        try {
            JSONArray array = new JSONArray(Content);
            ArrayList<Accidents> aArrayList = new ArrayList<Accidents>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                Accidents acc = new Accidents();
                acc.setAccidentId(obj.getInt("incidentId"));
                acc.setDescription(obj.getString("description"));
                acc.setsTime(obj.getString("start"));
                acc.setEndTime(obj.getString("end"));
                acc.setLastModified(obj.getString("lastModified"));
                acc.setCongestion(obj.getString("congestion"));

                aArrayList.add(acc);
            }
            return aArrayList;
        }
        catch (JSONException e){
            return null;

        }
    }
}
