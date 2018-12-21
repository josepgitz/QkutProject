package ke.co.qkut.qkut.util.newtork.real;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Remote {
    AppCompatActivity appCompatActivity;
    static DatabaseReference databaseReference;
  public void  getDbReal(final AppCompatActivity appCompatActivity){
      this.appCompatActivity=appCompatActivity;
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
   //   firebaseDatabase.setPersistenceEnabled(true);
        databaseReference=firebaseDatabase.getReference("users");
       databaseReference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               User user=dataSnapshot.getValue(User.class) ;
               Toast.makeText(appCompatActivity,user.getEmail(),Toast.LENGTH_SHORT).show();
               Log.e("user name",dataSnapshot.getChildrenCount()+"");
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
       });
       User user= new User();
       user.setName("This is the user");
       user.setEmail("There they are");
        addUser(user);
      //Log.e("root",databaseReference.getRooat().getKey());
  }
  public static void addUser(User user){

     databaseReference.child(databaseReference.push().getKey()).setValue(user);
  }


}
