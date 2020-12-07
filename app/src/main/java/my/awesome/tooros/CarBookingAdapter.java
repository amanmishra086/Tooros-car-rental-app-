package my.awesome.tooros;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarBookingAdapter extends RecyclerView.Adapter<CarBookingAdapter.viewHolder> {
    ArrayList<CarBookingModel>carBookingModels;
   final Context context;

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

        String fuel="";
        switch (Integer.parseInt(carBookingModel.getFueltype())){

            case 1:
                fuel="Petrol";
                break;
            case 2:
                fuel="Diesel";
                break;
            case 3:
                fuel="Electric";
                break;
            case 4:
                fuel="CNG";
                break;
            case 5:
                fuel="LPG";
                break;

        }


        holder.fueltype.setText(fuel);
        holder.price.setText("₹"+carBookingModel.getPrice());
        holder.seat.setText(carBookingModel.getSeat()+" seat");

        String gear="";
        switch (Integer.parseInt(carBookingModel.getGeartype())) {

            case 1:
                gear = "Automatic";
                break;
            case 2:
                gear = "Manual";
                break;

        }
        holder.geartype.setText(gear);

        holder.baggage.setText(carBookingModel.getBaggage()+" Baggage");
       // holder.status.setText(carBookingModel.getStatus());
        holder.status.setText("Book Now");
        holder.carname.setText(carBookingModel.getCarname());
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context,PaymentPage.class);
//                context.startActivity(intent);
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
