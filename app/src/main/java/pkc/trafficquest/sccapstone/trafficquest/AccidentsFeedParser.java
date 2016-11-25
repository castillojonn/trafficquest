package pkc.trafficquest.sccapstone.trafficquest;

/**
 * Created by pkcho on 11/24/2016.
 */

public class AccidentsFeedParser {
    /*public static ArrayList<AccidentsAPI> parseFeed(String Content)  {
        try {
            JSONArray array = new JSONArray(Content);
            ArrayList<AccidentsAPI> aArrayList = new ArrayList<AccidentsAPI>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                AccidentsAPI acc = new AccidentsAPI();
                acc.setAccidentId(obj.getInt("incidentId"));
                acc.setDescription(obj.getString("description"));
                acc.setsTime(obj.getString("start"));
                acc.setEnd(obj.getString("end"));
                acc.setLastModified(obj.getString("lastModified"));
                acc.setRoadClosed(obj.getString("congestion"));

                aArrayList.add(acc);
            }
            return aArrayList;
        }
        catch (JSONException e){
            return null;

        }
    }
    */
}
