package com.example.myapplication;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

public class LunchList extends TabActivity {
    private Restaurant r = new Restaurant();
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private RestaurantAdapter restaurantArrayAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(onSave);

        ListView list = (ListView)findViewById(R.id.restaurants);
        list.setOnItemClickListener(onListClick);

        restaurantArrayAdapter = new RestaurantAdapter();
        list.setAdapter(restaurantArrayAdapter);

        TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.restaurants);
        spec.setIndicator("List",getResources().getDrawable(R.drawable.checklist));
        getTabHost().addTab(spec);
        spec = getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details",
                getResources().getDrawable(R.drawable.restaurant));
        getTabHost().addTab(spec);
        getTabHost().setCurrentTab(0);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Restaurant r = new Restaurant();

            EditText name = (EditText)findViewById(R.id.name);
            EditText address = (EditText)findViewById(R.id.address);

            r.setName(name.getText().toString());
            r.setAddress(address.getText().toString());

            RadioGroup type = (RadioGroup)findViewById(R.id.type);
            switch (type.getCheckedRadioButtonId()) {
                case R.id.radio_btn_take_out:
                    r.setType("Take out");
                    break;
                case R.id.radio_btn_eat_in:
                    r.setType("Eat in");
                    break;
                case R.id.radio_btn_delivery:
                    r.setType("Delivery");
                    break;
            }
            restaurantList.add(r);
        }
    };

    public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        public RestaurantAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);

        }

        public RestaurantAdapter() {
            super(  LunchList.this,
                    android.R.layout.simple_list_item_1,
                    restaurantList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if(row==null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, null);
            }

            Restaurant r = restaurantList.get(position);

            ((TextView)row.findViewById(R.id.title)).setText(r.getName());
            ((TextView)row.findViewById(R.id.subTitle)).setText(r.getAddress());
            ImageView icon = (ImageView)row.findViewById(R.id.icon);

            String type = r.getType();
            if(type.equals("Take out")) {
                icon.setImageResource(R.drawable.take_out);

            } else if (type.equals("Eat in")) {
                icon.setImageResource(R.drawable.eat_in);
            } else {
                icon.setImageResource(R.drawable.delivery);
            }
            return row;
        }
    }
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener()
    {
        public void onListClick(AdapterView<?>parent, View view,int position, long id)
        {
            Restaurant r = listRestaurant.get(position);  // lấy item được chọn
            EditText name;
            EditText address;
            RadioGroup types;

            name = (EditText)findViewById(R.id.name);
            address = (EditText)findViewById(R.id.address);
            types = (RadioGroup)findViewById(R.id.types);

            name.setText(r.getName());
            address.setText(r.getAddress());
            if (r.getType().equals("Sit down"))
                types.check(R.id.sit_down);
            else if (r.getType().equals("Take out"))
                types.check(R.id.take_out);
            else
                types.check(R.id.delivery);

            getTabHost().setCurrentTab(1);
        }
    }
}


