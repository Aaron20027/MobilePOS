package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.EmployeeReportResponse;
import com.example.mobilepose.Model.API.Entities.EmployeeReportResponse1;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import retrofit2.Call;

public class EmployeeReport extends Fragment {

    TextView a1,a2,a3,a4;
    BarChart barChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employee_report, container, false);

        a1=view.findViewById(R.id.a1);
        a2=view.findViewById(R.id.a2);
        a3=view.findViewById(R.id.a3);
        a4=view.findViewById(R.id.a4);

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<EmployeeReportResponse[]>> rep1 = api.getEmployee(1);
        rep1.enqueue(new APICallback<>(
                response ->
                {
                    EmployeeReportResponse[] emp=response;
                    for (EmployeeReportResponse empResponse : emp) {

                        a1.setText(empResponse.acc);
                        a2.setText(String.valueOf(empResponse.disCount));
                        a3.setText(empResponse.acc1);
                        a4.setText(String.valueOf(empResponse.avgOrdersPerEmployee));

                    }


                },
                error ->
                {


                }
        ));


        barChart=view.findViewById(R.id.barchart);

        Call<ResponseBase<EmployeeReportResponse1[]>> rep2 = api.getEmployee1(1);
        rep2.enqueue(new APICallback<>(
                response ->
                {

                    ArrayList<BarEntry> entries = new ArrayList<>();
                    ArrayList<String> names = new ArrayList<>();
                    EmployeeReportResponse1[] employeeReportResponse1 = response;
                    int index = 0;
                    for (EmployeeReportResponse1 empReportResponse : employeeReportResponse1) {
                        entries.add(new BarEntry(index, empReportResponse.disCount));
                        names.add(empReportResponse.acc);
                        index++;
                    }

                    barChart.getDescription().setEnabled(false);
                    barChart.setDrawGridBackground(false);
                    barChart.setDrawBarShadow(false);
                    barChart.setHighlightFullBarEnabled(false);

                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(names)); // Set the names as labels

                    YAxis leftAxis = barChart.getAxisLeft();
                    leftAxis.setDrawGridLines(false);

                    barChart.getAxisRight().setEnabled(false);

                    BarDataSet dataSet = new BarDataSet(entries, "Label");
                    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    BarData data = new BarData(dataSet);
                    barChart.setData(data);
                    barChart.invalidate();


                },
                error ->
                {


                }
        ));


        return view;
    }
}