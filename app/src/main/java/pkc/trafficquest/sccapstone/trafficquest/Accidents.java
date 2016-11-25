package pkc.trafficquest.sccapstone.trafficquest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pkcho on 11/24/2016.
 */

public class Accidents {
    @SerializedName("incidentId")
    @Expose
    private int incidentId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("roadClosed")
    @Expose
    private String roadClosed;

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getRoadClosed() {
        return roadClosed;
    }

    public void setRoadClosed(String roadClosed) {
        this.roadClosed = roadClosed;
    }


}
