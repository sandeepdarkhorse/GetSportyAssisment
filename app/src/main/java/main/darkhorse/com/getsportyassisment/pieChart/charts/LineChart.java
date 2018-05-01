
package main.darkhorse.com.getsportyassisment.pieChart.charts;

import android.content.Context;
import android.util.AttributeSet;

import main.darkhorse.com.getsportyassisment.pieChart.data.LineData;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider.LineDataProvider;
import main.darkhorse.com.getsportyassisment.pieChart.renderer.LineChartRenderer;

//import darkhorsesports.getsporty.pieChart.data.LineData;
//import darkhorsesports.getsporty.pieChart.interfaces.dataprovider.LineDataProvider;
//import darkhorsesports.getsporty.pieChart.renderer.LineChartRenderer;


/**
 * Chart that draws lines, surfaces, circles, ...
 *
 * @author Philipp Jahoda
 */
public class LineChart extends BarLineChartBase<LineData> implements LineDataProvider {

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new LineChartRenderer(this, mAnimator, mViewPortHandler);
    }

    @Override
    public LineData getLineData() {
        return mData;
    }

    @Override
    protected void onDetachedFromWindow() {
        // releases the bitmap in the renderer to avoid oom error
        if (mRenderer != null && mRenderer instanceof LineChartRenderer) {
            ((LineChartRenderer) mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }
}
