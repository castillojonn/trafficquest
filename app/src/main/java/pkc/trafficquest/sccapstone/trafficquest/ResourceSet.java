
package pkc.trafficquest.sccapstone.trafficquest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResourceSet {

    @SerializedName("estimatedTotal")
    @Expose
    private int estimatedTotal;
    @SerializedName("resources")
    @Expose
    private ArrayList<Accidents> resources = new ArrayList<Accidents>();

    /**
     * 
     * @return
     *     The estimatedTotal
     */
    public int getEstimatedTotal() {
        return estimatedTotal;
    }

    /**
     * 
     * @param estimatedTotal
     *     The estimatedTotal
     */
    public void setEstimatedTotal(int estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    /**
     * 
     * @return
     *     The resources
     */
    public ArrayList<Accidents> getResources() {
        return resources;
    }

    /**
     * 
     * @param resources
     *     The resources
     */
    public void setResources(ArrayList<Accidents> resources) {
        this.resources = resources;
    }

}
