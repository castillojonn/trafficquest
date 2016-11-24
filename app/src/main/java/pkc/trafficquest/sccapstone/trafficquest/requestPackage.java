package pkc.trafficquest.sccapstone.trafficquest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pkchoksi on 11/24/2016.
 */

public class RequestPackage {
    private String Uri;
    private String method = "GET";
    private Map<String,String> params = new HashMap<>();

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public void setParams(String key, String value){
        params.put(key,value);
    }
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
}
