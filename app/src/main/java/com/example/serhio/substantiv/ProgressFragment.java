package com.example.serhio.substantiv;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;


public class ProgressFragment extends Fragment {
    private final static String ZERO_STARS = "zeroStars";
    private final static String ONE_STAR = "oneStar";
    private final static String TWO_STARS = "twoStars";
    private final static String THREE_STARS = "threeStars";
    private final static String FOUR_STARS = "fourStars";


    public ProgressFragment() {
        // Required empty public constructor
    }

    public static ProgressFragment newInstance(int[] data) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putInt(FOUR_STARS, data[0]);
        args.putInt(THREE_STARS, data[1]);
        args.putInt(TWO_STARS, data[2]);
        args.putInt(ONE_STAR, data[3]);
        args.putInt(ZERO_STARS, data[4]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        PieChart pieChart = view.findViewById(R.id.pieChart);
        populateChart(pieChart);

        return view;
    }


    private void populateChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(getArguments().getInt(FOUR_STARS), ""));
        entries.add(new PieEntry(getArguments().getInt(THREE_STARS), ""));
        entries.add(new PieEntry(getArguments().getInt(TWO_STARS), ""));
        entries.add(new PieEntry(getArguments().getInt(ONE_STAR), ""));
        entries.add(new PieEntry(getArguments().getInt(ZERO_STARS), ""));

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.pink), getResources().getColor(R.color.green), getResources().getColor(R.color.yellow), getResources().getColor(R.color.grey));
        dataSet.setSliceSpace(0);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        data.setValueFormatter(new PercentFormatter());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(70f);
        pieChart.setHoleRadius(65f);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        pieChart.animateXY(1400, 1400);

        prepareLegend(pieChart);


        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);

        prepareCenterHole(pieChart);
    }

    private void prepareCenterHole(PieChart pieChart) {
        int zeroStars = getArguments().getInt(ZERO_STARS);
        int oneStar = getArguments().getInt(ONE_STAR);
        int twoStars = getArguments().getInt(TWO_STARS);
        int threeStars = getArguments().getInt(THREE_STARS);
        int fourStars = getArguments().getInt(FOUR_STARS);
        int summ = zeroStars + oneStar + twoStars + threeStars + fourStars;
        pieChart.setCenterText("Learned: " + fourStars + "/" + summ);
    }

    private void prepareLegend(PieChart pieChart) {
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(true);

        ArrayList<LegendEntry> legendEntryes = new ArrayList<>();

        int[] colors = pieChart.getData().getColors();

        String[] labels = {"Learned", "Three stars", "Two stars", "One star", "To learn"};
        for (int i = 0; i < 5; i++) {
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = labels[i];
            legendEntry.formColor = colors[i];
            legendEntryes.add(legendEntry);

        }
        legend.setCustom(legendEntryes);
    }

}
