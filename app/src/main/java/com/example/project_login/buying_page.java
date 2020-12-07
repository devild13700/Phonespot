package com.example.project_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class buying_page extends AppCompatActivity {
    TextView specs,name;
    RatingBar ratingBar;
    ImageView imageView;
    Button button;
    String buyLink;
    ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_page);
        ratingBar = findViewById(R.id.ratingBar);
        imageView = findViewById(R.id.imageView2);
        name = findViewById(R.id.textView18);
        registerForContextMenu(imageView);
        specs = findViewById(R.id.textView17);

        button = findViewById(R.id.button4);
        if (getIntent() != null) {
            buyLink=getIntent().getStringExtra("link");
            specs.setText(getIntent().getStringExtra("specs"));
            ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("rating")));
            Picasso.get().load(getIntent().getStringExtra("img")).into(imageView);
            name.setText(getIntent().getStringExtra("name"));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    goToLink(buyLink);
                }
            });
        }
    }
    //Context Menu Declaration and The Toast message
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Phone Image Actions");
    }
    //When The Context Menu is clicked the We Will The Do The Specific Action !
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.download){
            goToLink(getIntent().getStringExtra("img"));
        }
        else if(item.getItemId()==R.id.flip){
            String gotToFlipkart=String.format("https://www.flipkart.com/search?q=%s",getIntent().getStringExtra("name").replace(" ","+"));
            goToLink(gotToFlipkart);
        }
        else if(item.getItemId()==R.id.gsm){
            String gotToGsm=String.format("https://www.gsmarena.com/res.php3?sSearch=%s",getIntent().getStringExtra("name").replace(" ","+"));
            goToLink(gotToGsm);
        }
        else{
            return false;
        }
        return true;
    }
    private void goToLink(String linkStr){
        String url = linkStr;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    //Options Menu With the Sharing Feature Which can Share The Link of The Phone !
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menubar_menu,menu);
        MenuItem shareItem = menu.findItem(R.id.share);
        shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent intentShareLink = new Intent(Intent.ACTION_SEND);
        intentShareLink.setType("text/plain");
        intentShareLink.putExtra(Intent.EXTRA_TEXT,"Buy The Latest Samrtphone "+buyLink+" Exclusively From Phonespot and Get 35% Off");
        shareActionProvider.setShareIntent(intentShareLink);
        return true;
    }


}
