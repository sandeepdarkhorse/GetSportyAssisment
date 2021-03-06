package main.darkhorse.com.getsportyassisment.pieChart.listener;

//import darkhorsesports.getsporty.pieChart.data.DataSet;
//import darkhorsesports.getsporty.pieChart.data.Entry;

import main.darkhorse.com.getsportyassisment.pieChart.data.DataSet;
import main.darkhorse.com.getsportyassisment.pieChart.data.Entry;

/**
 * Listener for callbacks when drawing on the chart.
 * 
 * @author Philipp
 * 
 */
public interface OnDrawListener {

	/**
	 * Called whenever an entry is added with the finger. Note this is also called for entries that are generated by the
	 * library, when the touch gesture is too fast and skips points.
	 * 
	 * @param entry
	 *            the last drawn entry
	 */
	void onEntryAdded(Entry entry);

	/**
	 * Called whenever an entry is moved by the User after beeing highlighted
	 * 
	 * @param entry
	 */
	void onEntryMoved(Entry entry);

	/**
	 * Called when drawing finger is lifted and the draw is finished.
	 * 
	 * @param dataSet
	 *            the last drawn DataSet
	 */
	void onDrawFinished(DataSet<?> dataSet);

}
