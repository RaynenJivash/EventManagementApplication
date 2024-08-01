package com.fit2081.assignment1;

import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterEvent extends RecyclerView.Adapter<RecyclerAdapterEvent.ViewHolder> {

    List<Event> data;


    public RecyclerAdapterEvent(List<Event> _data) {
        super();
        data = _data;
        Log.d("stock", "got data with size=" + _data.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout2, viewGroup, false); //CardView inflated as RecyclerView list item

        return new ViewHolder(v);
    }

//    public void toGoogle(View v, String studentCountryString){
//        Intent intent = new Intent(v.getContext(), EventGoogleResult.class);
//        intent.putExtra("countryName", studentCountryString);
//        startActivity(intent);
//    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d("stock", "Bind a view for pos" + position);
        viewHolder.catID2.setText("Category Id: "+ data.get(position).getCatId());
        viewHolder.eventName.setText("Name: "+data.get(position).getName());
        viewHolder.eventId.setText("Id: "+data.get(position).getEventId());

        int tickets = 0;
        if(data.get(position).getCount() != -1){
            tickets = data.get(position).getCount();
        }
        viewHolder.eventCount.setText("Tickets: "+String.valueOf(tickets));

        if(data.get(position).getIsActive()){
            viewHolder.eventActive.setText("Active");
        }
        else{
            viewHolder.eventActive.setText("Inactive");
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students

            Context context;
            @Override
            public void onClick(View v) {

                String studentCountryString = viewHolder.eventName.getText().toString();

                context = v.getContext();

                // launch new Activity with supplied country name
                Intent intent = new Intent(v.getContext(),EventGoogleResult.class);
                intent.putExtra("searchName", studentCountryString);
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

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView catID2;
        public TextView eventName;
        public TextView eventId;
        public TextView eventCount;
        public TextView eventActive;



        public ViewHolder(View itemView) {
            super(itemView);
//            this.itemView = itemView;
            catID2 = itemView.findViewById(R.id.catID2);
            eventName = itemView.findViewById(R.id.eventName);
            eventId = itemView.findViewById(R.id.eventID);
            eventCount = itemView.findViewById(R.id.eventCount);
            eventActive = itemView.findViewById(R.id.eventActive);
        }
    }

}

