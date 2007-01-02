package com.star.common.richclient.factory.control.table;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.star.common.richclient.Constant;
import com.star.common.util.bean.PropertyUtils;

/**
 * 表控件工厂中使用的表列类.
 * 
 * @author liuwei
 * 
 */
public class FTableColumn implements CellEditorFactory {

	private final static int DEFAULT_WIDTH = 50; // 默认列宽

	private String columnName = ""; // 列名

	private String propertyName; // 属性名

	private int width = DEFAULT_WIDTH; // 列宽

	private int style = SWT.LEFT; // 列的显示类型

	private boolean resizable = true; // 是否可以重新设置列宽

	private boolean moveable = true; // 是否可以移动列

	private CellEditor cellEditor; // 列的单元格编辑器

	private boolean canModify = true; // 列是否能够编辑, 提供CellModifierByColumn使用

	// private Object toStringConvertor;

	public TableColumn addTo(Table table) {
		ColumnLayoutData data = createColumnLayoutData();
		TableColumn c = addColumn(table, columnName, style, data);
		c.setResizable(resizable);
		c.setMoveable(moveable);
		c.setData(Constant.PROPERTY, propertyName);
		c.setData(Constant.F_TABLE_COLUMN, this);
		return c;
	}

	protected ColumnLayoutData createColumnLayoutData() {
		ColumnLayoutData data = new ColumnPixelData(width, resizable);
		// new ColumnPixelData(int widthInPixels, boolean resizable, boolean
		// addTrim)
		// new ColumnWeightData(int weight, int minimumWidth, boolean resizable)
		return data;
	}

	/**
	 * 给表加入列.
	 * 
	 * @param table
	 *            表
	 * @param columnName
	 *            列名
	 * @param style
	 *            列的类型
	 * @param data
	 *            列的布局数据
	 * @return 表列
	 */
	public TableColumn addColumn(Table table, String columnName, int style,
			ColumnLayoutData data) {
		TableLayout layout = (TableLayout) table.getLayout();
		layout.addColumnData(data);
		TableColumn col = new TableColumn(table, style);
		col.setText(columnName);
		col.setData(Constant.COLUMN_INDEX, table.getColumnCount() - 1);
		return col;
	}

	/**
	 * 得到对象在该列对应属性的值.
	 * 
	 * @param element
	 *            对象
	 * @return 列对应属性的值
	 */
	public Object getColumnValue(Object element) {
		Object value = null;

		if (element != null) {
			value = PropertyUtils.getProperty(element, propertyName);
		}
		if (value == null && cellEditor != null
				&& cellEditor.getClass().equals(TextCellEditor.class)) {
			value = "";
		}

		return value;
	}

	/**
	 * 设置对象在该列对应属性的值.
	 * 
	 * @param element
	 *            对象
	 * @param value
	 *            属性值
	 */
	public void setColumnValue(Object element, Object value) {
		if (element != null) {
			PropertyUtils.setProperty(element, propertyName, value);
		}
	}

	/**
	 * 得到对象在该列显示的文本.
	 * 
	 * @param element
	 *            对象
	 * @return 显示的文本
	 */
	public String getColumnText(Object element) {
		Object value = getColumnValue(element);
		// toStringConvertor.getText(value);
		return value == null ? "" : value.toString();
		// TODO 不同类型对象的展现形式不同,如boolean、Date等.
	}

	/**
	 * 得到对象在该列显示的图标.
	 * 
	 * @param element
	 *            对象
	 * @return 显示的图标
	 */
	public Image getColumnImage(Object element) {
		return null;
	}

	public CellEditor create(Table table) {
		if (cellEditor != null) {
			CellEditor editor = null;
			try {
				editor = cellEditor.getClass().newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			editor.setStyle(cellEditor.getStyle());
			editor.setValidator(cellEditor.getValidator());
			editor.create(table);
			return editor;
		}
		return null;
	}

	// ====================GET/SET======================

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isCanModify() {
		return canModify;
	}

	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}

	public void setCellEditor(CellEditor cellEditor) {
		this.cellEditor = cellEditor;
	}
}
