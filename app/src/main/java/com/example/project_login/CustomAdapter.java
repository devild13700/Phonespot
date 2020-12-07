package com.example.project_login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    ArrayList<Phone>phones;
    public CustomAdapter(Context context,ArrayList<Phone>phones){
        this.context=context;
        this.phones=phones;
    }
    @NonNull
    @Override

    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.row,null,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, final int position) {

        holder.name.setText(phones.get(position).getMake()+" "+phones.get(position).getPhoneName());
        holder.price.setText(phones.get(position).getPrice()+"");
        Log.d("priceShow",phones.get(position).getPrice()+"");
        Picasso.get().load(phones.get(position).getImageUrl()).into(holder.phoneImage);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=position;
                Intent intent=new Intent(context,buying_page.class);
                intent.putExtra("name",phones.get(i).getMake()+" "+phones.get(i).getPhoneName());
                intent.putExtra("rating",phones.get(i).getRating());
                intent.putExtra("img",phones.get(i).getImageUrl());
                intent.putExtra("specs","Price: "+phones.get(i).getPrice()+"\n\nROM: "+phones.get(i).getRom()+"\n\nRAM: "+phones.get(i).getRam()+"\n\nProcessor: "+phones.get(i).getProcessor()+"\n\nBattery: "+phones.get(i).getBattery());
                intent.putExtra("link",phones.get(i).getBuy_link());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView name,price;
        ImageView phoneImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.linear);
            name=itemView.findViewById(R.id.nameRow);
            price=itemView.findViewById(R.id.priceRow);
            phoneImage=itemView.findViewById(R.id.imageView);
        }

    }
}
