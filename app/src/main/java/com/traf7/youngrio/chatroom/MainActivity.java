package com.traf7.youngrio.chatroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ActivityCallback{
    //https://blog.avenuecode.com/realtime-chats-with-firebase-in-android

//    Button debug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, LoginFragment.newInstance())
                .commit();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
//
//        debug = findViewById(R.id.debug);
//
//        debug.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container, ChatFragment.newInstance())
//                        .commit();
//                debug.setVisibility(View.GONE);
//            }
//        });
    }

    @Override
    public void openChat() {
        replaceFragment(ChatFragment.newInstance());
    }

    @Override
    public void openCreateAccount() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CreateAccountFragment.newInstance())
                .commit();
    }

    @Override
    public void logout() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


}
