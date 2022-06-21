package com.example.as919097;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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
                if (id == R.integer.login || id = EditorInfo.IME_NULL) {
                    return false;
                }
                attemptLogin();
                return true;
            }
        });
        // Firebase authentication
        mAuth = FirebaseAuth.getInstance();


    }

    public void openregister() {
        Intent Register_intent = new Intent(this, register.class);
        startActivity(Register_intent);
    }

    public void opensignin() {
        Intent SignIn_intent = new Intent(this, SignInActivity.class);
        startActivity(SignIn_intent);
    }

    private void attemptLogin() {

    }
}
