package com.example.as919097;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookFragment extends Fragment {

    // UI references
    private EditText bNameInput;
    private EditText bSchoolEmailInput;
    private EditText bFormClassInput;
    private EditText bRecipientsInput;
    private EditText bTimeInput;
    private EditText bBookMessage;
    private Button bSubmit;

    // Firebase referencing
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        // Firebase
        db = FirebaseFirestore.getInstance();

        // Define the text fields
        bNameInput = view.findViewById(R.id.book_fullName);
        bSchoolEmailInput = view.findViewById(R.id.book_schoolEmail);
        bFormClassInput = view.findViewById(R.id.book_formClass);
        bRecipientsInput = view.findViewById(R.id.book_recipients);
        bTimeInput = view.findViewById(R.id.book_timeAndDate);
        bBookMessage = view.findViewById(R.id.book_message);
        bSubmit = view.findViewById(R.id.book_submit);

        // On click for submission
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptBookSubmission();
            }
        });

        return view;
    }

    // Checking if the inputs are valid and should be passed through
    private void attemptBookSubmission() {
        String Name = bNameInput.getText().toString();
        String schoolEmail = bSchoolEmailInput.getText().toString();
        String formClass = bFormClassInput.getText().toString();
        String Time = bTimeInput.getText().toString();

        bNameInput.setError(null);
        bSchoolEmailInput.setError(null);
        bFormClassInput.setError(null);
        bTimeInput.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Validation, checking if each input is correct
        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(requireContext(), "Please put your name", Toast.LENGTH_SHORT).show();
            focusView = bNameInput;
            cancel = true;
            return;
        }

        if (TextUtils.isEmpty(schoolEmail)) {
            Toast.makeText(requireContext(), "Please put your school email", Toast.LENGTH_SHORT).show();
            focusView = bSchoolEmailInput;
            cancel = true;
            return;
        }

        if (TextUtils.isEmpty(formClass)) {
            Toast.makeText(requireContext(), "Please put your form class", Toast.LENGTH_SHORT).show();
            focusView = bFormClassInput;
            cancel = true;
            return;
        }

        if (TextUtils.isEmpty(Time)) {
            Toast.makeText(requireContext(), "Please put your intended time", Toast.LENGTH_SHORT).show();
            focusView = bTimeInput;
            cancel = true;
            return;
        }

        if (!isSchoolEmailValid(schoolEmail)) {
            Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            focusView = bSchoolEmailInput;
            cancel = true;
        }

        // Checks to see if any of the inputs failed
        if (cancel) {
            focusView.requestFocus();
        } else {
            writeFirestore();
        }
    }

    // Check if school email is valid
    private boolean isSchoolEmailValid(String email) {return(email.contains("@")); }

    // Write Data into the firestore database
    private void writeFirestore() {
        // Get inputs and convert into strings
        String Name = bNameInput.getText().toString();
        String schoolEmail = bSchoolEmailInput.getText().toString();
        String formClass = bFormClassInput.getText().toString();
        String recipient = bRecipientsInput.getText().toString();
        String Time = bTimeInput.getText().toString();

        // Creating a hashmap to layout the data in an orderly way
        Map<String, Object> user = new HashMap<>();
        user.put("Full Name",Name);
        user.put("School Email",schoolEmail);
        user.put("Form Class",formClass);
        user.put("Date", Time);
        user.put("Others involved", recipient);

        // Contact firebase
        db.collection("user")
            .add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                // Toast messages for success
                public void onSuccess(DocumentReference documentReference) {
                    showSubmittedDialog("Booking Submitted, A Councillor will be in touch via email!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                // Toast messages if failed
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
        });
    }

    private void showSubmittedDialog(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Success!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.checkbox_on_background)
                .show();
    }
}