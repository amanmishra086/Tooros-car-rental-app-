package my.awesome.tooros;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        final CarBookingModel carBookingModel=carBookingModels.get(position);
       int pos1=carBookingModels.indexOf(carBookingModel);
        //holder.carimage.setImageResource(carBookingModel.getCarimage());
        Picasso.with(context).load(carBookingModel.getCarimage().replace("http","https")).fit().centerInside().into(holder.carimage);
        holder.note.setText(carBookingModel.getNote());

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


        int bookCount=Integer.parseInt(carBookingModel.getBookCount());
        int mtCount=Integer.parseInt(carBookingModel.getMtCount());

        if(bookCount==0 && mtCount==0){
            holder.status.setText("Book Now");
            holder.buttonlinearlayout.setBackgroundResource(R.drawable.redbutton);

            final String finalFuel = fuel;
            final String finalGear = gear;
            holder.status.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context,PaymentPage.class);

//                    intent.putExtra("carimage",carBookingModel.getCarimage());
//                    intent.putExtra("carname",""+carBookingModel.getCarname().toString());
//                    intent.putExtra("geartype",""+ finalGear);
//                    intent.putExtra("fuel",""+finalFuel);
//                    intent.putExtra("car_id",carBookingModel.getCar_id());

                    SharedPreferences sharedPreferences2 = context.getSharedPreferences("sharedpref3", MODE_PRIVATE);
                    final SharedPreferences.Editor myEdit = sharedPreferences2.edit();
                    myEdit.putString("carimage",carBookingModel.getCarimage());
                    myEdit.putString("carname",""+carBookingModel.getCarname().toString());
                    myEdit.putString("geartype",""+ finalGear);
                    myEdit.putString("fuel",""+finalFuel);
                    myEdit.putString("car_id",carBookingModel.getCar_id());
                    myEdit.apply();

                    context.startActivity(intent);

                }
            });

        }else {

            holder.status.setText(" Booked ");
            holder.buttonlinearlayout.setBackgroundResource(R.drawable.redbutton2);
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();

                }
            });
        }

        holder.carname.setText(carBookingModel.getCarname());


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
        TextView note;
        LinearLayout buttonlinearlayout;
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
            note=itemView.findViewById(R.id.textView13);
            buttonlinearlayout=itemView.findViewById(R.id.buttonlinearLayout);

        }
    }
    }
