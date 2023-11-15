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


public class MainActivity extends AppCompatActivity {

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
        if (item.getItemId() == R.id.menu_item1){
            Toast.makeText(getApplicationContext(), "Item 1", Toast.LENGTH_LONG).show();
        } else if ( item.getItemId() == R.id.menu_item2){
            Toast.makeText(getApplicationContext(), "Item 2", Toast.LENGTH_LONG).show();
        }
        else if ( item.getItemId() == R.id.menu_item3){
            Toast.makeText(getApplicationContext(), "Item 3", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart");
        //TODO: Nur zum Testen, später raus!
        Intent intent = new Intent( getApplication(), PostActivity.class);
        startActivity( intent );
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