package com.example.leedayeon.listdetail;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LG_note1 on 2016-08-22.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Recycler_item> items;
    int item_layout;

    public RecyclerAdapter(Context context, List<Recycler_item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        ViewHolder viewHolder= new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.image.setImageResource(items.get(position).getImage());
        holder.title.setText(items.get(position).getTitle());
        holder.desc.setText(items.get(position).getDesc());
        holder.date.setText(items.get(position).getDate());
        holder.situation.setImageResource(items.get(position).getSituation());

        holder.situation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.situation.setImageResource(R.mipmap.join);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView desc;
        TextView date;
        ImageView situation;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.imageView);
            title=(TextView)itemView.findViewById(R.id.textViewTitle);
            desc =(TextView)itemView.findViewById(R.id.textViewDesc);
            cardview=(CardView)itemView.findViewById(R.id.messageRecyclerView);
            date=(TextView)itemView.findViewById(R.id.textViewDate);
            situation=(ImageView)itemView.findViewById(R.id.imageViewSitu);
        }
    }
}
