package me.farishcv.android.sunshine.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootview.findViewById(R.id.listview_forecast);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getContext(), R.layout.list_item_forecast, R.id.list_item_forecast_textview,
                Arrays.asList("Today - Sunny - 88/63", "Tomorrow - Foggy - 70/40", "Weds - Cloudy - 72/63", "Thurs - Asteroids - 75/65", "Fri - Heavy Rain - 65/56", "Sat - HELP TRAPPED IN WEATHER STATION - 60/51", "Sun - Sunny - 80/68"));
        listView.setAdapter(ad);

        return rootview;
    }

    public class FethcWeatherTak extends AsyncTask<Void, Void, Void> {
        private final String LOG_TAG = FethcWeatherTak.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlconnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=560025&mode=json&units=metric&cnt=7&appid=44db6a862fba0b067b1930da0d769e98");

                urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestMethod("GET");
                urlconnection.connect();

                InputStream inputStream = urlconnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlconnection != null) {
                    urlconnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }

            }

            return null;
        }

    }

}