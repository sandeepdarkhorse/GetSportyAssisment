package main.darkhorse.com.getsportyassisment.pieChart.renderer;

//import darkhorsesports.getsporty.pieChart.animation.ChartAnimator;
//import darkhorsesports.getsporty.pieChart.data.DataSet;
//import darkhorsesports.getsporty.pieChart.data.Entry;
//import darkhorsesports.getsporty.pieChart.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
//import darkhorsesports.getsporty.pieChart.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
//import darkhorsesports.getsporty.pieChart.interfaces.datasets.IDataSet;
//import darkhorsesports.getsporty.pieChart.utils.ViewPortHandler;

import main.darkhorse.com.getsportyassisment.pieChart.animation.ChartAnimator;
import main.darkhorse.com.getsportyassisment.pieChart.data.DataSet;
import main.darkhorse.com.getsportyassisment.pieChart.data.Entry;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets.IDataSet;
import main.darkhorse.com.getsportyassisment.pieChart.utils.ViewPortHandler;

/**
 * Created by Philipp Jahoda on 09/06/16.
 */
public abstract class BarLineScatterCandleBubbleRenderer extends DataRenderer {

    /**
     * buffer for storing the current minimum and maximum visible x
     */
    protected XBounds mXBounds = new XBounds();

    public BarLineScatterCandleBubbleRenderer(ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
    }

    /**
     * Returns true if the DataSet values should be drawn, false if not.
     *
     * @param set
     * @return
     */
    protected boolean shouldDrawValues(IDataSet set) {
        return set.isVisible() && set.isDrawValuesEnabled();
    }

    /**
     * Checks if the provided entry object is in bounds for drawing considering the current animation phase.
     *
     * @param e
     * @param set
     * @return
     */
    protected boolean isInBoundsX(Entry e, IBarLineScatterCandleBubbleDataSet set) {

        if (e == null)
            return false;

        float entryIndex = set.getEntryIndex(e);

        if (e == null || entryIndex >= set.getEntryCount() * mAnimator.getPhaseX()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Class representing the bounds of the current viewport in terms of indices in the values array of a DataSet.
     */
    protected class XBounds {

        /**
         * minimum visible entry index
         */
        public int min;

        /**
         * maximum visible entry index
         */
        public int max;

        /**
         * range of visible entry indices
         */
        public int range;

        /**
         * Calculates the minimum and maximum x values as well as the range between them.
         *
         * @param chart
         * @param dataSet
         */
        public void set(BarLineScatterCandleBubbleDataProvider chart, IBarLineScatterCandleBubbleDataSet dataSet) {
            float phaseX = Math.max(0.f, Math.min(1.f, mAnimator.getPhaseX()));

            float low = chart.getLowestVisibleX();
            float high = chart.getHighestVisibleX();

            Entry entryFrom = dataSet.getEntryForXValue(low, Float.NaN, DataSet.Rounding.DOWN);
            Entry entryTo = dataSet.getEntryForXValue(high, Float.NaN, DataSet.Rounding.UP);

            min = entryFrom == null ? 0 : dataSet.getEntryIndex(entryFrom);
            max = entryTo == null ? 0 : dataSet.getEntryIndex(entryTo);
            range = (int) ((max - min) * phaseX);
        }
    }
}
