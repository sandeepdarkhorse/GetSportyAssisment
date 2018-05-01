package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import main.darkhorse.com.getsportyassisment.R;


public class AwardResultAdapter extends RecyclerView.Adapter<AwardResultAdapter.MyViewHolder>
{

    private final Context context;

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<Award> moviesList;

    public AwardResultAdapter(List<Award> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.achievement_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Award best_result = moviesList.get(position);





        holder.degree.setText(best_result.getNameOfAward() );
        //  holder.org_name.setText();
        holder.stream.setText(best_result.getDescription());
        holder.degree_duraction.setText(best_result.getDate());




    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView degree, org_name, stream, degree_duraction;


        public MyViewHolder(View view) {
            super(view);

            degree = (TextView) view.findViewById(R.id.degree);
            org_name = (TextView) view.findViewById(R.id.org_name);
            stream = (TextView) view.findViewById(R.id.stream);
            degree_duraction = (TextView) view.findViewById(R.id.degree_duraction);
            org_name.setVisibility(View.GONE);



        }
    }


}
