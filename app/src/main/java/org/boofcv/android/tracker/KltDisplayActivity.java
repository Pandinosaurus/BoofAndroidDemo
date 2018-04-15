package org.boofcv.android.tracker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import org.boofcv.android.R;

import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;

/**
 * Displays KLT tracks.
 *
 * @author Peter Abeles
 */
public class KltDisplayActivity extends PointTrackerDisplayActivity {

	public KltDisplayActivity() {
		super(Resolution.R640x480);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.klt_controls,null);

		SeekBar seek = controls.findViewById(R.id.slider_tracks);
		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				ConfigGeneralDetector config = new ConfigGeneralDetector();
				config.maxFeatures = progress+1;
				config.threshold = 20;
				config.radius = 3;

				PointTracker<GrayU8> tracker =
						FactoryPointTracker.klt(new int[]{1,2,4},config,3,GrayU8.class, GrayS16.class);

				Log.i("KLT","threshold = "+progress);
				setProcessing(new PointProcessing(tracker));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		seek.setProgress(150);

		setControls(controls);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
}