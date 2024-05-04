package com.example.gamepedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.gamepedia.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;    
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener{

    ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<ListData> dataList;
    private MyAdapter adapter;
    StorageReference storageReference;
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,allgames,profile;

    String image;
    String[] keyList={"assassin","daysgone","forza","godofwar","gta5","mario","rdr2","spider","tomb","zero"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawerLayout=findViewById(R.id.drawerLayout);
        menu=findViewById(R.id.menu);
        home=findViewById(R.id.home);
        allgames=findViewById(R.id.allgames);
        profile=findViewById(R.id.profile);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                redirectActivity(MainActivity.this, MainActivity2.class);
            }
        });

        allgames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, ProfileActivity.class);
            }
        });

        String[] nameList = {"Assassin Creed Mirage","Days Gone","Forza Horizon 5","God Of War: Ragnork","Grand Theft Auto 5","Super Mario Bros Wonder","Red Dead Redemption 2","Marvel's SpiderMan 2","Shadow Of Tomb Rider","Horizon Zero Dawn"};
       Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new MyAdapter(this, dataList);


        for(int i=0;i<keyList.length;i++)
        {
            ListData data=new ListData(nameList[i],null,keyList[i]);
            dataList.add(data);
        }

        for(ListData d:dataList) {
            storageReference = FirebaseStorage.getInstance().getReference(d.key+".jpg");
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override

                public void onSuccess(Uri uri) {
                    image = uri.toString();
                    d.setImage(image);
                }
            });
        }
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
        intent.putExtra("key",keyList[position]);
        startActivity(intent);
    }
    public static void openDrawer(DrawerLayout drawerLayout)
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity)
    {
        Intent intent=new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

}


