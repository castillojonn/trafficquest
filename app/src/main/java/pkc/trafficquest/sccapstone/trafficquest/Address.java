
package pkc.trafficquest.sccapstone.trafficquest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("adminDistrict")
    @Expose
    private String adminDistrict;
    @SerializedName("countryRegion")
    @Expose
    private String countryRegion;
    @SerializedName("formattedAddress")
    @Expose
    private String formattedAddress;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;


    public Address() {

    }

    public Address(String name, android.location.Address address) {
        adminDistrict = address.getAdminArea();
        countryRegion = address.getCountryName();
        formattedAddress = address.getAddressLine(0);
        locality = address.getLocality();
        this.name = name;
        lat = address.getLatitude();
        lng = address.getLongitude();
    }
    /**
     * 
     * @return
     *     The adminDistrict
     */
    public String getAdminDistrict() {
        return adminDistrict;
    }

    /**
     * 
     * @param adminDistrict
     *     The adminDistrict
     */
    public void setAdminDistrict(String adminDistrict) {
        this.adminDistrict = adminDistrict;
    }

    /**
     * 
     * @return
     *     The countryRegion
     */
    public String getCountryRegion() {
        return countryRegion;
    }

    /**
     * 
     * @param countryRegion
     *     The countryRegion
     */
    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    /**
     * 
     * @return
     *     The formattedAddress
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * 
     * @param formattedAddress
     *     The formattedAddress
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     * 
     * @return
     *     The locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * 
     * @param locality
     *     The locality
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Override
    public String toString() {
        return "Address{" +
                "adminDistrict='" + adminDistrict + '\'' +
                ", countryRegion='" + countryRegion + '\'' +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", locality='" + locality + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
