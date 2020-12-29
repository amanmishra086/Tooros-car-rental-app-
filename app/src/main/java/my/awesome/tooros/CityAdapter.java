package my.awesome.tooros;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CityAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<ModelCity> modelCitys;

    public CityAdapter(Context context,int resource, ArrayList objects) {
        super(context,0,objects);
        this.context = context;
        this.modelCitys =objects ;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view=LayoutInflater.from(context).inflate(R.layout.city,parent,false);
        TextView textView=view.findViewById(R.id.cityname);
        textView.setText(modelCitys.get(position).getCity());
        String id=modelCitys.get(position).getCity_id();
        String city=modelCitys.get(position).getCity();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Date", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("city",""+city);
        myEdit.putString("cityid",""+id);
        myEdit.apply();
      //  Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
        return view;
    }
}
