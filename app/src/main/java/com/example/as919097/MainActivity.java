package com.example.as919097;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Initialising local variables
    private FirebaseAuth mAuth;
    private Button RegisterButton;
    private Button SignInButton;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring the aspects in the xml file
        setSupportActionBar(findViewById(R.id.toolbar));
        mEmailView = findViewById(R.id.LogonEmail);
        mPasswordView = findViewById(R.id.LogonPassword);

        // Register Button, opening the register activity
        RegisterButton = (Button) findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openregister();
            }
        });

        // Sign In Button, opening SignInActivity
        SignInButton = (Button) findViewById(R.id.signInButton);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {opensignin(); }
        });

        // Enter key for library
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 100 || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        // Firebase authentication
        mAuth = FirebaseAuth.getInstance();


    }

    // Function for register button
    public void openregister() {
        Intent Register_intent = new Intent(this, registerActivity.class);
        startActivity(Register_intent);
    }

    // Function for sign in button
    public void opensignin() {
        attemptLogin();
    }

    private void attemptLogin() {
        // Getting the email and password values
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        // Checks for if the email or password field is empty
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Please put text in the input fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email has a "@"
        if (!emailSignInValid(email)) {
            Toast.makeText(this, "Please put a valid email",  Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();
        // Checking the user credentials through firebase authentication
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("firebasee","signInWithEmail() onComplete: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d("firebase", "Problem signing in: " + task.getException());
                    showErrorDialog("Username or password is incorrect");
                } else {
                    Intent intent = new Intent(MainActivity.this, homeActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private boolean emailSignInValid(String email) {return(email.contains("@")); }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
