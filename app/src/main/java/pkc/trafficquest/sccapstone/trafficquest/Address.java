
package pkc.trafficquest.sccapstone.trafficquest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

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

}
