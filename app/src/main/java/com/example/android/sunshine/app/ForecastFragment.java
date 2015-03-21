package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rbhavsar on 2/13/2015.
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    private static final String TAG_POSITION = "position";

    private ArrayAdapter<String >  mForecastAdapter;


    //ArrayAdapter mForecastAdapter;

    public ForecastFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       //Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {


            updateWeather();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //setHasOptionsMenu(true);
        // Create some dummy date for the listview.

        /*
        String[] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/40",
                "Weds - Cloudy - 72/63",
                "Thurs - Asteroids - 75/65",
                "Fri - Heavy Rain - 65/56",
                "Sat - HELP TRAPPED IN WEATHERSTATION - 60/51",
                "SUN - Sunny - 80/68"


        };

        final List<String> weekForecast = new ArrayList<String>(
                Arrays.asList(forecastArray));
        */

        // Create Adapter.

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                new ArrayList<String>());


        //  android:id="@+id/listview_forecast"
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

        listView.setAdapter(mForecastAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String forecast = mForecastAdapter.getItem(position);

                //Toast.makeText(getActivity(), forecast , Toast.LENGTH_SHORT).show();

                // Starting single contact activity
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);


            }
        });

            return rootView;
        } // oncreateview

       private void updateWeather(){
           //FetchWeatherTask weatherTask = new FetchWeatherTask();
           FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);

           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
           //String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
           String location = prefs.getString(getString(R.string.pref_location_key),
                   getString(R.string.pref_location_default));
           weatherTask.execute(location);
       
       };

        @Override
        public void onStart(){
            super.onStart();

            updateWeather();

        }





}
