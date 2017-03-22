package pkc.trafficquest.sccapstone.trafficquest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SoullDrumma on 3/21/17.
 */

public class SavedSearchesAdapter extends RecyclerView.Adapter<SavedSearchesAdapter.ViewHolder> {

    private final List<Address> mAddresses;

    private OnItemClickListener mOnClick;

    public SavedSearchesAdapter(List<Address> addresses) {
        mAddresses = addresses;
    }

    @Override
    public SavedSearchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_search, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedSearchesAdapter.ViewHolder holder, int position) {
        final Address address = mAddresses.get(position);
        holder.textView.setText(address.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClick != null) {
                    mOnClick.onClick(address);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnClick = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onClick(Address address);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.saved_searches_textview);
        }
    }

}
