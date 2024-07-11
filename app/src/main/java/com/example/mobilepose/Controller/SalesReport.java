package com.example.mobilepose.Controller;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.Entities.SalesResponse;
import com.example.mobilepose.Model.API.Entities.SalesResponse1;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;


public class SalesReport extends Fragment {

    TextView tranCountTxt,avgTxt,tranSaleTxt;
    LineChart linechart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_sales_report, container, false);

        tranCountTxt=view.findViewById(R.id.totalTran);
        tranSaleTxt=view.findViewById(R.id.total);
        avgTxt=view.findViewById(R.id.avgTran);

        linechart=view.findViewById(R.id.lineChart);

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<SalesResponse[]>> rep = api.getSalesReport(1);
        rep.enqueue(new APICallback<>(
                response ->
                {
                    SalesResponse[] salesResponses = response;
                    for (SalesResponse salesResponse : salesResponses) {
                        tranCountTxt.setText(String.valueOf(salesResponse.transactionCount));
                        tranSaleTxt.setText(String.valueOf(salesResponse.avgSales));
                        avgTxt.setText(String.valueOf(salesResponse.avgSales));
                    }

                },
                error ->
                {


                }
        ));



        Call<ResponseBase<SalesResponse1[]>> rep1 = api.getSalesReport1(1);
        rep1.enqueue(new APICallback<>(
                response ->
                {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    List<Date> dates = new ArrayList<>();
                    List<Float> sale = new ArrayList<>();
                    SalesResponse1[] salesResponses = response;
                    for (SalesResponse1 salesResponse : salesResponses) {
                        Date date = null;
                        try {
                            date = dateFormat.parse(salesResponse.date);
                            dates.add(date);
                            sale.add(salesResponse.order_total);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                    }



                    List<String> dateLabels = new ArrayList<>();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    for (Date date : dates) {
                        dateLabels.add(sdf.format(date));
                    }

                    List<Entry> entries = new ArrayList<>();
                    for (int i = 0; i < sale.size(); i++) {
                        entries.add(new Entry(i, sale.get(i)));
                    }

                    LineDataSet dataSet = new LineDataSet(entries, "Sales"); // Label for dataset
                    dataSet.setColor(Color.BLUE); // Set line color
                    dataSet.setCircleColor(Color.BLUE); // Set circle color
                    dataSet.setLineWidth(2f); // Set line width

                    LineData lineData = new LineData(dataSet);

                    // Customize x-axis labels
                    XAxis xAxis = linechart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(dateLabels)); // Set date labels
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position x-axis labels at bottom

                    linechart.setData(lineData);

                    Description desc = new Description();
                    desc.setText("Sales over Time");
                    linechart.setDescription(desc);

                    linechart.invalidate();

                },
                error ->
                {


                }
        ));



        return view;
    }


}