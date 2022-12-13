package com.example.webthingclient.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webthingclient.ThingsList;
import com.example.webthingclient.databinding.ItemRowThingBinding;

import java.util.ArrayList;

public class ListThingAdapter extends RecyclerView.Adapter<ListThingAdapter.ListViewHolder> {

    private ArrayList<ThingsList> listThing;
    public ListThingAdapter(ArrayList<ThingsList> list) {
        this.listThing = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemRowThingBinding binding =  ItemRowThingBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ThingsList thing = listThing.get(position);
        holder.binding.imgItemPhoto.setImageResource(thing.getPhoto());
        holder.binding.tvItemName.setText(thing.getName());
        holder.binding.tvItemDescription.setText(thing.getDescription());

        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(listThing.get(holder.getAdapterPosition())));

    }

    @Override
    public int getItemCount() {
        return listThing.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ItemRowThingBinding binding;

        ListViewHolder(ItemRowThingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(ThingsList data);
    }
}
