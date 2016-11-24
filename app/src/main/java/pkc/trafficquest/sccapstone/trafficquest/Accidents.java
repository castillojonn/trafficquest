package pkc.trafficquest.sccapstone.trafficquest;

/**
 * Created by pkcho on 11/24/2016.
 */

public class Accidents {
    private String sTime;
    private String endTime;
    private int accidentId;
    private String description;
    //private int numVehicles;
    private int severity;
    private String lastModified;
    private String Congestion;
    public String getCongestion() {
        return Congestion;
    }

    public void setCongestion(String congestion) {
        Congestion = congestion;
    }


    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public int getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(int accidentId) {
        this.accidentId = accidentId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }




}
