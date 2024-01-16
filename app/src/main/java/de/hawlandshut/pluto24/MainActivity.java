package de.hawlandshut.pluto24;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import de.hawlandshut.pluto24.Model.Post;


public class MainActivity extends AppCompatActivity {

    static final String TAG ="xx MainActivity";

    ListenerRegistration  mListenerRegistration;
    CustomAdapter mAdapter;
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new CustomAdapter();
        mRecyclerView = findViewById( R.id.recycler_view);
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ));
        mRecyclerView.setAdapter( mAdapter );
        Log.d(TAG, "called onCreate");
    }

    ListenerRegistration addQueryListener(){
        Query query = FirebaseFirestore.getInstance().collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(5);
        return query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                Log.w(TAG, "Number of docs in Snapshot"+snapshots.size());
                mAdapter.mPostList.clear();
                for (QueryDocumentSnapshot doc : snapshots) {
                    Post addedPost;
                    if (doc.get("uid") != null) {
                        Log.d(TAG, "SnapshotListener : received " + doc.getId());
                        addedPost = Post.fromDocument( doc );
                        mAdapter.mPostList.add( addedPost );
                        Log.d(TAG,(String) doc.get("body") );
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }); // Hier weiter
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplication(), SignInActivity.class);
            startActivity(intent);
        }
        else {
            mListenerRegistration = addQueryListener();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_manage_account) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(getApplication(), ManageAccountActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "You should not be able to do this", Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId() == R.id.menu_post) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(getApplication(), PostActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "You should not be able to do this", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}