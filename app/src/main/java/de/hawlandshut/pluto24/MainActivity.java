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
import com.google.firebase.auth.AuthResult;
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
    }

    private void doSendActivationMail() {
    }

    private void doDeleteUser() {
    }

    private void doResetPassword() {
    }

    private void doCreateUser() {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(TEST_MAIL, TEST_PASSWORD)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String msg;
                                if (task.isSuccessful()){
                                    msg = "User created";
                                } else {
                                    msg = "Failed: " +task.getResult();
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
            msg = "User : " + user.getEmail();
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