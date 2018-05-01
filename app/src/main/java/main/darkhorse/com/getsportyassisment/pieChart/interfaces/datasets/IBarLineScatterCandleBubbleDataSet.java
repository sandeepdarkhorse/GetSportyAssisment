package main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets;


import main.darkhorse.com.getsportyassisment.pieChart.data.Entry;

/**
 * Created by philipp on 21/10/15.
 */
public interface IBarLineScatterCandleBubbleDataSet<T extends Entry> extends IDataSet<T> {

    /**
     * Returns the color that is used for drawing the highlight indicators.
     *
     * @return
     */
    int getHighLightColor();
}
