
package main.darkhorse.com.getsportyassisment.pieChart.data;

import java.util.List;

import main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets.ILineDataSet;

//import darkhorsesports.getsporty.pieChart.interfaces.datasets.ILineDataSet;

/**
 * Data object that encapsulates all data associated with a LineChart.
 * 
 * @author Philipp Jahoda
 */
public class LineData extends BarLineScatterCandleBubbleData<ILineDataSet> {

    public LineData() {
        super();
    }

    public LineData(ILineDataSet... dataSets) {
        super(dataSets);
    }

    public LineData(List<ILineDataSet> dataSets) {
        super(dataSets);
    }
}
