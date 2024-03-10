package com.yuri.quiorb.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private static FirebaseAuth auth;
    private static DatabaseReference reference;

    public static FirebaseAuth userGetAuth(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static String getUID(){
        return userGetAuth().getCurrentUser().getUid();
    }

    public static DatabaseReference getReference(){
        if(reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    public static DatabaseReference getReferenceUsuarios(){
        if(reference == null){
            reference = getReference().child("usuarios");
        }
        return reference;
    }
}
