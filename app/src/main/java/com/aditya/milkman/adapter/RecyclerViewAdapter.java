package com.aditya.milkman.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.aditya.milkman.R;
import com.aditya.milkman.component.CustomInputField;
import com.aditya.milkman.handler.DatabaseHandler;
import com.aditya.milkman.model.MilkRecord;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<MilkRecord> milkRecordList;
    private DatabaseHandler databaseHandler;

    public RecyclerViewAdapter(Context context, List<MilkRecord> milkRecordList, DatabaseHandler databaseHandler) {
        this.context = context;
        this.milkRecordList = milkRecordList;
        this.databaseHandler = databaseHandler;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        MilkRecord milkRecord = milkRecordList.get(i);
        viewHolder.cardName.setText(milkRecord.getPersonName());
        viewHolder.cardAddress.setText(milkRecord.getPersonAddress());
        viewHolder.cardMilkWanted.setText("Milk Wanted: " + milkRecord.getMilkWanted() + "L");
        viewHolder.cardStartingDate.setText("Starting: " + milkRecord.getStartingDate());

        viewHolder.deleteButton.setOnClickListener(l -> {

            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_delete, null);

            CustomInputField dialogInput = dialogView.findViewById(R.id.dialog_input);
            TextView warningText = dialogView.findViewById(R.id.warning_text);

            warningText.setText("To confirm deletion, type " + milkRecord.getPersonName() + " below:");

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                    .setTitle("Confirm Deletion")
                    .setView(dialogView)
                    .setCancelable(true)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        String input = dialogInput.getText().toString().trim();
                        if (input.equals(milkRecord.getPersonName())) {
                            int deleted = databaseHandler.deleteRecord(milkRecord.getPersonId());
                            if (deleted > 0) {
                                // Record has been deleted now we have to update the adapter and other things.
                                milkRecordList.remove(i);
                                notifyItemRemoved(i);
                                Toast.makeText(context, "Record Deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Delete Failed!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Incorrect Name, Deletion failed.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = builder.show();

            // Setting a custom bg.
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_box);
            alertDialog.getWindow().getDecorView().setPadding(20, 20, 20, 20);
        });

        viewHolder.updateButton.setOnClickListener(l -> {
            Toast.makeText(context, "Feature not available right now.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return milkRecordList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateRecord(List<MilkRecord> milkRecordList) {
        this.milkRecordList = milkRecordList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView cardName, cardAddress, cardMilkWanted, cardStartingDate;
        private MaterialButton updateButton, deleteButton;

        @SuppressLint("CutPasteId")
        public ViewHolder(@NonNull View view) {
            super(view);
            cardName = view.findViewById(R.id.card_name);
            cardAddress = view.findViewById(R.id.card_address);
            cardMilkWanted = view.findViewById(R.id.card_milk_wanted);
            cardStartingDate = view.findViewById(R.id.card_starting_date);
            updateButton = view.findViewById(R.id.update_btn);
            deleteButton = view.findViewById(R.id.delete_btn);
        }
    }
}
