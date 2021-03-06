package com.example.coffee_lovers_mrcoffee.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Customer;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.ui.admin.admin_landing_page;
import com.example.coffee_lovers_mrcoffee.ui.customer.CustomerNavigationActivity;
import com.example.coffee_lovers_mrcoffee.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.disposables.Disposable;

public class SignInActivity extends AppCompatActivity {

    // dependencies
    private final Container container = Container.instant();
    private final ValidationUtils validationUtils = container.validationUtils;
    private final AuthService authService = container.authService;

    // disposals
    private Disposable currentUserDisposer;

    // components
    private EditText txt_email, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // init controllers
        txt_email = findViewById(R.id.txt_signin_email);
        txt_password = findViewById(R.id.txt_signin_password);

        // listen for current user changes
        currentUserDisposer = authService.currentUser.subscribe(this::onUserChanges);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentUserDisposer.dispose();
    }

    // ---- EVENTS

    // handle sign up link click
    public void onSignUpLinkClick(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    // handle sign in button click
    public void onSignInClick(View v) {

        // validation
        if (!validateFields())
            return;

        // check if admin is trying to login
        if (txt_email.getText().toString().equals("admin@admin.com")
                && txt_password.getText().toString().equals("admin")) {

            Intent intent = new Intent(SignInActivity.this, admin_landing_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {
            authService.SignIn(txt_email.getText().toString(), txt_password.getText().toString(), task -> {
            }, e -> Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
        }

    }

    // current user changes listeners
    private void onUserChanges(Customer customer) {
        // logout if user is null
        if (customer != Customer.NULL) {

            // goto sign in
            Intent intent = new Intent(SignInActivity.this, CustomerNavigationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

    // ---- MECHANISMS

    private boolean validateFields() {

        Map<EditText, String> textBoxes = new HashMap<>();
        textBoxes.put(txt_email, "Email is required");
        textBoxes.put(txt_password, "Password is required");

        if (validationUtils.isAnyEmpty(textBoxes)) {
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email.getText()).matches()) {
            txt_email.setError("Email address is not valid");
            return false;
        } else {
            return true;
        }

    }

}
