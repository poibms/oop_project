package com.example.project.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.Database.DBHelper;
import com.example.project.Database.TripDB;
import com.example.project.Helper.DateTimeHelper;
import com.example.project.Helper.SearchHelper;
import com.example.project.Helper.SpinnerSetterResult;
import com.example.project.Model.Trip;
import com.example.project.R;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchFragment extends Fragment {

    public EditText startDateEdit, fromEdit, toEdit, capacityEdit;
    private Calendar date;
    private Button searchBtn;
    private SQLiteDatabase db;
    private String from, to, startDate;
    private int capacity;
    private ListView tripListView;
    private CustomListAdapter customListAdapter;


    ArrayList<Trip> listTrip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startDateEdit = (EditText) view.findViewById(R.id.startDateEdit);
        fromEdit = (EditText) view.findViewById(R.id.fromEdit);
        toEdit = (EditText) view.findViewById(R.id.toEdit);
        capacityEdit = (EditText) view.findViewById(R.id.capacityEdit);
        tripListView = (ListView) view.findViewById(R.id.tripListView);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);


        db = new DBHelper(getContext()).getReadableDatabase();

        listTrip = new ArrayList<>();

        date = Calendar.getInstance();

        setListeners();
        setDateListeners();

        customListAdapter = new CustomListAdapter(getContext(), listTrip);
        tripListView.setAdapter(customListAdapter);
    }

    private void setDateListeners() {
        startDateEdit.setInputType(InputType.TYPE_NULL);

        DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) ->
                startDateEdit.setText(DateTimeHelper.getGeneralDateFormat(year, monthOfYear + 1, dayOfMonth));

        DatePickerDialog birthdayDialog = new DatePickerDialog(getContext(), d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH));

        startDateEdit.setOnClickListener(view -> {
            birthdayDialog.show();
        });
        startDateEdit.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                birthdayDialog.show();
            }
        });
    }

    private void bindingValues() {
        from = fromEdit.getText().toString().trim();
        to = toEdit.getText().toString().trim();
        startDate = startDateEdit.getText().toString();
        capacity = Integer.parseInt(capacityEdit.getText().toString().trim());
    }

    private void setListeners() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindingValues();

                SpinnerSetterResult<Trip> tripsResult =
                        SearchHelper.getTrips(getContext(), from, to, startDate, capacity);
                customListAdapter.updateStudentsList(tripsResult.getList());

            }


        });
    }

    public class CustomListAdapter extends BaseAdapter {

        private ArrayList<Trip> trips;
        private Context context;



        public CustomListAdapter(Context context, ArrayList<Trip> trips) {
            this.context = context;
            this.trips = trips;

        }

        @Override
        public int getCount() {return trips.size();}

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        public void updateStudentsList(ArrayList<Trip> trips) {
            this.trips.clear();
            this.trips.addAll(trips);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int pos, View vw, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.list_item, null);

            TextView from = (TextView) view.findViewById(R.id.from);
            TextView to = (TextView) view.findViewById(R.id.to);
            TextView time = (TextView) view.findViewById(R.id.startTime);
            TextView transportType = (TextView) view.findViewById(R.id.transportType);
            TextView capacity = (TextView) view.findViewById(R.id.capacity);
            TextView price = (TextView) view.findViewById(R.id.price);

            from.setText(trips.get(pos).getStart());
            to.setText(trips.get(pos).getFinish());
            time.setText(trips.get(pos).getStartTime());
            transportType.setText(trips.get(pos).getTransportType());
            capacity.setText(trips.get(pos).getCapacity() + "");
            price.setText(trips.get(pos).getPrice() + "");

//            view.setOnClickListener(v -> {
//                Intent intent = new Intent(getContext(), SelectedStudentActivity.class);
////                intent.putExtra("Trip", trips.get(pos));
//                startActivity(intent);
//            });

            return view;
        }
    }
}
