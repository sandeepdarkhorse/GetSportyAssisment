
package main.darkhorse.com.getsportyassisment.pieChart.charts;

import android.content.Context;
import android.util.AttributeSet;

import main.darkhorse.com.getsportyassisment.pieChart.data.CandleData;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider.CandleDataProvider;
import main.darkhorse.com.getsportyassisment.pieChart.renderer.CandleStickChartRenderer;

//import darkhorsesports.getsporty.pieChart.data.CandleData;
//import darkhorsesports.getsporty.pieChart.interfaces.dataprovider.CandleDataProvider;
//import darkhorsesports.getsporty.pieChart.renderer.CandleStickChartRenderer;

/**
 * Financial chart type that draws candle-sticks (OHCL chart).
 *
 * @author Philipp Jahoda
 */
public class CandleStickChart extends BarLineChartBase<CandleData> implements CandleDataProvider {

    public CandleStickChart(Context context) {
        super(context);
    }

    public CandleStickChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CandleStickChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new CandleStickChartRenderer(this, mAnimator, mViewPortHandler);

        getXAxis().setSpaceMin(0.5f);
        getXAxis().setSpaceMax(0.5f);
    }

    @Override
    public CandleData getCandleData() {
        return mData;
    }
}
