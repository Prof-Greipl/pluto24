package de.hawlandshut.pluto24.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Post {
    String title;
    String body;
    Date createdAt;
    String uid;
    String email;
    String source;

    public Post(String title, String body, Date createdAt, String uid, String email, String source) {
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.uid = uid;
        this.email = email;
        this.source = source;
    }

    public static Post fromDocument(DocumentSnapshot doc){
        String title = (String) doc.get("title");
        String body = (String) doc.get("body");
        Date createdAt = ((Timestamp) doc.get("createdAt")).toDate();
        String uid  = (String) doc.get("uid");
        String email  = (String) doc.get("email");
        String source = (String) doc.get("source");
        return new Post( title, body, createdAt, uid, email, source);
    }
}


