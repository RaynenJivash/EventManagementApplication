package com.fit2081.assignment1;

import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterCategory extends RecyclerView.Adapter<RecyclerAdapterCategory.ViewHolder> {

    List<EventCategory> data;


    public RecyclerAdapterCategory(List<EventCategory> _data) {
        super();
        this.data = _data;
        Log.d("stock", "got data with size=" + _data.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false); //CardView inflated as RecyclerView list item

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d("stock", "Bind a view for pos" + position);
        viewHolder.catID.setText(data.get(position).getCatId());
        viewHolder.catName.setText(data.get(position).getName());
        viewHolder.catCount.setText(String.valueOf(data.get(position).getCount()));


        int count = 0;
        //If a valid count is stored then set to that count, else set to 0
        if(data.get(position).getCount() != -1){
            count = data.get(position).getCount();
        }
        viewHolder.catCount.setText(String.valueOf(count));

        // If isActive set cardview text to yes, else no
        String isActive;
        if(data.get(position).getIsActive()){
            isActive = "Yes";
        }
        else{
            isActive = "No";
        }

        viewHolder.catIA.setText(isActive);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students

            Context context;
            @Override
            public void onClick(View v) {

//                String studentCountryString = viewHolder.eventName.getText().toString();

                context = v.getContext();

                // launch new Activity with supplied country name
                Intent intent = new Intent(v.getContext(),GoogleMapActivity.class);
                intent.putExtra("searchLocation", data.get(position).getCatLocation());
                context.startActivity(intent);

//                startActivity(new Intent(v.getContext(),EventGoogleResult.class));
//                toGoogle(v, studentCountryString);
//                Intent i = new Intent(v.getContext(), EventGoogleResult.class);
//                startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<EventCategory> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView catID;
        public TextView catName;
        public TextView catCount;
        public TextView catIA;



        public ViewHolder(View itemView) {
            super(itemView);
//            this.itemView = itemView;
            catID = itemView.findViewById(R.id.catID);
            catName = itemView.findViewById(R.id.catCount);
            catCount = itemView.findViewById(R.id.catName);
            catIA = itemView.findViewById(R.id.catIA);
        }
    }

}

