package com.example.mykominfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.PegawaiViewHolder> {

    private Context context;
    private ArrayList<Pegawai> pegawaiList;
    private OnItemClickListener mListener;

    public PegawaiAdapter(Context context, ArrayList<Pegawai> pegawaiList) {
        this.context = context;
        this.pegawaiList = pegawaiList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemClick(Pegawai pegawai);
    }

    @NonNull
    @Override
    public PegawaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pegawai, parent, false);
        return new PegawaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PegawaiViewHolder holder, int position) {
        Pegawai pegawai = pegawaiList.get(position);
        holder.namaTextView.setText(pegawai.getNama());
        holder.jabatanTextView.setText(pegawai.getJabatan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(pegawai); // Panggil metode onItemClick yang sesuai
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return pegawaiList.size();
    }

    // Method to set new data to the adapter
    public void setData(ArrayList<Pegawai> newData) {
        this.pegawaiList = newData;
        notifyDataSetChanged();
    }
    public static class PegawaiViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView, jabatanTextView;

        public PegawaiViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.namaTextView);
            jabatanTextView = itemView.findViewById(R.id.jabatanTextView);
        }
    }
}