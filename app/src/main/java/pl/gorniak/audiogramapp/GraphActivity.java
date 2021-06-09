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

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

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

        //10,20,30,40,50,60,70, 80, 90, 100, 110 dB --> dopuszczalne ubytki sluchu
        ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        int[] leftEar = extras.getIntArray("leftEar");
        int[] rightEar = extras.getIntArray("rightEar");
        //ucho lewe
        yValues1.add(new Entry(125, leftEar[0]));
        yValues1.add(new Entry(250, leftEar[1]));
        yValues1.add(new Entry(500, leftEar[2]));
        yValues1.add(new Entry(1000, leftEar[3]));
        yValues1.add(new Entry(1500, leftEar[4]));
        yValues1.add(new Entry(2000, leftEar[5]));
        yValues1.add(new Entry(3000, leftEar[6]));
        yValues1.add(new Entry(4000, leftEar[7]));
        yValues1.add(new Entry(6000, leftEar[8]));
        //ucho prawe
        yValues2.add(new Entry(125, rightEar[0]));
        yValues2.add(new Entry(250, rightEar[1]));
        yValues2.add(new Entry(500, rightEar[2]));
        yValues2.add(new Entry(1000, rightEar[3]));
        yValues2.add(new Entry(1500, rightEar[4]));
        yValues2.add(new Entry(2000, rightEar[5]));
        yValues2.add(new Entry(3000, rightEar[6]));
        yValues2.add(new Entry(4000, rightEar[7]));
        yValues2.add(new Entry(6000, rightEar[8]));

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
        mChart.getAxisLeft().setInverted(true);
        mChart.setTransitionName("test");
    }
}