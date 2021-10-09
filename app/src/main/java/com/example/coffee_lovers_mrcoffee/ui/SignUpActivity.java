package com.example.coffee_lovers_mrcoffee.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.enums.Gender;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Customer;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.ui.customer.CustomerProfileActivity;
import com.example.coffee_lovers_mrcoffee.utils.ValidationUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class SignUpActivity extends AppCompatActivity {

    private final AuthService authService = new AuthService();
    private final BirthdayPickerFragment birthdayPicker = new BirthdayPickerFragment();
    private Disposable birthdayChangesDisposer;
    private final ValidationUtils validationUtils = new ValidationUtils();

    private LocalDate birthday = null;

    private TextView txt_birthday;
    private EditText txt_phoneNumber, txt_firstName, txt_lastName, txt_password, txt_email, txt_confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // init controllers
        txt_birthday = findViewById(R.id.txt_birthday);
        txt_phoneNumber = findViewById(R.id.txt_edit_phoneNumber);
        txt_firstName = findViewById(R.id.txt_edit_firstName);
        txt_lastName = findViewById(R.id.txt_edit_lastName);
        txt_password = findViewById(R.id.txt_password);
        txt_email = findViewById(R.id.txt_email);
        txt_confirmPassword = findViewById(R.id.txt_confirmPassword);

        // subscribe for birthday changes
        birthdayChangesDisposer = birthdayPicker.birthday.subscribe(this::onBirthdayChanges);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unsubscribe
        birthdayChangesDisposer.dispose();
    }


    // ---- EVENT HANDLERS

    // sign up button click handler
    public void onSignUpClick(View v) {

        // validate form
        if (!checkFields())
            return;

        // create model
        Customer cus = new Customer();
        cus.phoneNumber = txt_phoneNumber.getText().toString();
        cus.gender = ((RadioButton) findViewById(R.id.rb_male)).isChecked() ? Gender.Male : Gender.Female;
        cus.password = txt_password.getText().toString();
        cus.birthday = Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        cus.lastName = txt_lastName.getText().toString();
        cus.firstName = txt_firstName.getText().toString();
        cus.email = txt_email.getText().toString();

        // sign up
        authService.SignUp(cus,
                task -> {
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(SignUpActivity.this, CustomerProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (task.getException() != null) {
                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show());

    }

    // birthday picker label clicked
    public void onBirthdayPickClick(View v) {
        birthdayPicker.show(getSupportFragmentManager(), "datePicker");
    }


    // listen for birthday changes
    public void onBirthdayChanges(LocalDate localDate) {
        birthday = localDate;
        txt_birthday.setText(birthday.toString());
    }


    // sign in link click handler
    public void onSignInLinkClick(View v) {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    // ---- MECHANISMS

    private boolean checkFields() {

        Map<EditText, String> textBoxes = new HashMap<>();
        textBoxes.put(txt_phoneNumber, "Phone number is required");
        textBoxes.put(txt_firstName, "First name is required");
        textBoxes.put(txt_lastName, "Last name is required");
        textBoxes.put(txt_password, "Password is required");
        textBoxes.put(txt_email, "Email is required");

        if (validationUtils.isAnyEmpty(textBoxes)) {
            return false;
        } else if(!txt_password.getText().toString().equals(txt_confirmPassword.getText().toString())) {
            txt_confirmPassword.setError("Two passwords do not match");
            return false;
        } else if (birthday == null) {
            Toast.makeText(this,"Please select the birthday", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email.getText()).matches()) {
            txt_email.setError("Email address is not valid");
            return false;
        }

        return true;

    }


    // ---- LOWER LEVEL CLASSES

    public static class BirthdayPickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public Subject<LocalDate> birthday = PublishSubject.create();

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            // set max date
            datePickerDialog.getDatePicker().setMaxDate(c.getTime().getTime());

            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            birthday.onNext(LocalDate.of(year, month, day));
        }

    }

}