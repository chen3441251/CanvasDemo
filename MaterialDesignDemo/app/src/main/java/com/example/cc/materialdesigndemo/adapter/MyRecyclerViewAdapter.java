package com.example.cc.materialdesigndemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cc.materialdesigndemo.FruitDetailsActivity;
import com.example.cc.materialdesigndemo.R;
import com.example.cc.materialdesigndemo.bean.Fruit;

import java.util.ArrayList;

/**
 * @创建者 :           chaochen
 * @创建时间 :         2017/10/23 20:54
 * @描述 :           ${TODO}
 */


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Fruit> list;
    private Context          mMContext;

    public MyRecyclerViewAdapter(ArrayList<Fruit> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, parent, false);
        mMContext = parent.getContext();
        final ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Fruit fruit = list.get(adapterPosition);
                //                Toast.makeText(mMContext,"被点击的条目:"+adapterPosition+",,被点击的名称:"+fruit.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(mMContext, FruitDetailsActivity.class);
                intent.putExtra("fruitName", fruit.getName());
                intent.putExtra("fruitResId", fruit.getResId());
//                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
//                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getResId());
                mMContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.mTv_name.setText(fruit.getName());
        //利用glide加载图片可以有效控制oom
        Glide.with(mMContext).load(fruit.getResId()).into(holder.mIv_pohto);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private       ImageView mIv_pohto;
        private       TextView  mTv_name;
        private final CardView  mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mIv_pohto = (ImageView) mCardView.findViewById(R.id.iv_pohto);
            mTv_name = (TextView) mCardView.findViewById(R.id.tv_name);
        }
    }
}
