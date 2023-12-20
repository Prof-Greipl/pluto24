package de.hawlandshut.pluto24;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mEditTextEmail;
    EditText mEditTextPassword;
    EditText mEditTextPassword1;
    Button mButtonCreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mEditTextEmail = findViewById(R.id.create_account_edit_text_email);
        mEditTextPassword = findViewById( R.id.create_account_edit_text_password);
        mEditTextPassword1 = findViewById( R.id.create_account_edit_text_password1); 
        mButtonCreateAccount = findViewById( R.id.create_account_button_create_account);
        
        mButtonCreateAccount.setOnClickListener( this );

        // TODO: Testing only - remove later
        //mEditTextEmail.setText("fhgreipl@gmail.com");
        //mEditTextPassword.setText("123456");
        //mEditTextPassword1.setText("123456");
    }


    @Override
    public void onClick(View view) {
        String email, password, password1;
        email = mEditTextEmail.getText().toString();
        password = mEditTextPassword.getText().toString();
        password1 = mEditTextPassword1.getText().toString();
        // TODO: Implementieren
        // Validieren der E-Mail (Aufbau)
        // Passwort-Regeln
        // Passwort-Identität
        if (!password.equals(password1)){
            // TODO: Toast
            Toast.makeText(getApplicationContext(), "Pls. check passwords!", Toast.LENGTH_LONG).show();
        }
       else {
            FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password )
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String msg;
                    if (task.isSuccessful()) {
                        msg = "User created.";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        msg = "Failed :" + task.getException();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}