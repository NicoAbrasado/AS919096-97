package com.example.as919097;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button RegisterButton;
    private Button SignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declare the toolbar in the xml file
        setSupportActionBar(findViewById(R.id.toolbar));

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
    }

    public void openregister() {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    public void opensignin() {
        Intent SignIn_intent = new Intent(this, SignInActivity.class);
        startActivity(SignIn_intent);
    }
}