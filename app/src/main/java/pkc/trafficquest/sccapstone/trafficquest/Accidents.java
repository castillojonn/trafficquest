package pkc.trafficquest.sccapstone.trafficquest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pkcho on 11/24/2016.
 */

public class Accidents implements Parcelable {
    /*

               "__type":"TrafficIncident:http:\/\/schemas.microsoft.com\/search\/local\/ws\/rest\/v1",
               "point":{
                  "type":"Point",
                  "coordinates":[
                     38.85135,
                     -94.34033
                  ]
               },
               "congestion":"",
               "description":"MO-150 is closed between 5th Ave S and Court Dr - construction",
               "detour":"",
               "end":"\/Date(1310396400000)\/",
               "incidentId":210546697,
               "lane":"",
               "lastModified":"\/Date(1309391096593)\/",
               "roadClosed":true,
               "severity":3,
               "start":"\/Date(1307365200000)\/",
               "type":9,
               "verified":true
    */

    public Accidents() {
        // default constructor
    }

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("incidentId")
    @Expose
    private String incidentId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("severity")
    @Expose
    private int severity;
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
    private boolean roadClosed;
    @SerializedName("congestion")
    @Expose
    private String congestion;
    @SerializedName("detour")
    @Expose
    private String detour;
    @SerializedName("verified")
    @Expose
    private boolean verified;
    @SerializedName("lane")
    @Expose
    private String lane;
    @SerializedName("point")
    @Expose
    private Point point;
    @SerializedName("toPoint")
    @Expose
    private ToPoint toPoint;

    //List<Point> points = new ArrayList<Point>();

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @SerializedName("source")
    @Expose
    private  int source;
    /*
        public List<Point> getToPoint() {
            return toPoint;
        }

        public void setToPoint(List<Point> toPoint) {
            this.toPoint = toPoint;
        }


        @SerializedName("toPoint")
        @Expose
        List<Point> toPoint = new ArrayList<Point>();
    */
    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    @SerializedName("type")
    @Expose
    private int type2;
/*
public List<Point> getPoints() {
       return points;
   }

 */

    /*public void setPoints(List<Point> points) {
        this.points = points;
    }
*/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getDetour() {
        return detour;
    }

    public void setDetour(String detour) {
        this.detour = detour;
    }

    public String getCongestion() {
        return congestion;
    }

    public void setCongestion(String congestion) {
        this.congestion = congestion;
    }



    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
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

    public int getSeverity() { return severity; }

    public void setSeverity(int severity) {
        this.severity = severity;
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

    public boolean getRoadClosed() {
        return roadClosed;
    }

    public void setRoadClosed(boolean roadClosed) {
        this.roadClosed = roadClosed;
    }

    public Point getPoint(){ return point; }

    public void setPoint(Point point) { this.point = point; }

    public ToPoint getToPoint(){ return toPoint; }

    public void setToPoint(ToPoint toPoint){ this.toPoint = toPoint; }


    protected Accidents(Parcel in) {
        type = in.readString();
        incidentId = in.readString();
        description = in.readString();
        severity = in.readInt();
        start = in.readString();
        end = in.readString();
        lastModified = in.readString();
        roadClosed = in.readByte() != 0x00;
        congestion = in.readString();
        detour = in.readString();
        verified = in.readByte() != 0x00;
        lane = in.readString();
        point = (Point) in.readValue(Point.class.getClassLoader());
        toPoint = (ToPoint) in.readValue(ToPoint.class.getClassLoader());
        source = in.readInt();
        type2 = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(incidentId);
        dest.writeString(description);
        dest.writeInt(severity);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(lastModified);
        dest.writeByte((byte) (roadClosed ? 0x01 : 0x00));
        dest.writeString(congestion);
        dest.writeString(detour);
        dest.writeByte((byte) (verified ? 0x01 : 0x00));
        dest.writeString(lane);
        dest.writeValue(point);
        dest.writeValue(toPoint);
        dest.writeInt(source);
        dest.writeInt(type2);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Accidents> CREATOR = new Parcelable.Creator<Accidents>() {
        @Override
        public Accidents createFromParcel(Parcel in) {
            return new Accidents(in);
        }

        @Override
        public Accidents[] newArray(int size) {
            return new Accidents[size];
        }
    };
}