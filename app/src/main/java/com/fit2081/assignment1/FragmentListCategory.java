package com.fit2081.assignment1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fit2081.assignment1.provider.CategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListCategory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterCategory adapter;

    Gson gson = new Gson();
    CategoryViewModel mCategoryViewModel;


    public FragmentListCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Now: all the data items are in the array list, send it to the recycler adapter to create views.


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recycler_view);


        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        // Setting adapter based on current stored categories in SharedPreferences
//        ArrayList<EventCategory> db = retrieveCategoryData();
//        adapter = new RecyclerAdapterCategory(db);
//        recyclerView.setAdapter(adapter);

        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), newData -> {
            adapter = new RecyclerAdapterCategory(newData);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
//            Toast.makeText(this, newData, Toast.LENGTH_SHORT).show();
        });
//        recyclerView.setAdapter(adapter);


        return view;
    }

    //Method to update current data arrayList based on sharedPreferences
    public ArrayList<EventCategory> retrieveCategoryData(){

//        mCustomerViewModel.getAllCustomers().observe(this, newData -> {
//                    adapter.setCustomers(newData);
//                    adapter.notifyDataSetChanged();
//                    tv.setText(Integer.toString(newData.size()));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Category_List", MODE_PRIVATE);
        String string = sharedPreferences.getString("CATEGORIES", "[]");

            Type type = new TypeToken<ArrayList<EventCategory>>() {
            }.getType();
            return gson.fromJson(string, type);

    }

    //Method called when wanting to update current fragment view based on changes made in sharedPreferences
    public void notifyAdapter(){
        ArrayList<EventCategory> db = retrieveCategoryData();
        adapter = new RecyclerAdapterCategory(db);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}