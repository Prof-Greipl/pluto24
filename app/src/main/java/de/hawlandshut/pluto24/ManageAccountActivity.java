package de.hawlandshut.pluto24;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener{
    static final String TAG ="xx ManageAccount";

    // 1. Deklaration
    TextView mTextViewEmail;
    TextView mTextViewAccountVerified;
    TextView mTextViewId;

    Button mButtonSignOut;
    Button mButtonDeleteAccount;
    Button mButtonSendActivationMail;

    EditText mEditTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        // 2. Init UI Vars
        mTextViewEmail = findViewById( R.id.manage_account_textview_email);
        mTextViewAccountVerified = findViewById( R.id.manage_account_textview_account_verified);
        mTextViewId = findViewById( R.id.manage_account_textview_id);
        mButtonDeleteAccount = findViewById( R.id.manage_account_button_delete_acccount);
        mButtonSignOut = findViewById( R.id.manage_account_button_sign_out);
        mButtonSendActivationMail = findViewById( R.id.manage_account_button_send_activation_mail);
        mEditTextPassword = findViewById( R.id.manage_account_password);

        mButtonSignOut.setOnClickListener( this );
        mButtonSendActivationMail.setOnClickListener( this );
        mButtonDeleteAccount.setOnClickListener( this );

    }

    @Override
    protected void onStart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mTextViewEmail.setText("E-Mail : " + user.getEmail());
            mTextViewAccountVerified.setText("Konto verifziert : " + user.isEmailVerified());
            mTextViewId.setText("Technical Id : " + user.getUid());
        }
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.manage_account_button_sign_out){
            doSignOut();
        }
        if (view.getId() == R.id.manage_account_button_send_activation_mail){
            doSendActivationMail();
        }
        if (view.getId() == R.id.manage_account_button_delete_acccount){
            doDeleteAccount();
        }
    }



    private void doSendActivationMail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Toast.makeText(getApplicationContext(), "Bitte erst anmelden!", Toast.LENGTH_LONG).show();
        } else {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){ // Erfolgsfall
                                Toast.makeText(getApplicationContext(), "E-Mail was sent!", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Verificaion-Mail sent");
                            }
                            else { // Fehlerfall
                                Toast.makeText(getApplicationContext(),
                                        "Sending E-Mail failed! " + task.getException(),
                                        Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Sending Verification E-Mail failed! " + task.getException());
                            }
                        }
                    });
        }

    }

    private void doSignOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String msg;
        if (user != null){
            FirebaseAuth.getInstance().signOut();
            msg = "Signed out.";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            msg = "This should never happen";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

    }

    private void doDeleteAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(), "Bitte erst anmelden!", Toast.LENGTH_LONG).show();
        } else {

            String password = mEditTextPassword.getText().toString();
            if (password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Password is empty!", Toast.LENGTH_LONG).show();
                return;
            }
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            user.reauthenticate(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) { // Erfolgsfall
                                Toast.makeText(getApplicationContext(), "Reauth fine!", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Reauth fine!");
                                doFinalDeletion();
                            } else { // Fehlerfall
                                Toast.makeText(getApplicationContext(),
                                        "Reauth failed : " + task.getException(),
                                        Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Reauth failed : " + task.getException());
                            }
                        }
                    });
        }
    }

    private void doFinalDeletion() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(), "No user in final deletion.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failure: No user in FinalDeletion");
        } else {
            user.delete()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) { // Erfolgsfall
                                Toast.makeText(getApplicationContext(), "Account deleted.", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Account deleted");
                                finish();
                            } else { // Fehlerfall
                                Toast.makeText(getApplicationContext(),
                                        "Deletion failed!" + task.getException(),
                                        Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Deletion failed : " + task.getException());
                            }
                        }
                    });
        }
    }


}