package de.hawlandshut.pluto24;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    static final String TAG ="xx SignInActivity";
    EditText mEditTextEMail;
    EditText mEditTextPassword;

    Button mButtonSignIn;
    Button mButtonResetPassword;

    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mEditTextEMail = findViewById( R.id.sign_in_edit_text_email);
        mEditTextPassword = findViewById( R.id.sign_in_edit_text_password);
        mButtonCreateAccount = findViewById( R.id.sign_in_button_create_acccount);
        mButtonResetPassword = findViewById( R.id.sign_in_button_reset_password);
        mButtonSignIn = findViewById( R.id.sign_in_button_sign_in);

        mButtonCreateAccount.setOnClickListener( this );
        mButtonResetPassword.setOnClickListener( this );
        mButtonSignIn.setOnClickListener( this );

        // TODO: Only for testing - remove later
        mEditTextEMail.setText("fhgreipl@gmail.com");
        mEditTextPassword.setText("123456");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button_sign_in) {
            String email = mEditTextEMail.getText().toString();
            String password = mEditTextPassword.getText().toString();
            FirebaseAuth.getInstance().signInWithEmailAndPassword( email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){ // Erfolgsfall
                                Toast.makeText(getApplicationContext(), "Your are signed in!", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "User signed in");
                                finish();
                            }
                            else { // Fehlerfall
                                Toast.makeText(getApplicationContext(),
                                        "Sign in failed : " + task.getException(),
                                        Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Sign in failed : " + task.getException());
                            }
                        }
                    });
        }
    }
}