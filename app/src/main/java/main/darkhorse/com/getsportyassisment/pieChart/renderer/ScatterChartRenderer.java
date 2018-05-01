
package main.darkhorse.com.getsportyassisment.pieChart.renderer;

import android.graphics.Canvas;
import android.util.Log;

import java.util.List;

import main.darkhorse.com.getsportyassisment.pieChart.animation.ChartAnimator;
import main.darkhorse.com.getsportyassisment.pieChart.data.Entry;
import main.darkhorse.com.getsportyassisment.pieChart.data.ScatterData;
import main.darkhorse.com.getsportyassisment.pieChart.highlight.Highlight;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.dataprovider.ScatterDataProvider;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets.IScatterDataSet;
import main.darkhorse.com.getsportyassisment.pieChart.renderer.scatter.IShapeRenderer;
import main.darkhorse.com.getsportyassisment.pieChart.utils.MPPointD;
import main.darkhorse.com.getsportyassisment.pieChart.utils.Transformer;
import main.darkhorse.com.getsportyassisment.pieChart.utils.Utils;
import main.darkhorse.com.getsportyassisment.pieChart.utils.ViewPortHandler;

//import darkhorsesports.getsporty.pieChart.animation.ChartAnimator;
//import darkhorsesports.getsporty.pieChart.data.Entry;
//import darkhorsesports.getsporty.pieChart.data.ScatterData;
//import darkhorsesports.getsporty.pieChart.highlight.Highlight;
//import darkhorsesports.getsporty.pieChart.interfaces.dataprovider.ScatterDataProvider;
//import darkhorsesports.getsporty.pieChart.interfaces.datasets.IScatterDataSet;
//import darkhorsesports.getsporty.pieChart.renderer.scatter.IShapeRenderer;
//import darkhorsesports.getsporty.pieChart.utils.MPPointD;
//import darkhorsesports.getsporty.pieChart.utils.Transformer;
//import darkhorsesports.getsporty.pieChart.utils.Utils;
//import darkhorsesports.getsporty.pieChart.utils.ViewPortHandler;

public class ScatterChartRenderer extends LineScatterCandleRadarRenderer {

    protected ScatterDataProvider mChart;

    public ScatterChartRenderer(ScatterDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        mChart = chart;
    }

    @Override
    public void initBuffers() {
    }

    @Override
    public void drawData(Canvas c) {

        ScatterData scatterData = mChart.getScatterData();

        for (IScatterDataSet set : scatterData.getDataSets()) {

            if (set.isVisible())
                drawDataSet(c, set);
        }
    }

    float[] mPixelBuffer = new float[2];

    protected void drawDataSet(Canvas c, IScatterDataSet dataSet) {

        ViewPortHandler viewPortHandler = mViewPortHandler;

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        float phaseY = mAnimator.getPhaseY();

        IShapeRenderer renderer = dataSet.getShapeRenderer();
        if (renderer == null) {
            Log.i("MISSING", "There's no IShapeRenderer specified for ScatterDataSet");
            return;
        }

        int max = (int)(Math.min(
                Math.ceil((float)dataSet.getEntryCount() * mAnimator.getPhaseX()),
                (float)dataSet.getEntryCount()));

        for (int i = 0; i < max; i++) {

            Entry e = dataSet.getEntryForIndex(i);

            mPixelBuffer[0] = e.getX();
            mPixelBuffer[1] = e.getY() * phaseY;

            trans.pointValuesToPixel(mPixelBuffer);

            if (!viewPortHandler.isInBoundsRight(mPixelBuffer[0]))
                break;

            if (!viewPortHandler.isInBoundsLeft(mPixelBuffer[0])
                    || !viewPortHandler.isInBoundsY(mPixelBuffer[1]))
                continue;

            mRenderPaint.setColor(dataSet.getColor(i / 2));
            renderer.renderShape(
                    c, dataSet, mViewPortHandler,
                    mPixelBuffer[0], mPixelBuffer[1],
                    mRenderPaint);
        }
    }

    @Override
    public void drawValues(Canvas c) {

        // if values are drawn
        if (isDrawingValuesAllowed(mChart)) {

            List<IScatterDataSet> dataSets = mChart.getScatterData().getDataSets();

            for (int i = 0; i < mChart.getScatterData().getDataSetCount(); i++) {

                IScatterDataSet dataSet = dataSets.get(i);

                if (!shouldDrawValues(dataSet))
                    continue;

                // apply the text-styling defined by the DataSet
                applyValueTextStyle(dataSet);

                mXBounds.set(mChart, dataSet);

                float[] positions = mChart.getTransformer(dataSet.getAxisDependency())
                        .generateTransformedValuesScatter(dataSet,
                                mAnimator.getPhaseX(), mAnimator.getPhaseY(), mXBounds.min, mXBounds.max);

                float shapeSize = Utils.convertDpToPixel(dataSet.getScatterShapeSize());

                for (int j = 0; j < positions.length; j += 2) {

                    if (!mViewPortHandler.isInBoundsRight(positions[j]))
                        break;

                    // make sure the lines don't do shitty things outside bounds
                    if ((!mViewPortHandler.isInBoundsLeft(positions[j])
                            || !mViewPortHandler.isInBoundsY(positions[j + 1])))
                        continue;

                    Entry entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min);

                    drawValue(c, dataSet.getValueFormatter(), entry.getY(), entry, i, positions[j],
                            positions[j + 1] - shapeSize, dataSet.getValueTextColor(j / 2 + mXBounds.min));
                }
            }
        }
    }

    @Override
    public void drawExtras(Canvas c) {
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {

        ScatterData scatterData = mChart.getScatterData();

        for (Highlight high : indices) {

            IScatterDataSet set = scatterData.getDataSetByIndex(high.getDataSetIndex());

            if (set == null || !set.isHighlightEnabled())
                continue;

            final Entry e = set.getEntryForXValue(high.getX(), high.getY());

            if (!isInBoundsX(e, set))
                continue;

            MPPointD pix = mChart.getTransformer(set.getAxisDependency()).getPixelForValues(e.getX(), e.getY() * mAnimator
                    .getPhaseY());

            high.setDraw((float) pix.x, (float) pix.y);

            // draw the lines
            drawHighlightLines(c, (float) pix.x, (float) pix.y, set);
        }
    }
}
