package com.example.testcft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Valute> mData;

    public RecyclerViewAdapter(Context mContext, List<Valute> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void clear() {
        int size = mData.size();
        mData.clear();
        this.notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final BlankFragment blankFragment = BlankFragment.newInstance(R.layout.currency_conversion_dialog);

        holder.idView.setText(mData.get(position).getId());
        holder.numCodeView.setText(mData.get(position).getNumCode());
        holder.charCodeView.setText(mData.get(position).getCharCode());
        holder.nominalView.setText(mData.get(position).getNominal().toString());
        holder.nameView.setText(mData.get(position).getName());
        holder.valueView.setText(mData.get(position).getValue().toString());
        holder.previousView.setText(mData.get(position).getPrevious().toString());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BlankFragment.count ==0){
                    BlankFragment.count++;
                    blankFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(),"tag");
                    blankFragment.setNameCurrency(mData.get(position).getName());
                    blankFragment.setNominalInt(mData.get(position).getNominal());
                    blankFragment.setCurrencyDouble(mData.get(position).getValue());
                    blankFragment.setContext(mContext);
                    blankFragment.setCharCode(mData.get(position).getCharCode());
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView idView;
        private TextView numCodeView;
        private TextView charCodeView;
        private TextView nominalView;
        private TextView nameView;
        private TextView valueView;
        private TextView previousView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view_id);
            idView = itemView.findViewById(R.id.idView);
            numCodeView = itemView.findViewById(R.id.numCodeView);
            charCodeView = itemView.findViewById(R.id.charCodeView);
            nominalView = itemView.findViewById(R.id.nominalView);
            nameView = itemView.findViewById(R.id.nameView);
            valueView = itemView.findViewById(R.id.valueView);
            previousView = itemView.findViewById(R.id.previousView);

        }
    }
}
