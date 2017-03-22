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
        holder.textView.setText(mAddresses.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.saved_searches_textview);
        }
    }

}
