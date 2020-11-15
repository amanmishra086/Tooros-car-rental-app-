package my.awesome.tooros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class Guidlines_adapter extends RecyclerView.Adapter<Guidlines_adapter.viewHolder> {
ArrayList<Guidlines_model> guidlines_models;
Context context;

    public Guidlines_adapter(ArrayList<Guidlines_model> guidlines_models, Context context) {
        this.guidlines_models = guidlines_models;
        this.context = context;
    }

    @NonNull
    @Override
    public Guidlines_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_guidlines, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Guidlines_adapter.viewHolder holder, int position) {
   Guidlines_model guidlines_model1=guidlines_models.get(position);
       // Picasso.with(context).load(guidlines_model1.getImage_url().replace("http","https")).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return guidlines_models.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.senitizationimage);
        }
    }
}
