package com.aditya.milkman.component;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import com.aditya.milkman.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

public class CustomInputField extends LinearLayout {

    private TextInputLayout inputLayout;
    private TextInputEditText editText;
    private TypedArray array;
    private String hintText, inputType, endIconMode;
    private int endIconDrawableResource;

    public CustomInputField(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.input_layout, this, true);

        inputLayout = findViewById(R.id.text_input_layout);
        editText = findViewById(R.id.text_input_edit_text);

        // This is kinda used for creating the custom attributes like we did use in our custom component.
        array = context.obtainStyledAttributes(attributeSet, R.styleable.CustomInputField);

        hintText = array.getString(R.styleable.CustomInputField_cif_hintText);
        inputType = array.getString(R.styleable.CustomInputField_cif_inputType);
        endIconMode = array.getString(R.styleable.CustomInputField_cif_endIconMode);
        endIconDrawableResource = array.getResourceId(R.styleable.CustomInputField_cif_endIconDrawable, 0);

        if (hintText != null) {
            inputLayout.setHint(hintText);
        }

        if (inputType != null) {
            switch (inputType) {
                case "textEmailAddress":
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
                case "textPassword":
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case "number":
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    break;
                case "date":
                    editText.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
                    break;
                case "text":
                default:
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
            }
        }

        if (endIconMode != null) {
            if (endIconMode.equals("password_toggle")) {
                inputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            } else if (endIconMode.equals("custom")) {
                inputLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
                inputLayout.setEndIconDrawable(endIconDrawableResource);
            }
        }
    }

    public Editable getText() {
        return editText.getText();
    }

    public void setEndIconClickListener(OnClickListener listener) {
        if (inputLayout != null) {
            if (inputType.equals("date")) {
                // Now we have to open the date dialog and set the text to the date.
                inputLayout.setEndIconOnClickListener(listener);
            }
        }
    }

    public TextInputEditText getEditText() {
        return this.editText;
    }

}
