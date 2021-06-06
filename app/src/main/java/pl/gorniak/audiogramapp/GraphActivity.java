package pl.gorniak.audiogramapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";
    private LineChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mChart = (LineChart) findViewById(R.id.linechart);
//        mChart.setOnChartGestureListener(GraphActivity.this);
//        mChart.setOnChartValueSelectedListener(GraphActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();
        yValues1.add(new Entry(125, 60f));
        yValues1.add(new Entry(300, 44f));
        yValues1.add(new Entry(1000, 64f));
        yValues1.add(new Entry(1500, 74f));
        yValues1.add(new Entry(2000, 45f));
        yValues1.add(new Entry(2500, 63f));
        yValues1.add(new Entry(3000, 82f));

        yValues2.add(new Entry(125, 60f));
        yValues2.add(new Entry(300, 94f));
        yValues2.add(new Entry(1000, 54f));
        yValues2.add(new Entry(1500, 64f));
        yValues2.add(new Entry(2000, 45f));
        yValues2.add(new Entry(2500, 43f));
        yValues2.add(new Entry(3000, 72f));


        LineDataSet set1 = new LineDataSet(yValues1, "Lewe ucho");
        LineDataSet set2 = new LineDataSet(yValues2, "Prawe ucho");
        set1.setFillAlpha(85);
        set2.setFillAlpha(85);
        set1.setColor(Color.BLUE,100);
        set2.setColor(Color.RED,100);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(set1);
        iLineDataSets.add(set2);
        LineData data = new LineData(iLineDataSets);
        mChart.setData(data);




    }
}