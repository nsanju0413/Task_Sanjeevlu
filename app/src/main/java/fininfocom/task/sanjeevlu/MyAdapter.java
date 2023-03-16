package fininfocom.task.sanjeevlu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
       ArrayList<Detail> details;

    public MyAdapter(Context context, ArrayList<Detail> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Detail detail=details.get(position);
        String n=detail.getName();
        String a=detail.getCity();
        int V=detail.getAge();
        String s=String.valueOf(V);
        holder.nameV.setText("Name:"+n);
        holder.addressV.setText("Adress:" +a);
        holder.ageV.setText("Age :"+s);
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameV,addressV,ageV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameV=itemView.findViewById(R.id.nameText);
            addressV=itemView.findViewById(R.id.addressText);
            ageV=itemView.findViewById(R.id.ageTextView);
        }
    }
}
