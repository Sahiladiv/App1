package com.example.searchanimal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.searchanimal.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    Button searchButton;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        searchButton = (Button)findViewById(R.id.search);
        MainActivity s = new MainActivity();

        searchButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchText = (EditText)findViewById(R.id.searchAnimal);
            String mySearch = String.valueOf(searchText.getText());

            DocumentReference docref = FirebaseFirestore.getInstance().collection("animals").document(mySearch);
            docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override

                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        if(doc.exists()){


                            String common = (String) doc.get("Common_Name");
                            String scientific = (String)doc.get("Scientific_Name");
                            String foodEaten = (String) doc.get("Food");
                            String locationFound = (String)doc.get("Location");

                            Intent intent = new Intent(getApplicationContext(),SearchedAnimal.class);
                            intent.putExtra("Common Name",common);
                            intent.putExtra("Scientific Name",scientific);
                            intent.putExtra("Food",foodEaten);
                            intent.putExtra("Location",locationFound);

                            startActivity(intent);
                        }
                        else{
                            String t = "No data";

                            Log.d("Error:","No data");
                        }

                    }



                }
            });
        }

    });


    }

}