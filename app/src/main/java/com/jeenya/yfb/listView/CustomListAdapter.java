package com.jeenya.yfb.listView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeenya.yfb.R;
import com.jeenya.yfb.db.UserDataFromDb;

import java.util.ArrayList;

/**
 * Created by hp on 7/7/2015.
 */
public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> {

    ArrayList<UserDataFromDb> userDataList = new ArrayList<>();
    LayoutInflater inflator;
    // private VolleySingleton volleySingleton;
    //  private ImageLoader imageLoader;
    private ClickListener clickListener;
    //FOR TEST
    private int listOrGrid = 0;


    public void setContactData(ArrayList<UserDataFromDb> userDataList) {
        this.userDataList = userDataList;
        notifyItemRangeChanged(0, userDataList.size());
    }

    public CustomListAdapter(Context context) {
        inflator = LayoutInflater.from(context);
        //  volleySingleton = VolleySingleton.getInstance();
        //  imageLoader = volleySingleton.getImageLoader();
        //  listOrGrid = a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.lsitview_custom_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        UserDataFromDb currentUser = userDataList.get(position);
        holder.name.setText(currentUser.getName());
        holder.ph.setText(currentUser.getPhNumber());
        holder.bloodGroup.setText(currentUser.getBloodGroup());



    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, ph, bloodGroup;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.userName);
            ph = (TextView) itemView.findViewById(R.id.userNumber);
            bloodGroup = (TextView) itemView.findViewById(R.id.userBloodGroup);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition(), userDataList);
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position, ArrayList<UserDataFromDb> product);

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
