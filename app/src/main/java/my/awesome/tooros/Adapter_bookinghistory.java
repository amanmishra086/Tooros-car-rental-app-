package my.awesome.tooros;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class Adapter_bookinghistory  extends RecyclerView.Adapter<Adapter_bookinghistory.viewHolder> {
    ArrayList<Model_bookinghistory> arrayList;
    Context context;
    ProgressDialog progressDialog;

    JsonHttpParse jsonhttpParse = new JsonHttpParse();
    String finalResult ;
    String HttpURL = "https://www.cakiweb.com/tooros/api/api.php";

    String booking_id;

    public Adapter_bookinghistory(ArrayList<Model_bookinghistory> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_bookinghistory.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new Adapter_bookinghistory.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_bookinghistory.viewHolder holder, int position) {
        final Model_bookinghistory model=arrayList.get(position);


        holder.startdate.setText(model.getStartdate());
        holder.enddate.setText(model.getEnddate());
        holder.carname.setText(model.getCarname());
        holder.price.append(""+model.getPrice());

//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 booking_id=model.getBooking_id();
                getInvoice("getInvoice",booking_id);

                Snackbar.make(v, "Booking Invoice Downloaded !!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("View", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(context,PdfViewActivity.class);
                                intent.putExtra("booking_id",booking_id);
                                context.startActivity(intent);
                                //context.startActivity(new Intent(context,PdfViewActivity.class));
                            }
                        })
                        .setActionTextColor(context.getResources().getColor(android.R.color.holo_green_dark))
                        .show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void getInvoice(final String method,final String booking_id){

        class UserLoginClass extends AsyncTask<String,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(context,"Loading...",null,true,true);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(httpResponseMsg);
                    String status = jsonObject2.getString("status");

                    if(status.equals("200")){

                        String base64string=jsonObject2.getString("base64file");

                        // Toast.makeText(ThankYou.this, ""+base64string, Toast.LENGTH_LONG).show();
                        try {
                            download(base64string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else{

                        String messege = jsonObject2.getString("msg");
                        Toast.makeText(context, messege, Toast.LENGTH_SHORT).show();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


                String jsonInputString="{\"method\":\"getInvoice\",\"booking_id\":\""+booking_id+"\"}";

                //  Toast.makeText(PaymentPage.this, ""+book_id, Toast.LENGTH_SHORT).show();

//                finalResult = jsonhttpParse.postRequest(method,Email,Password, HttpURL);
                finalResult = jsonhttpParse.postRequest(jsonInputString, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(method,booking_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void download(String base64string) throws IOException {

        base64string=base64string.replace("\r\n","");


        final File dwldsPath = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/"+booking_id+"_"+"Tooros_Invoice.pdf");
        //  final File dwldsPath = new File("/storage/emulated/0/Movies/Tooros_Booking_new.pdf");
        byte[] pdfAsBytes = Base64.getDecoder().decode(base64string);
        FileOutputStream os;
        os = new FileOutputStream(dwldsPath, false);
        os.write(pdfAsBytes);
        os.flush();
        os.close();



    }

    public class viewHolder extends RecyclerView.ViewHolder{
       TextView startdate,enddate,carname,price , invoice;
       LinearLayout linearLayout;
       CardView cardView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            startdate=itemView.findViewById(R.id.startdate);
            enddate=itemView.findViewById(R.id.enddate);
            carname=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            invoice=itemView.findViewById(R.id.invoicedownload);
            linearLayout=itemView.findViewById(R.id.linearlayout);
            cardView=itemView.findViewById(R.id.card1);
        }
    }
}
