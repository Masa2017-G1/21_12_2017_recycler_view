package com.sheygam.masa_2017_21_12;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gregorysheygam on 21/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "ADAPTER";
    private ArrayList<Person> persons;
    private AdapterClickListener listener;

    public MyAdapter(AdapterClickListener listener) {
        this.listener = listener;
        persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            persons.add(new Person("Person " + i,"person" + i +"@mail.com"));
        }
    }

    public void addItem(Person person){
        persons.add(2,person);
        notifyItemInserted(2);
    }
    public void remove(int position){
        persons.remove(position);
        notifyItemRemoved(position);
    }

    public void updateItem(int position){
        notifyItemChanged(position);
    }

    public void moveItem(int from, int to){
        Person tmp = persons.remove(from);
        persons.add(to,tmp);
        notifyItemMoved(from,to);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Person person = persons.get(position);
        holder.nameTxt.setText(person.getName());
        holder.emailTxt.setText(person.getEmail());
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTxt, emailTxt;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            emailTxt = itemView.findViewById(R.id.email_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v,getAdapterPosition(),persons.get(getAdapterPosition()));
        }
    }

    public interface AdapterClickListener{
        void onItemClick(View view, int position, Person data);
    }
}
