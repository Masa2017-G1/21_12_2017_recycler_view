package com.sheygam.masa_2017_21_12;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gregorysheygam on 21/12/2017.
 */

public class MySecondAdapter extends RecyclerView.Adapter<MySecondAdapter.MySecondViewHolder>{
    private ArrayList<Person> persons;

    public MySecondAdapter() {
        persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            persons.add(new Person("Person " + i,"person"+i+"@mail.com"));
        }
    }

    public Person getItem(int position){
        return persons.get(position);
    }

    @Override
    public MySecondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row,parent,false);
        return new MySecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MySecondViewHolder holder, int position) {
        Person p = persons.get(position);
        holder.nameTxt.setText(p.getName());
        holder.emailTxt.setText(p.getEmail());
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class MySecondViewHolder extends RecyclerView.ViewHolder{
        TextView nameTxt, emailTxt;
        public MySecondViewHolder(View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            emailTxt = itemView.findViewById(R.id.email_txt);
        }
    }
}
