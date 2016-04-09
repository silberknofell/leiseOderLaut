package de.geihe.leiseOderLaut.timeView;

import java.util.HashMap;
import java.util.List;

import de.geihe.leiseOderLaut.mode.Mode;

public class TimeViewManager {

	private final TimeViewFactory timeViewFactory;
	protected HashMap<Integer, List<TimeView>> viewsMap;


	public TimeViewManager(TimeViewFactory timeViewFactory) {
		this.timeViewFactory = timeViewFactory;
		viewsMap = new HashMap<Integer, List<TimeView>>();
		for (int mode = 1; mode <= Mode.ANZAHL; mode++) {
			createViews(mode);
		}
	}

	public void   createViews() {
		int mode = Mode.readModeFromPrefs();
		createViews(mode);
	}

	private void  createViews(int mode) {
		removeViews(mode);
		viewsMap.put(mode, timeViewFactory.createTimeViewList(mode));
	}

	public void showViews(int mode) {
		showViews(viewsMap.get(mode));
	}

	private void showViews(List<TimeView> views) {
		if (views!=null && views.size()>0) {
			for (TimeView tv : views) {
				tv.show();
			}
		}
	}

	public List<TimeView> getTimeViewList(int mode) {
		return viewsMap.get(mode);
	}

	public void removeViews(int mode) {
		List<TimeView> views = viewsMap.get(mode);
		if (views!=null && views.size()>0) {
			for (TimeView tv : views) {
				tv.remove();
			}
		}
	}

	public void update(int mode) {
		for (TimeView tv : viewsMap.get(mode)) {
			tv.update();
		}
	}
}
