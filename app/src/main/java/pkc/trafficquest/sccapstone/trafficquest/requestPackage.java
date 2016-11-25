package pkc.trafficquest.sccapstone.trafficquest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkchoksi on 11/24/2016.
 */

public class RequestPackage {

    @SerializedName("authenticationResultCode")
    @Expose
    private String authenticationResultCode;
    @SerializedName("brandLogoUri")
    @Expose
    private String brandLogoUri;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("resourceSets")
    @Expose
    List<ResourceSet> resourceSets = new ArrayList<ResourceSet>();
    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName("statusDescription")
    @Expose
    private String statusDescription;
    @SerializedName("traceId")
    @Expose
    private String traceId;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAuthenticationResultCode() {
        return authenticationResultCode;
    }

    public void setAuthenticationResultCode(String authenticationResultCode) {
        this.authenticationResultCode = authenticationResultCode;
    }

    public String getBrandLogoUri() {
        return brandLogoUri;
    }

    public void setBrandLogoUri(String brandLogoUri) {
        this.brandLogoUri = brandLogoUri;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<ResourceSet> getResourceSets() {
        return resourceSets;
    }

    public void setResourceSets(List<ResourceSet> resourceSets) {
        this.resourceSets = resourceSets;
    }




    /*
    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();
        for (String key:params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key),"UTF-8");
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            if (sb.length() > 0){
                sb.append("&");
            }
            sb.append(key + "=" +value);
        }
        return sb.toString();
    }
    */
}
