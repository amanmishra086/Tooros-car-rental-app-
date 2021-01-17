package my.awesome.tooros;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class Offer_adapter extends RecyclerView.Adapter<Offer_adapter.viewHolder> {
    ArrayList<Guidlines_model> offer_model_arraylist;
    Context context;

    public Offer_adapter(ArrayList<Guidlines_model> offer_model_arraylist, Context context) {
        this.offer_model_arraylist = offer_model_arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_guidlines, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Guidlines_model offer_model1=offer_model_arraylist.get(position);

        //holder.imageView.setImageResource(offer_model1.getImage());
        String imageurl=offer_model1.getImgurl();

        if (TextUtils.isEmpty(imageurl)) {
            // option 1: cancel Picasso request and clear ImageView
//            Picasso
//                    .with(context)
//                    .cancelRequest(holder.imageView);
//
//            holder.imageView.setImageDrawable(null);

            // option 2: load placeholder with Picasso

        Picasso
                .with(context)
                .load(R.drawable.offertooros)
                .into(holder.imageView);


        }
        else {
            Picasso
                    .with(context)
                    .load(imageurl)
                    .fit().centerCrop() // will explain later
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return offer_model_arraylist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.offerimage);
        }
    }
}
