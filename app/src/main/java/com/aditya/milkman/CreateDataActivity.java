package com.aditya.milkman;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.aditya.milkman.adapter.RecyclerViewAdapter;
import com.aditya.milkman.component.CustomInputField;
import com.aditya.milkman.handler.DatabaseHandler;
import com.aditya.milkman.model.MilkRecord;
import com.aditya.milkman.ui.home.HomeFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateDataActivity extends AppCompatActivity {

    private CustomInputField nameInputField, addressInputField, milkInLitresInputField, dateInputField;
    private MaterialButton materialButton;
    private DatabaseHandler databaseHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

        // Instantiating the DB handler object.
        databaseHandler = new DatabaseHandler(this);

        materialButton = findViewById(R.id.create_data_btn);
        nameInputField = findViewById(R.id.name_input);
        addressInputField = findViewById(R.id.address_input);
        milkInLitresInputField = findViewById(R.id.litre_input);
        dateInputField = findViewById(R.id.date_input);
        dateInputField.setEndIconClickListener(value -> {
            showDatePickerDialog(dateInputField);
        });

        materialButton.setOnClickListener(value -> {
            // We have to check the fields value are valid or not.
            if (checkIsEmpty(nameInputField) || checkIsEmpty(addressInputField) || checkIsEmpty(milkInLitresInputField) || checkIsEmpty(dateInputField)) {
                Toast.makeText(this, "Field's cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            } else if (databaseHandler.isRecordExistsByName(nameInputField.getText().toString().trim())) {
                Toast.makeText(this, "Record Exists by the name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Else we need to save the data to the database.
            MilkRecord newRecord = MilkRecord
                    .builder()
                    .personName(nameInputField.getText().toString().trim())
                    .personAddress(addressInputField.getText().toString().trim())
                    .milkWanted(Float.parseFloat(milkInLitresInputField.getText().toString().trim()))
                    .startingDate(dateInputField.getText().toString())
                    .build();
            databaseHandler.saveRecord(newRecord);

            // Now we have to display a toast.
            Toast.makeText(this, "Record Saved Successfully", Toast.LENGTH_SHORT).show();

        });

    }

    private boolean checkIsEmpty(CustomInputField customInputField) {
        return customInputField.getText().toString().isEmpty() || customInputField.getText() == null;
    }

    public void goBack(View view) {
        finish();
    }

    private void showDatePickerDialog(CustomInputField customInputField) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker datePickerView, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // This listener is called when the user clicks ok in the dialog.
                    String date = String.format(Locale.US, "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    customInputField.getEditText().setText(date);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }
}
