package com.example.hodhod.hotels.adapterds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hodhod.hotels.R;
import com.example.hodhod.hotels.models.HotelItem;

import java.util.List;

public class HotelRecyclerAdapter extends RecyclerView.Adapter<HotelRecyclerAdapter.ViewHolder> {

    private List<HotelItem> mHotelList;
    private Context mContext;
    private OnHotelListener mOnHotelListener;

    public HotelRecyclerAdapter(Context context, OnHotelListener onHotelListener, List<HotelItem> hotelList) {
        mContext = context;
        this.mOnHotelListener = onHotelListener;
        mHotelList = hotelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotels_list_item, parent, false);
        return new ViewHolder(view, mOnHotelListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.hotelName.setText(mHotelList.get(position).getSummary().getHotelName());

        Glide.with(mContext)
                .load(mHotelList.get(position).getImage().get(0).getUrl())
                .placeholder(R.drawable.failed)
                .error(R.drawable.failed)
                .centerCrop()
                .into(holder.hotelImage);

    }

    @Override
    public int getItemCount() {
        return mHotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // ui
        ImageView hotelImage;
        TextView hotelName;
        // vars
        private OnHotelListener mOnHotelListener;

        public ViewHolder(@NonNull View itemView, OnHotelListener onHotelListener) {
            super(itemView);
            this.mOnHotelListener = onHotelListener;
            hotelImage = itemView.findViewById(R.id.hotel_image);
            hotelName = itemView.findViewById(R.id.hotel_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnHotelListener.onHotelClick(getAdapterPosition());
        }
    }
}