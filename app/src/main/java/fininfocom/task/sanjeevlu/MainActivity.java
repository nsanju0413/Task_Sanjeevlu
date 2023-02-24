package fininfocom.task.sanjeevlu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    MyAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Detail> detailsList;

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
                return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.SortAge:
                sortAge();

                return true;
            case R.id.SortName:
               sortName();
                return true;
            case R.id.SortCity:
                sortCity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void sortCity() {
        Collections.sort(detailsList,new Comparator<Detail>()
        {
            public int compare(Detail o1, Detail o2) {
                return o1.getCity().compareTo(o2.getCity()
                );
            }
        });

        adapter.notifyDataSetChanged();
    }

    private void sortName() {
        Collections.sort(detailsList,new Comparator<Detail>()
        {
            public int compare(Detail o1, Detail o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        adapter.notifyDataSetChanged();
    }

    private void sortAge() {
        Collections.sort(detailsList,new Comparator<Detail>()
    {
        public int compare(Detail o1, Detail o2) {
            return o1.getAge() - o2.getAge();
            //return o1.getAge().compareTo(o2.getAge());
        }
    });

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview1);
        databaseReference=FirebaseDatabase.getInstance().getReference("Details");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        detailsList=new ArrayList();
        adapter=new MyAdapter(this, detailsList);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren())
                {
                    Detail details1=data.getValue(Detail.class);
                    detailsList.add(details1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}