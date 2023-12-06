package de.hawlandshut.pluto24;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    static final String TEST_MAIL = "fhgreipl@gmail.com";
    static final String TEST_PASSWORD = "123456";

    static final String TAG ="xx MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "called onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_test_auth){
            doTestAuth();
        }

        if (item.getItemId() == R.id.menu_create_user){
            doCreateUser();
        }

        if (item.getItemId() == R.id.menu_delete_user){
            doDeleteUser();
        }

        if (item.getItemId() == R.id.menu_reset_password){
            doResetPassword();
        }
        
        if (item.getItemId() == R.id.menu_activ_mail){
            doSendActivationMail();
        }

        if (item.getItemId() == R.id.menu_sign_in){
            doSignIn();
        }

        if (item.getItemId() == R.id.menu_sign_out){
            doSignOut();
        }
        
        return super.onOptionsItemSelected(item);

    
    }

    private void doSignOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String msg;
        if (user != null){
            FirebaseAuth.getInstance().signOut();
            msg = "Signed out";
        }
        else {
            msg = "No user! Pls. sign in first.";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

    }

    private void doSignIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Toast.makeText(getApplicationContext(), "Bitte erst abmelden!", Toast.LENGTH_LONG).show();
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword( TEST_MAIL, TEST_PASSWORD)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){ // Erfolgsfall
                                Toast.makeText(getApplicationContext(), "Your are signed in!", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "User signed in");
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

    private void doDeleteUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Toast.makeText(getApplicationContext(), "Bitte erst anmelden!", Toast.LENGTH_LONG).show();
        }
        else {
            AuthCredential credential = EmailAuthProvider.getCredential(TEST_MAIL, TEST_PASSWORD);
            user.reauthenticate( credential )
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){ // Erfolgsfall
                                Toast.makeText(getApplicationContext(), "Reauth fine!", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Reauth fine!");
                                doFinalDeletion();
                            }
                            else { // Fehlerfall
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
                                // finish();
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

    private void doResetPassword() {
        FirebaseAuth.getInstance().sendPasswordResetEmail( TEST_MAIL )
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) { // Erfolgsfall
                            Toast.makeText(getApplicationContext(), "Reset Mail sent.", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Reset - Mail sent.");
                            // finish();
                        } else { // Fehlerfall
                            Toast.makeText(getApplicationContext(),
                                    "Sending mail failed " + task.getException(),
                                    Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Sending mail failed " + task.getException());
                        }
                    }
                });
    }

    private void doCreateUser() {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(TEST_MAIL, TEST_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String msg;
                                if (task.isSuccessful()){
                                    msg = "User created";
                                } else {
                                    msg = "Failed :" + task.getException();
                                }
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            }
                        }
                );
    }

    private void doTestAuth() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String msg = "leer";
        if (user != null){
            msg = "User : " + user.getEmail() + " (Ver: " + user.isEmailVerified() +")";
        }
        else {
            msg = "No user sign in";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart");
        //TODO: Nur zum Testen, später raus!
        //Intent intent = new Intent( getApplication(), PostActivity.class);
        //startActivity( intent );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "called onResume");
    }

    @Override
    protected void onPause() {
        // TODO: remove later!
        super.onPause();
        Log.d(TAG, "called onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "called onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "called onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "called onRestart");
    }
}