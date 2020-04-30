package com.matloob.myresponsiveapp.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matloob.myresponsiveapp.R;
import com.matloob.myresponsiveapp.models.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serar Matloob on 4/30/2020.
 */
public class TracksRecyclerAdapter extends RecyclerView.Adapter<TracksRecyclerAdapter.ItemViewHolder>{
    // Data list of tracks
    private List<Track> tracks = new ArrayList<>();

    private int selectedPosition = -1;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.track_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.bind(tracks.get(position));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public void setTracks(List<Track> tracks, boolean shouldReload, int pos) {
        this.tracks = tracks;
        this.selectedPosition = pos;
        if(shouldReload){
            selectedPosition = -1;
        }
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout linearLayout;
        ItemViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_item);
            linearLayout = view.findViewById(R.id.item_linear_layout);
        }

        void bind(Track track) {
            textView.setText(track.getName());
            if (selectedPosition == getAdapterPosition()){
                linearLayout.setBackgroundColor(Color.argb(40, 100, 200, 100));
            }
            else {
                linearLayout.setBackgroundColor(Color.WHITE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TAG", "onClick: " + getAdapterPosition());
                    Toast.makeText(view.getContext(), "Playing " + getItem(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                    if (selectedPosition == getAdapterPosition()) {
                        selectedPosition = -1;
                    } else {
                        selectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public Track getItem(int position) {
        return tracks.get(position);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}