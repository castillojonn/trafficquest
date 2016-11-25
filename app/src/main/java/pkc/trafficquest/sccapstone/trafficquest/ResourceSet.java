
package pkc.trafficquest.sccapstone.trafficquest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResourceSet {

    @SerializedName("estimatedTotal")
    @Expose
    private Integer estimatedTotal;
    @SerializedName("resources")
    @Expose
    private List<Accidents> resources = new ArrayList<Accidents>();

    /**
     * 
     * @return
     *     The estimatedTotal
     */
    public Integer getEstimatedTotal() {
        return estimatedTotal;
    }

    /**
     * 
     * @param estimatedTotal
     *     The estimatedTotal
     */
    public void setEstimatedTotal(Integer estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    /**
     * 
     * @return
     *     The resources
     */
    public List<Accidents> getResources() {
        return resources;
    }

    /**
     * 
     * @param resources
     *     The resources
     */
    public void setResources(List<Accidents> resources) {
        this.resources = resources;
    }

}
