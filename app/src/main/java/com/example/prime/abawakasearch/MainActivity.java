package com.example.prime.abawakasearch;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//public class MainActivity extends Fragment {
//    private View groupFragmentview;
//    private ListView list_view;
//    private ArrayAdapter<String> arrayAdapter;
//    private ArrayList<String> list_of_groups = new ArrayList<>();
//
//    private DatabaseReference groupRef;
//
//    public MainActivity() {
//        // Required empty public constructor
//    }
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_teachers);
//
//
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
////        groupFragmentview = inflater.inflate(R.layout.activity_main, container, false);
//
//
//        groupRef = FirebaseDatabase.getInstance().getReference().child("Groups");
//
//        initializeFields();
//
//        retriveAndDisplayGroups();
//
//        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String currentGroupName = adapterView.getItemAtPosition(position).toString();
//
////                Intent groupChatIntent = new Intent(getContext(), GroupChatActivity.class);
////                groupChatIntent.putExtra("groupName", currentGroupName);
////                startActivity(groupChatIntent);
//
//            }
//        });
//
//
//        return groupFragmentview;
//    }
//
//
//    private void initializeFields() {
//
//        list_view = (ListView) groupFragmentview.findViewById(R.id.list_view);
//        arrayAdapter = new ArrayAdapter<String>(
//                getContext(), android.R.layout.simple_list_item_1, list_of_groups);
//        list_view.setAdapter(arrayAdapter);
//    }
//
//    private void retriveAndDisplayGroups() {
//
//        groupRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Set<String> set = new HashSet<>();
//                Iterator iterator = dataSnapshot.getChildren().iterator();
//
//                while (iterator.hasNext()){
//
//                    set.add(((DataSnapshot)iterator.next()).getKey());
//
//                }
//
//                list_of_groups.clear();
//                list_of_groups.addAll(set);
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//}

public class MainActivity extends AppCompatActivity {

    // Define the Teacher Firebase DatabaseReference
    private DatabaseReference businessRef;

    // Define a String ArrayList for the teachers
    private ArrayList<String> businessList = new ArrayList<>();

    // Define a ListView to display the data
    private ListView listViewBussiness;

    // Define an ArrayAdapter for the list
    private ArrayAdapter<String> arrayAdapter;

    /**
     * onCreate method
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Associate the Teacher Firebase Database Reference with the database's teacher object
        businessRef = FirebaseDatabase.getInstance().getReference();
        businessRef = businessRef.child("business");

        // Associate the teachers' list with the corresponding ListView
        listViewBussiness = (ListView) findViewById(R.id.list_view);

        // Set the ArrayAdapter to the ListView
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, businessList);
        listViewBussiness.setAdapter(arrayAdapter);

        // Attach a ChildEventListener to the teacher database, so we can retrieve the teacher entries
        businessRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {

                    set.add(((DataSnapshot) iterator.next()).getKey());

                }

                businessList.clear();
                businessList.addAll(set);
                arrayAdapter.notifyDataSetChanged();

            }
//                .addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                // Get the value from the DataSnapshot and add it to the teachers' list
//                String teacher = dataSnapshot.getValue(String.class);
//                businessList.add(teacher);
//
//                // Notify the ArrayAdapter that there was a change
//                arrayAdapter.notifyDataSetChanged();
//            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewBussiness.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String currentGroupName = adapterView.getItemAtPosition(position).toString();
            }
        });

    }
}