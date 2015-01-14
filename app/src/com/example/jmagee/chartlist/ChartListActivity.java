package com.example.jmagee.chartlist;

import android.app.ListActivity;
import android.os.Bundle;

import com.shinobicontrols.charts.DataAdapter;
import com.shinobicontrols.charts.DataPoint;
import com.shinobicontrols.charts.SimpleDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel Magee on 06/11/2014.
 *
 * Copyright (C) 2014 Scott Logic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
public class ChartListActivity extends ListActivity {

    // Initialise constants
    private static final int NUMBER_OF_CHARTS = 100;
    private static final int NUMBER_OF_DATAPOINTS = 200;
    private static final double Y_AXIS_RANGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);

        // Create an ArrayList to store the DataAdapters for each chart
        List<DataAdapter> dataAdapters = new ArrayList<DataAdapter>();

        for (int i = 0; i < NUMBER_OF_CHARTS; i++) {
            dataAdapters.add(createDataAdapter());
        }

        // Create a list adapter to populate the ListView and set it
        ChartArrayAdapter adapter = new ChartArrayAdapter(this, R.layout.list_item, dataAdapters);
        setListAdapter(adapter);
    }

    private DataAdapter<Integer, Integer> createDataAdapter() {
        DataAdapter<Integer, Integer> dataAdapter = new SimpleDataAdapter<Integer, Integer>();

        // Create the DataPoints for your DataAdapter with an incrementing x value and a bounded, random y value
        for(int i = 0; i < NUMBER_OF_DATAPOINTS; i++) {
            dataAdapter.add(new DataPoint<Integer, Integer>(i, (int) (Math.floor(Math.random() * Y_AXIS_RANGE))));
        }

        return dataAdapter;
    }

    @Override
    protected void onPause() {
        super.onPause();

        int start = getListView().getFirstVisiblePosition();
        int end = getListView().getLastVisiblePosition();
        ChartArrayAdapter.ViewHolder viewHolder;

        for (int i = start; i <= end; i++) {
            viewHolder = (ChartArrayAdapter.ViewHolder) getListView().getChildAt(i - start).getTag();
            viewHolder.chart.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int start = getListView().getFirstVisiblePosition();
        int end = getListView().getLastVisiblePosition();
        ChartArrayAdapter.ViewHolder viewHolder;

        for (int i = start; i <= end; i++) {
            viewHolder = (ChartArrayAdapter.ViewHolder) getListView().getChildAt(i - start).getTag();
            viewHolder.chart.onResume();
        }
    }
}