package com.example.oniichan.lunchlist;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LunchList extends TabActivity {
  List<Restaurant> model=new ArrayList<Restaurant>();
  RestaurantAdapter adapter=null;
  EditText name=null;
  EditText address=null;
  RadioGroup types=null;

  List<Restaurant> modelSaleOff=new ArrayList<Restaurant>();
  RestaurantAdapter2 adapterSaleOff=null;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    name=(EditText)findViewById(R.id.name);
    address=(EditText)findViewById(R.id.addr);
    types=(RadioGroup)findViewById(R.id.types);
    
    Button save=(Button)findViewById(R.id.save);
    
    save.setOnClickListener(onSave);
    
    ListView list=(ListView)findViewById(R.id.restaurants);
    
    adapter=new RestaurantAdapter();
    list.setAdapter(adapter);
    
    TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
    
    spec.setContent(R.id.restaurants);
    spec.setIndicator("List", getResources()
                                .getDrawable(R.drawable.list));
    getTabHost().addTab(spec);
    
    spec=getTabHost().newTabSpec("tag2");
    spec.setContent(R.id.details);
    spec.setIndicator("Details", getResources()
                                  .getDrawable(R.drawable.restaurant));
    getTabHost().addTab(spec);

    spec=getTabHost().newTabSpec("tag3");
    spec.setContent(R.id.saleoff);
    spec.setIndicator("Type", getResources()
            .getDrawable(R.drawable.list));
    getTabHost().addTab(spec);

    getTabHost().setCurrentTab(0);
    
    list.setOnItemClickListener(onListClick);

    ListView listSaleOff=(ListView)findViewById(R.id.saleoff);

    adapterSaleOff=new RestaurantAdapter2();
    listSaleOff.setAdapter(adapterSaleOff);
  }
  
  private View.OnClickListener onSave=new View.OnClickListener() {
    public void onClick(View v) {
      Restaurant r=new Restaurant();
      EditText name=(EditText)findViewById(R.id.name);
      EditText address=(EditText)findViewById(R.id.addr);

      r.setName(name.getText().toString());
      r.setAddress(address.getText().toString());

      RadioGroup types=(RadioGroup)findViewById(R.id.types);
      RadioGroup discount=(RadioGroup)findViewById(R.id.discount);

      switch (types.getCheckedRadioButtonId()) {
        case R.id.sit_down:
          r.setType("sit_down");
          break;

        case R.id.take_out:
          r.setType("take_out");
          break;

        case R.id.delivery:
          r.setType("delivery");
          break;
      }

      switch (discount.getCheckedRadioButtonId()) {
        case R.id.dc0:
          r.setDiscount("dc0");
          break;

        case R.id.dc25:
          r.setDiscount("dc25");
          break;

        case R.id.dc50:
          r.setDiscount("dc50");
          break;

        case R.id.dc70:
          r.setDiscount("dc70");
          break;
      }
      adapter.add(r);
      adapterSaleOff.add(r);
//      if(r.getType().equals("take_out"))
//        adapterSaleOff.add(r);
      adapterSaleOff.sort(new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
          List<String> a = new ArrayList<String>();
          a.add("take_out");
          a.add("sit_down");
          a.add("delivery");
          return a.indexOf(o1.getType()) < a.indexOf(o2.getType()) ? 0 : 1;
        }
      });
    }
  };
  
  private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
    public void onItemClick(AdapterView<?> parent,
                             View view, int position,
                             long id) {
      Restaurant r=model.get(position);
      
      name.setText(r.getName());
      address.setText(r.getAddress());
      
      if (r.getType().equals("sit_down")) {
        types.check(R.id.sit_down);
      }
      else if (r.getType().equals("take_out")) {
        types.check(R.id.take_out);
      }
      else {
        types.check(R.id.delivery);
      }
      
      getTabHost().setCurrentTab(1);
    }
  };
  
  class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    RestaurantAdapter() {
      super(LunchList.this, R.layout.row, model);
    }
    
    public View getView(int position, View convertView,
                        ViewGroup parent) {
      View row=convertView;
      RestaurantHolder holder=null;
      
      if (row==null) {                          
        LayoutInflater inflater=getLayoutInflater();
        
        row=inflater.inflate(R.layout.row, parent, false);
        holder=new RestaurantHolder(row);
        row.setTag(holder);
      }
      else {
        holder=(RestaurantHolder)row.getTag();
      }
      
      holder.populateFrom(model.get(position));
      
      return(row);
    }
  }
  
  static class RestaurantHolder {
    private TextView name=null;
    private TextView address=null;
    private TextView dctext=null;
    private ImageView icon=null;
    private ImageView icondc=null;
    
    RestaurantHolder(View row) {
      name=(TextView)row.findViewById(R.id.title);
      address=(TextView)row.findViewById(R.id.address);
      dctext=(TextView)row.findViewById(R.id.dctext);
      icon=(ImageView)row.findViewById(R.id.icon);
      icondc=(ImageView)row.findViewById(R.id.icondc);
    }

    void populateFrom(Restaurant r) {
      name.setText(r.getName());
      address.setText(r.getAddress());
      dctext.setText(r.getDiscount().replace("dc", "") + "%");

      if (r.getType().equals("sit_down")) {
        icon.setImageResource(R.drawable.ball_red);
      }
      else if (r.getType().equals("take_out")) {
        icon.setImageResource(R.drawable.ball_yellow);
      }
      else {
        icon.setImageResource(R.drawable.ball_green);
      }

      if (r.getDiscount().equals("dc0")) {
        icondc.setImageResource(R.drawable.ball_red);

      }
      else if (r.getDiscount().equals("dc25")) {
        icondc.setImageResource(R.drawable.ball_red);
      }
      else if (r.getDiscount().equals("dc50")) {
        icondc.setImageResource(R.drawable.ball_yellow);
      }
      else {
        icondc.setImageResource(R.drawable.ball_green);
      }
    }
  }

  class RestaurantAdapter2 extends ArrayAdapter<Restaurant> {
    RestaurantAdapter2() {
      super(LunchList.this, R.layout.row, modelSaleOff);
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
      View row=convertView;
      RestaurantHolder holder=null;

      if (row==null) {
        LayoutInflater inflater=getLayoutInflater();

        row=inflater.inflate(R.layout.row, parent, false);
        holder=new RestaurantHolder(row);
        row.setTag(holder);
      }
      else {
        holder=(RestaurantHolder)row.getTag();
      }

      holder.populateFrom(modelSaleOff.get(position));

      return(row);
    }
  }
}