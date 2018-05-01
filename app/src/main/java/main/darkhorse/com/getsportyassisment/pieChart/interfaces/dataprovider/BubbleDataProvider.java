package main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider;


import main.darkhorse.com.getsportyassisment.pieChart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
