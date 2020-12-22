package com.example.lab4client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<PersonResult.Persons>{
    private  int resourceId;
    public PersonAdapter(Context context, int textViewResourceId, List<PersonResult.Persons> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        PersonResult.Persons person =getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView pname =(TextView)view.findViewById(R.id.pname);
        TextView page =(TextView)view.findViewById(R.id.page);
        TextView pteleno=(TextView)view.findViewById(R.id.pteleno);
        pname.setText("姓名："+person.getName());
        String age="未填写";
        if(person.getAge()!=null && person.getAge()!=0){
            age=person.getAge().toString();
        }
        page.setText("年龄："+age);
        pteleno.setText("电话："+person.getTeleno());
        return view;
    }
}
