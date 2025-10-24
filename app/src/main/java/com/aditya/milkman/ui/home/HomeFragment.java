package com.aditya.milkman.ui.home;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aditya.milkman.R;
import com.aditya.milkman.adapter.RecyclerViewAdapter;
import com.aditya.milkman.handler.DatabaseHandler;
import com.aditya.milkman.model.MilkRecord;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<MilkRecord> milkRecordList;
    private DatabaseHandler databaseHandler;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Here we need to just instantiate the variables.
        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // Here we need to fetch the records.
        databaseHandler = new DatabaseHandler(view.getContext());
        milkRecordList = databaseHandler.fetchAllRecords();

        if (milkRecordList != null) {
            // For Recycler view adapter.
            recyclerViewAdapter = new RecyclerViewAdapter(view.getContext(), milkRecordList, databaseHandler);

            // Here we have to implement the logic of adding the items to the view in the recycler view.
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMilkRecordFromDB();
    }

    private void loadMilkRecordFromDB() {
        List<MilkRecord> newMilkRecordList = databaseHandler.fetchAllRecords();
        recyclerViewAdapter.updateRecord(newMilkRecordList);
    }
}