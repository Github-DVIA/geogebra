package org.geogebra.web.full.gui.toolbarpanel;

import org.geogebra.common.gui.SetLabels;
import org.geogebra.common.gui.view.table.TableValuesListener;
import org.geogebra.common.gui.view.table.TableValuesModel;
import org.geogebra.common.gui.view.table.TableValuesView;
import org.geogebra.common.kernel.kernelND.GeoEvaluatable;
import org.geogebra.web.html5.main.AppW;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * HTML representation of the Table of Values View.
 *
 * @author laszlo
 *
 */
public class TableValuesPanel extends FlowPanel
		implements SetLabels, TableValuesListener {

	private static final int TOOLBAR_HEADER_HEIGHT = 48;

	/** view of table values */
	TableValuesView view;
	private StickyValuesTable table;
	private TableValuesEmptyPanel emptyPanel;

	/**
	 * @param app
	 *            {@link AppW}.
	 */
	public TableValuesPanel(AppW app) {
		super();
		view = (TableValuesView) app.getGuiManager().getTableValuesView();
		view.getTableValuesModel().registerListener(this);
		emptyPanel = new TableValuesEmptyPanel(app);
		table = new StickyValuesTable(app, view);
		add(emptyPanel);
		add(table);
	}

	private void showEmptyView() {
		emptyPanel.show();
		table.hide();
		removeStyleName("tvTable");
		addStyleName("emptyTablePanel");
		addParentClassName("tableViewParent");
	}

	private void showTableView() {
		emptyPanel.hide();
		table.show();
		addStyleName("tvTable");
		removeParentClassName("tableViewParent");
		removeStyleName("emptyTablePanel");
	}

	private void addParentClassName(String className) {
		Element parent = getElement().getParentElement();
		if (parent != null) {
			parent.addClassName(className);
		}
	}

	private void removeParentClassName(String className) {
		Element parent = getElement().getParentElement();
		if (parent != null) {
			parent.removeClassName(className);
		}
	}

	@Override
	public void setLabels() {
		emptyPanel.setLabels();
	}

	/**
	 * Sets height of the view.
	 *
	 * @param height
	 *            to set.
	 */
	public void setHeight(int height) {
		table.setHeight(height - TOOLBAR_HEADER_HEIGHT);
	}

	/**
	 * Deletes the specified column from the view.
	 *
	 * @param column
	 *            column to delete.
	 * @param cb
	 *            to run on transition end.
	 */
	public void deleteColumn(int column, Runnable cb) {
		// not used.
	}

	@Override
	public void notifyColumnRemoved(TableValuesModel model, GeoEvaluatable evaluatable,
			int column) {

	}

	@Override
	public void notifyColumnChanged(TableValuesModel model, GeoEvaluatable evaluatable,
			int column) {
		// not used.
	}

	@Override
	public void notifyColumnAdded(TableValuesModel model, GeoEvaluatable evaluatable, int column) {
		if (model.getColumnCount() == 2) {
			showTableView();
		}
	}

	@Override
	public void notifyColumnHeaderChanged(TableValuesModel model, GeoEvaluatable evaluatable,
			int column) {
		// not used.
	}

	@Override
	public void notifyDatasetChanged(TableValuesModel model) {
		// not used.
	}

	@Override
	public void onAttach() {
		super.onAttach();

		if (view.isEmpty()) {
			showEmptyView();
		} else {
			showTableView();
		}
	}

	/**
	 * Scroll table view to the corresponding column of the geo.
	 *
	 * @param geo
	 *            to scroll.
	 */
	public void scrollTo(GeoEvaluatable geo) {
		table.scrollTo(geo);
	}
}
