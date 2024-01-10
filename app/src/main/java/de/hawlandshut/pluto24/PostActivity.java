package de.hawlandshut.pluto24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG ="xx PostActivity";

    EditText title;
    EditText text;
    Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        title = findViewById(R.id.post_edittext_title);
        text = findViewById(R.id.post_edittext_text);
        button_send = findViewById(R.id.post_button_post);
        button_send.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String my_title = title.getText().toString();
        String my_text = text.getText().toString();
        // Schreiben in die DB

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        postMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        postMap.put("title", my_title);
        postMap.put("body", my_text);
        postMap.put("source", "Android");
        postMap.put("createdAt", new Date());


        FirebaseFirestore.getInstance().collection("posts")
                .add(postMap)
                .addOnSuccessListener(
                        new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "Post successfully written.");
                                Toast.makeText(getApplicationContext(),
                                        "Post sent...", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Writing post failed" + e.getLocalizedMessage().toString());
                        Toast.makeText(getApplicationContext(),
                                "Sending failed...", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}
