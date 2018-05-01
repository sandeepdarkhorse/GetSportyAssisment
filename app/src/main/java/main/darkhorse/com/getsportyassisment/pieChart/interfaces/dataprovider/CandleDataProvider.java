package main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider;


import main.darkhorse.com.getsportyassisment.pieChart.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
