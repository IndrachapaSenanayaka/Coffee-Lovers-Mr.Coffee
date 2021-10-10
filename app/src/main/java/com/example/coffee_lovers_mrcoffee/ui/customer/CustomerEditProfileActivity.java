package com.example.coffee_lovers_mrcoffee.ui.customer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Customer;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.disposables.Disposable;

public class CustomerEditProfileActivity extends AppCompatActivity {

    // dependencies
    private final Container container = Container.instant();
    private final AuthService authService = container.authService;
    private final ValidationUtils validationUtils = container.validationUtils;

    // components
    private EditText txt_firstName, txt_lastName, txt_phone;

    // disposals
    private Disposable currentUserDisposer;

    // store entire customer data
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit_profile);

        // init components
        txt_firstName = findViewById(R.id.txt_edit_firstName);
        txt_lastName = findViewById(R.id.txt_edit_lastName);
        txt_phone = findViewById(R.id.txt_edit_phoneNumber);

        // listen for current user changes
        currentUserDisposer = authService
                .currentUser
                .subscribe(
                        this::ListenUserChanges,
                        throwable -> System.err.println("Error while receiving customer data" + throwable));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // remove listeners
        currentUserDisposer.dispose();
    }


    // ---- EVENTS

    // current user changes listeners
    private void ListenUserChanges(Customer customer) {

        this.customer = customer;

        // update fields
        txt_firstName.setText(customer.firstName);
        txt_lastName.setText(customer.lastName);
        txt_phone.setText(customer.phoneNumber);

    }


    // handle save changes click
    public void onSaveChangesCLick(View v) {

        // validate
        if (!validateFields())
            return;

        customer.firstName = txt_firstName.getText().toString();
        customer.lastName = txt_lastName.getText().toString();
        customer.phoneNumber = txt_phone.getText().toString();

        // update
        authService.UpdateCurrentUser(
                customer,
                task -> {
                    // go back
                    CustomerEditProfileActivity.this.finish();
                },
                e -> Toast.makeText(CustomerEditProfileActivity.this, "Update failed", Toast.LENGTH_LONG).show()
        );

    }


    // handle cancel button click
    public void onCancelClick(View v) {
        finish();
    }


    // ---- MECHANISMS

    // validate fields
    private boolean validateFields() {

        Map<EditText, String> textBoxes = new HashMap<>();
        textBoxes.put(txt_firstName, "First name is required");
        textBoxes.put(txt_lastName, "Last name is required");
        textBoxes.put(txt_phone, "Phone number is required");

        return !validationUtils.isAnyEmpty(textBoxes);

    }

}