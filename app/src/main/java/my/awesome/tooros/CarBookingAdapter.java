package my.awesome.tooros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarBookingAdapter extends RecyclerView.Adapter<CarBookingAdapter.viewHolder> {
    ArrayList<CarBookingModel>carBookingModels;
    Context context;

    public CarBookingAdapter(ArrayList<CarBookingModel> carBookingModels, Context context) {
        this.carBookingModels = carBookingModels;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carnooking, parent, false);
        return new CarBookingAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CarBookingModel carBookingModel=carBookingModels.get(position);
       int pos1=carBookingModels.indexOf(carBookingModel);
        holder.carimage.setImageResource(carBookingModel.getCarimage());
        holder.fueltype.setText(carBookingModel.getFueltype());
        holder.price.setText(carBookingModel.getPrice());
        holder.seat.setText(carBookingModel.getSeat());
        holder.geartype.setText(carBookingModel.getGeartype());
        holder.baggage.setText(carBookingModel.getBaggage());
        holder.status.setText(carBookingModel.getStatus());
        holder.carname.setText(carBookingModel.getCarname());
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do whatever require to do
            }
        });
    }

    @Override
    public int getItemCount() {
        return carBookingModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
ImageView carimage;
        TextView fueltype;
        TextView price;
        TextView seat;
        TextView geartype;
        TextView baggage;
        TextView status;
        TextView carname;
    Button book;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            carimage=itemView.findViewById(R.id.carimage);
            carname=itemView.findViewById(R.id.carname);
            fueltype=itemView.findViewById(R.id.fueltype);
            seat=itemView.findViewById(R.id.totalseat);
            price=itemView.findViewById(R.id.price);
            geartype=itemView.findViewById(R.id.geartype);
            baggage=itemView.findViewById(R.id.baggage);
            status=itemView.findViewById(R.id.status);


        }
    }
    }
