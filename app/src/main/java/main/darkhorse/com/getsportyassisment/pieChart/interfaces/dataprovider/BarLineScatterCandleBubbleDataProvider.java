package main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider;


import main.darkhorse.com.getsportyassisment.pieChart.components.YAxis;
import main.darkhorse.com.getsportyassisment.pieChart.data.BarLineScatterCandleBubbleData;
import main.darkhorse.com.getsportyassisment.pieChart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
