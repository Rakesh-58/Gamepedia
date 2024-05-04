package com.example.gamepedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import com.example.gamepedia.databinding.ActivityDetailedBinding;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class DetailedActivity extends AppCompatActivity {

    ActivityDetailedBinding binding;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,allgames,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
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
                redirectActivity(DetailedActivity.this, MainActivity2.class);
            }
        });

        allgames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DetailedActivity.this, MainActivity.class);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DetailedActivity.this, ProfileActivity.class);
            }
        });

        Intent intent = this.getIntent();
        if (intent != null){

            String key = intent.getStringExtra("key");
            progressDialog=new ProgressDialog(DetailedActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            mDatabase=FirebaseDatabase.getInstance().getReference().child("game");
            DatabaseReference node=mDatabase.child(key);

            node.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    binding.detailName.setText(value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    //kachow
                }
            });
            node.child("synopsis").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    binding.synopsis.setText(value);
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    //kachow
                }
            });
            storageReference=FirebaseStorage.getInstance().getReference(key+".jpg");
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DetailedActivity.this).load(uri.toString()).into(binding.detailImage);

                }
            });
            node.child("youtube").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    String video="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+value+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
                    binding.webView.loadData(video, "text/html","utf-8");
                    binding.webView.getSettings().setJavaScriptEnabled(true);
                    binding.webView.setWebChromeClient(new WebChromeClient());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    //kachow
                }
            });
            storageReference=FirebaseStorage.getInstance().getReference().child(key);
            storageReference.child("img1.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DetailedActivity.this).load(uri.toString()).into(binding.img1);
                }
            });
            storageReference.child("img2.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DetailedActivity.this).load(uri.toString()).into(binding.img2);
                }
            });
            storageReference.child("img3.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DetailedActivity.this).load(uri.toString()).into(binding.img3);
                }
            });
            storageReference.child("img4.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DetailedActivity.this).load(uri.toString()).into(binding.img4);
                }
            });
            storageReference.child("img5.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DetailedActivity.this).load(uri.toString()).into(binding.img5);
                }
            });
            storageReference.child("img6.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DetailedActivity.this).load(uri.toString()).into(binding.img6);
                }
            });

        }
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