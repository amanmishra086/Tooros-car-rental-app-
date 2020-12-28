package my.awesome.tooros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.viewHolder>  {
    Context context;
    ArrayList<ModelCity> modelCitys;

    public CityAdapter(Context context, ArrayList<ModelCity> modelCitys) {
        this.context = context;
        this.modelCitys =modelCitys ;
    }

    @NonNull
    @Override
    public CityAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city, parent, false);
        return new CityAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CityAdapter.viewHolder holder, int position) {
        final ModelCity modelCity=modelCitys.get(position);
        holder.Cityname.setText(modelCity.getCity());
        holder.Cityname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String id=modelCity.getCity_id().toString();
                Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelCitys.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView Cityname;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Cityname=itemView.findViewById(R.id.cityname);
           // imageView=itemView.findViewById(R.id.senitizationimage);
        }
    }
}
