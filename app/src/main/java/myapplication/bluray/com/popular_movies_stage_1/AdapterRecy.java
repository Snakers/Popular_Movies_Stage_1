package myapplication.bluray.com.popular_movies_stage_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class AdapterRecy extends RecyclerView.Adapter<AdapterRecy.MovieVh> {
    private Movies[] m;
    private Context context;
    final private ListenerClick listenerClick;

    AdapterRecy(Context context, ListenerClick listenerClick) {
        this.context = context;
        this.listenerClick = listenerClick;
    }

    public interface ListenerClick {
        void onItemListner(int itemIndex);
    }

    @Override
    public AdapterRecy.MovieVh onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layout = R.layout.activity_main;
        //boolean shouldAttachToParentImmediatly = false;
        View view = inflater.inflate(layout, parent, false);
        return new MovieVh(view);
    }

    @Override
    public void onBindViewHolder(final AdapterRecy.MovieVh holder, int position) {
        m = getMovies();

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerClick.onItemListner(holder.getAdapterPosition());
            }
        });
        Picasso.with(context).load((m[holder.getAdapterPosition()].getPosterPath())).into(holder.img);
        holder.img.setAdjustViewBounds(true);
        holder.img.setAdjustViewBounds(true);

    }

    @Override
    public int getItemCount() {
        if (m == null || m.length == 0) {
            Log.v("empty", "Hold On");
            return -1;
        }
        return m.length;
    }

    class MovieVh extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView img;

        public MovieVh(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_v);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            int item = getAdapterPosition();
            listenerClick.onItemListner(item);
        }
    }

    public void setWeatherData(Movies[] m) {
        this.m = m;
        notifyDataSetChanged();
    }

    public Movies[] getMovies() {
        return m;
    }
}
