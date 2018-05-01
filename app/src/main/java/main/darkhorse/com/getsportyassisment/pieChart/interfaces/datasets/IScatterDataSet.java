package main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets;

//import darkhorsesports.getsporty.pieChart.data.Entry;
//import darkhorsesports.getsporty.pieChart.renderer.scatter.IShapeRenderer;

import main.darkhorse.com.getsportyassisment.pieChart.data.Entry;
import main.darkhorse.com.getsportyassisment.pieChart.renderer.scatter.IShapeRenderer;

/**
 * Created by philipp on 21/10/15.
 */
public interface IScatterDataSet extends ILineScatterCandleRadarDataSet<Entry> {

    /**
     * Returns the currently set scatter shape size
     *
     * @return
     */
    float getScatterShapeSize();

    /**
     * Returns radius of the hole in the shape
     *
     * @return
     */
    float getScatterShapeHoleRadius();

    /**
     * Returns the color for the hole in the shape
     *
     * @return
     */
    int getScatterShapeHoleColor();

    /**
     * Returns the IShapeRenderer responsible for rendering this DataSet.
     *
     * @return
     */
    IShapeRenderer getShapeRenderer();
}
