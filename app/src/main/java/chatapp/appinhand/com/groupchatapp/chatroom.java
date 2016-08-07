package chatapp.appinhand.com.groupchatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Superoft on 8/3/2016.
 */
public class chatroom extends AppCompatActivity{

    EditText chatmsg;
    Button send;
TextView allMsgs;
String username,roomname;

String random_key;
    private DatabaseReference root;
    private  String chat_msg,chat_username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);
        chatmsg= (EditText) findViewById(R.id.typemsg);
        send= (Button) findViewById(R.id.send);
        allMsgs= (TextView) findViewById(R.id.chatmsgs);
username=getIntent().getExtras().get("username").toString();

        roomname=getIntent().getExtras().get("roomname").toString();

        setTitle("Room name: "+roomname);
        final DatabaseReference root= FirebaseDatabase.getInstance().getReference().child(roomname);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map1=new HashMap<String, Object>();
               random_key= root.push().getKey();
root.updateChildren(map1);

DatabaseReference mesaageroot=root.child(random_key);
                HashMap<String,Object> map2=new HashMap<String, Object>();
                map2.put("name",username.toString());
                map2.put("msg", chatmsg.getText().toString());

                mesaageroot.updateChildren(map2);

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversion(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversion(dataSnapshot);

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
        });

    }

    private void append_chat_conversion(DataSnapshot dataSnapshot)
    {

//        Set<String> set=new HashSet<String>();
        Iterator i =dataSnapshot.getChildren().iterator();

        while ((i.hasNext()))
        {
            chat_msg=(String)(((DataSnapshot) i.next()).getValue());
            chat_username=(String)(((DataSnapshot) i.next()).getValue());

        }
        allMsgs.append(chat_username+"\n"+chat_msg+"\n");


    }

}
