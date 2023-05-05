package com.example.blindareasecuritysystem.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.blindareasecuritysystem.Activity.MainActivity;
import com.example.blindareasecuritysystem.Activity.VedioActivity;
import com.example.blindareasecuritysystem.R;
import com.example.blindareasecuritysystem.Utils.Vedio;

import java.util.List;

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.ViewHolder>{

    private List<Vedio> mCarList;

    public VedioAdapter(List<Vedio> fruitList) {
        mCarList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vedio fruit = mCarList.get(position);
        holder.mTextView.setText(fruit.getName());
        holder.mim.setImageResource(fruit.getHead());
        holder.xTextView.setText(fruit.getScore());
    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private  int positon;
        private TextView mTextView;
        private TextView xTextView;
        private ImageView mim;

        public ViewHolder(View itemView) {
            super(itemView);
            positon = getPosition();
            mTextView = (TextView) itemView.findViewById(R.id.name);
            xTextView = (TextView) itemView.findViewById(R.id.score);
            mim = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"dianji" ,Toast.LENGTH_LONG).show();

                }
            });

            mim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"dianji"+positon,Toast.LENGTH_LONG).show();
                }
            });

        }
    }

}
