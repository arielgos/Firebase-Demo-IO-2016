package com.agos.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.agos.firebase.entities.Message;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    String userName = "";

    FirebaseDatabase database;

    DatabaseReference databaseReference;

    List<Message> messages = new ArrayList<Message>();

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        //iniciamos Firebase

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("messages");

        init();
    }

    private void init() {

        final EditText txtMessage = (EditText) findViewById(R.id.message);

        ((Button) findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtMessage.getText().toString() != "" && txtMessage.getText().toString().length() > 1) {
                    //enviamos mensaje a Firebase
                    Message message = new Message();
                    message.setUserName(userName);
                    message.setMessage(txtMessage.getText().toString());
                    message.setDate(new Date());
                    databaseReference.child(databaseReference.push().getKey()).setValue(message);
                    //actualizamos la interfaz
                    txtMessage.setText("");
                }
            }
        });

        MessageAdapter adapter = new MessageAdapter(Chat.this, messages);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);


        ChildEventListener listener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String lastKey) {
                Message message = dataSnapshot.getValue(Message.class);
                messages.add(message);
                ((MessageAdapter) list.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener(listener);

    }


    public class MessageAdapter extends ArrayAdapter<Message> {
        public MessageAdapter(Context context, List<Message> messages) {
            super(context, 0, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message message = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            }
            TextView tvMessage = (TextView) convertView.findViewById(R.id.message);
            TextView tvUserName = (TextView) convertView.findViewById(R.id.userName);
            TextView tvDate = (TextView) convertView.findViewById(R.id.date);
            tvMessage.setText(message.getMessage());
            tvUserName.setText(message.getUserName());
            tvDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(message.getDate()));
            return convertView;
        }
    }

}
