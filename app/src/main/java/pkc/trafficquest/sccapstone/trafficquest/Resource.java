
package pkc.trafficquest.sccapstone.trafficquest;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resource {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("bbox")
    @Expose
    private List<Double> bbox = new ArrayList<Double>();
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("point")
    @Expose
    private Point point;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("confidence")
    @Expose
    private String confidence;
    @SerializedName("entityType")
    @Expose
    private String entityType;
    @SerializedName("geocodePoints")
    @Expose
    private List<GeocodePoint> geocodePoints = new ArrayList<GeocodePoint>();
    @SerializedName("matchCodes")
    @Expose
    private List<String> matchCodes = new ArrayList<String>();

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The __type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The bbox
     */
    public List<Double> getBbox() {
        return bbox;
    }

    /**
     * 
     * @param bbox
     *     The bbox
     */
    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * 
     * @param point
     *     The point
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * 
     * @return
     *     The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The confidence
     */
    public String getConfidence() {
        return confidence;
    }

    /**
     * 
     * @param confidence
     *     The confidence
     */
    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    /**
     * 
     * @return
     *     The entityType
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * 
     * @param entityType
     *     The entityType
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * 
     * @return
     *     The geocodePoints
     */
    public List<GeocodePoint> getGeocodePoints() {
        return geocodePoints;
    }

    /**
     * 
     * @param geocodePoints
     *     The geocodePoints
     */
    public void setGeocodePoints(List<GeocodePoint> geocodePoints) {
        this.geocodePoints = geocodePoints;
    }

    /**
     * 
     * @return
     *     The matchCodes
     */
    public List<String> getMatchCodes() {
        return matchCodes;
    }

    /**
     * 
     * @param matchCodes
     *     The matchCodes
     */
    public void setMatchCodes(List<String> matchCodes) {
        this.matchCodes = matchCodes;
    }

}
