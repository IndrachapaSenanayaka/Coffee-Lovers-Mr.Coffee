package com.example.coffee_lovers_mrcoffee.ui.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.utils.ValidationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class CustomerChangePasswordActivity extends AppCompatActivity {

    // dependencies
    Container container = Container.instant();
    AuthService authService = container.authService;
    ValidationUtils validationUtils = container.validationUtils;

    // components
    private EditText txt_password, txt_confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_change_password);

        // init components
        txt_password = findViewById(R.id.txt_chngpwd_password);
        txt_confirmPassword = findViewById(R.id.txt_chngpwd_confirmPassword);
    }


    // ---- MECHANISMS

    // check if inputs are valid
    private boolean validateFields() {

        Map<EditText, String> textBoxes = new HashMap<>();
        if (validationUtils.isEmpty(txt_password, "Please provide a password"))
            return false;
        else if (!txt_password.getText().toString().equals(txt_confirmPassword.getText().toString())) {
            txt_confirmPassword.setError("Two passwords do not match");
            return false;
        } else
            return true;

    }


    // ---- EVENTS

    // cancel button click
    public void onCancelButtonClick(View v) {
        finish();
    }


    // change password button click
    public void onChangePasswordButtonClick(View v) {

        // validate
        if(!validateFields())
            return;

        authService.ChangePassword(txt_password.getText().toString(), task -> {
            if(task.isSuccessful()) {
                Toast.makeText(CustomerChangePasswordActivity.this,"Password was changed", Toast.LENGTH_LONG).show();
                finish();
            } else if (task.getException() != null) {
                Toast.makeText(CustomerChangePasswordActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}