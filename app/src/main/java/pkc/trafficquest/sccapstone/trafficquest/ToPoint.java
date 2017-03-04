package pkc.trafficquest.sccapstone.trafficquest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToPoint implements Parcelable {

    public ToPoint() {
        // default constructor
    }

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = new ArrayList<Double>();

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
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     *     The coordinates
     */
    public List<Double> getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }


    protected ToPoint(Parcel in) {
        type = in.readString();
        if (in.readByte() == 0x01) {
            coordinates = new ArrayList<Double>();
            in.readList(coordinates, Double.class.getClassLoader());
        } else {
            coordinates = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        if (coordinates == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(coordinates);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ToPoint> CREATOR = new Parcelable.Creator<ToPoint>() {
        @Override
        public ToPoint createFromParcel(Parcel in) {
            return new ToPoint(in);
        }

        @Override
        public ToPoint[] newArray(int size) {
            return new ToPoint[size];
        }
    };
}