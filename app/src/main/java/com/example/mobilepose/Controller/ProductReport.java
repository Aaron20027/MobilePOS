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
import com.example.mobilepose.Model.API.Entities.ProductReportResponse;
import com.example.mobilepose.Model.API.Entities.ProductReportResponse1;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.Entities.SalesResponse;
import com.example.mobilepose.Model.API.Entities.SalesResponse1;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class ProductReport extends Fragment {

    TextView TopProduct,TopCategory;
    PieChart piechart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_report, container, false);

        piechart=view.findViewById(R.id.barChart);

        TopProduct=view.findViewById(R.id.tp);
        TopCategory=view.findViewById(R.id.tf);

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<ProductReportResponse[]>> rep = api.getSalesProduct(1);
        rep.enqueue(new APICallback<>(
                response ->
                {
                    ProductReportResponse[] salesResponses = response;
                    for (ProductReportResponse salesResponse : salesResponses) {
                        TopProduct.setText(salesResponse.ProductName);
                        TopCategory.setText(salesResponse.categoryName);

                    }

                },
                error ->
                {


                }
        ));

        Call<ResponseBase<ProductReportResponse1[]>> rep1 = api.getSalesProduct1(1);
        rep1.enqueue(new APICallback<>(
                response ->
                {

                    List<String> categories=new ArrayList<>();
                    List<Integer> ammount=new ArrayList<>();
                    ProductReportResponse1[] productReportResponse1s = response;
                    for (ProductReportResponse1 productReportResponse: productReportResponse1s) {
                        categories.add(productReportResponse.categoryName);
                        ammount.add(productReportResponse.total_amount_sold);

                    }

                    List<PieEntry> entries = new ArrayList<>();
                    for (int i = 0; i < categories.size(); i++) {
                        entries.add(new PieEntry(ammount.get(i), categories.get(i)));
                    }

                    PieDataSet dataSet = new PieDataSet(entries, "Sales Distribution");
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    PieData pieData = new PieData(dataSet);

                    piechart.setData(pieData);

                    Description desc = new Description();
                    desc.setText("Sales Distribution by Category");
                    piechart.setDescription(desc);

                    piechart.invalidate();

                },
                error ->
                {


                }
        ));


        return view;
    }
}