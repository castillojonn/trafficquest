
package pkc.trafficquest.sccapstone.trafficquest;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceSet {

    @SerializedName("estimatedTotal")
    @Expose
    private Integer estimatedTotal;
    @SerializedName("resources")
    @Expose
    private List<Resource> resources = new ArrayList<Resource>();

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
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * 
     * @param resources
     *     The resources
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

}
