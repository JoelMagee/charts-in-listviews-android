package com.example.jmagee.chartlist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.shinobicontrols.charts.Axis;
import com.shinobicontrols.charts.ChartView;
import com.shinobicontrols.charts.DataAdapter;
import com.shinobicontrols.charts.LineSeries;
import com.shinobicontrols.charts.NumberAxis;
import com.shinobicontrols.charts.NumberRange;
import com.shinobicontrols.charts.ShinobiChart;

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
public class ChartArrayAdapter extends ArrayAdapter<DataAdapter> {

    private int rowLayoutId;
    private List<DataAdapter> values;
    private LayoutInflater inflater;
    private NumberRange yAxisRange;
    private NumberRange currentXRange;

    // Initialise constants
    private static final double Y_AXIS_RANGE = 10;
    private static final double X_AXIS_RANGE = 100;
    private static final double PADDING = 2;

    public ChartArrayAdapter(Context context, int rowLayoutId, List<DataAdapter> values) {
        super(context, rowLayoutId, values);
        this.rowLayoutId = rowLayoutId;
        this.values = values;
        this.yAxisRange = new NumberRange(0.0, Y_AXIS_RANGE + PADDING);
        this.currentXRange = new NumberRange(0.0, X_AXIS_RANGE);

        // get the LayoutInflater in the Constructor because we only need to get it once
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Check whether we have been passed a view which we can reuse
        if (convertView == null) {
            // If not then inflate one from the row xml
            convertView = inflater.inflate(rowLayoutId, parent, false);

            // Create a ViewHolder to store the reference to the ChartView
            holder = new ViewHolder();
            holder.chart = (ChartView) convertView.findViewById(R.id.chart);

            // Set the tag of this view to the ViewHolder so that we can get it when reusing this view
            convertView.setTag(holder);
        } else {
            // If we have been passed a view then we can get the ViewHolder from the tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the Shinobi Chart from the ChartView
        ShinobiChart shinobiChart = holder.chart.getShinobiChart();
        // uncomment this if you are using the trial version - shinobiChart.setLicenseKey("<license_key_here>");

        shinobiChart.setTitle("Chart #" + (position + 1));
        // If this chart hasn't been created with axes then create them
        if(shinobiChart.getXAxis() == null) {
            NumberAxis xAxis = createXAxis();
            shinobiChart.setXAxis(xAxis);
            NumberAxis yAxis = new NumberAxis(yAxisRange);
            shinobiChart.setYAxis(yAxis);
        }

        // If the chart doesn't have a line series then add one
        if (shinobiChart.getSeries().size() == 0) {
            LineSeries lineSeries = new LineSeries();
            shinobiChart.addSeries(lineSeries);
        }

        // Set the data adapter for each chart based on the position we are at in the list
        // This isn't guarded by an if statement because we want each chart to have it's own data
        // whereas each chart can have the same axes or series
        shinobiChart.getSeries().get(0).setDataAdapter(values.get(position));

        // Set the X axis displayed range to the current range so that we can't see the full data range
        ((NumberAxis) shinobiChart.getXAxis()).requestCurrentDisplayedRange(currentXRange.getMinimum(), currentXRange.getMaximum(), false, false);

        return convertView;
    }

    // Create a pannable X axis
    private NumberAxis createXAxis() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.enableGesturePanning(true);
        return xAxis;
    }

    // ViewHolder class that stores the ChartView
    public static class ViewHolder {
        ChartView chart;
    }
}
