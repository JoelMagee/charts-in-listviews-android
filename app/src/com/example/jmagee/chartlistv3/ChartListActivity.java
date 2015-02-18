package com.example.jmagee.chartlistv3;

import android.app.ListActivity;
import android.os.Bundle;

import com.shinobicontrols.charts.DataAdapter;
import com.shinobicontrols.charts.DataPoint;
import com.shinobicontrols.charts.NumberAxis;
import com.shinobicontrols.charts.NumberRange;
import com.shinobicontrols.charts.ShinobiChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel Magee on 06/11/2014.
 *
 * Copyright (C) 2015 Scott Logic
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
public class ChartListActivity extends ListActivity implements Runnable {

    private List<DataAdapter<Integer, Integer>> dataAdapters;

    // Initialise constants
    private static final int NUMBER_OF_CHARTS = 100;
    private static final int NUMBER_OF_DATAPOINTS = 200;
    private static final double Y_AXIS_RANGE = 10;
    private static final int DELAY = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);

        // Create an ArrayList to store the DataAdapters for each chart
        dataAdapters = new ArrayList<DataAdapter<Integer, Integer>>();

        for (int i = 0; i < NUMBER_OF_CHARTS; i++) {
            dataAdapters.add(createDataAdapter());
        }

        // Create a list adapter to populate the ListView and set it
        ChartArrayAdapter adapter = new ChartArrayAdapter(this, R.layout.list_item, dataAdapters, getListView());
        setListAdapter(adapter);
    }

    private DataAdapter<Integer, Integer> createDataAdapter() {
        DataAdapter<Integer, Integer> dataAdapter = new FireableDataAdapter<Integer, Integer>();

        // Create the DataPoints for your DataAdapter with an incrementing x value and a bounded, random y value
        for(int i = 0; i<NUMBER_OF_DATAPOINTS; i++) {
            dataAdapter.add(new DataPoint<Integer, Integer>(i, (int) (Math.floor(Math.random() * Y_AXIS_RANGE))));
        }

        return dataAdapter;
    }

    private static class FireableDataAdapter<X, Y> extends DataAdapter<X, Y> {
        public void fire() {
            fireUpdateHandler();
        }
    }

    @Override
    public void run() {
        // for each DataAdapter create a new DataPoint with an x value one greater than the previous and a bounded, random y value;
        // then remove the first DataPoint in the adapter and finally
        for (DataAdapter<Integer, Integer> dataAdapter : dataAdapters) {
            dataAdapter.add(new DataPoint<Integer, Integer>(dataAdapter.get(dataAdapter.size() - 1).getX() + 1, (int) (Math.floor(Math.random() * Y_AXIS_RANGE))));
            dataAdapter.remove(0);
            ((FireableDataAdapter<Integer, Integer>) dataAdapter).fire();
        }

        int start = getListView().getFirstVisiblePosition();
        int end = getListView().getLastVisiblePosition();
        ChartArrayAdapter.ViewHolder viewHolder;

        for (int i = start; i <= end; i++) {
            viewHolder = (ChartArrayAdapter.ViewHolder) getListView().getChildAt(i - start).getTag();
            ShinobiChart chart = viewHolder.chart.getShinobiChart();

            NumberAxis xAxis = (NumberAxis) chart.getXAxis();
            NumberRange xRange = (NumberRange) xAxis.getCurrentDisplayedRange();
            NumberRange xDataRange = (NumberRange) xAxis.getDataRange();
            final double delta = xDataRange.getMinimum() - xRange.getMinimum();
            xAxis.requestCurrentDisplayedRange(xRange.getMinimum() + delta, xRange.getMaximum() + delta, false, false);
        }

        // Call this Runnable again after a delay
        getListView().postDelayed(this, DELAY);
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

        // Prevent the Runnable from continuing to run, to avoid memory leaks
        getListView().removeCallbacks(this);
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

        // Start the Runnable
        run();
    }
}