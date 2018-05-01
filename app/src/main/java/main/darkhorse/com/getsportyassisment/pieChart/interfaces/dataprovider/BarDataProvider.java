package main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider;


import main.darkhorse.com.getsportyassisment.pieChart.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isHighlightFullBarEnabled();
}
