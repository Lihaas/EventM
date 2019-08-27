package com.skywalker.android.apps.eventm.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skywalker.android.apps.eventm.Fragments.Search;
import com.skywalker.android.apps.eventm.Model.SearchPojo;
import com.skywalker.android.apps.eventm.Model.TechPojo;
import com.skywalker.android.apps.eventm.OpenEvent;
import com.skywalker.android.apps.eventm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.searchViewHolder> {

    List<SearchPojo> data;
    Context context;

    public SearchAdapter(List<SearchPojo> data ,Context context){
        this.context = context;
        this.data =data;

    }

    @NonNull
    @Override
    public searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.demo1,parent,false);
        return new searchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchViewHolder holder, int position) {
        final SearchPojo pojo = data.get(position);
        Picasso.get().load(pojo.getPic()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class searchViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public searchViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchPojo datas = data.get(getAdapterPosition());

                    Bundle bundle = new Bundle();
                    bundle.putString("img",datas.getPic());
                    bundle.putInt("prize",datas.getPrize());
                    bundle.putString("No_of_parti",datas.getNoOfParti());
                    bundle.putString("detail",datas.getDetail());
                    bundle.putString("EventName",datas.getEventName());
                    bundle.putInt("EntryFee",datas.getEntryFee());
                    bundle.putString("Location",datas.getLocation());
                  /*  OpenEvent openEvent = new OpenEvent();
                    openEvent.setArguments(bundle);
*/
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new OpenEvent();
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.slide_up,
                                    R.animator.slide_down,
                                    R.animator.slide_up,
                                    R.animator.slide_down)
                            .replace(R.id.fragments, myFragment)
                            .addToBackStack(null).commit();


                    // context.startActivity(i);
                }
            });

        }
    }
}
