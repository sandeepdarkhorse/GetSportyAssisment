package main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider;


import main.darkhorse.com.getsportyassisment.pieChart.components.YAxis;
import main.darkhorse.com.getsportyassisment.pieChart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
