package main.darkhorse.com.getsportyassisment.pieChart.data;

import java.util.List;

import main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets.ICandleDataSet;


public class CandleData extends BarLineScatterCandleBubbleData<ICandleDataSet> {

    public CandleData() {
        super();
    }

    public CandleData(List<ICandleDataSet> dataSets) {
        super(dataSets);
    }

    public CandleData(ICandleDataSet... dataSets) {
        super(dataSets);
    }
}
