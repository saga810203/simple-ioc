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
 * ��ؼ�������ʹ�õı�����.
 * 
 * @author liuwei
 * 
 */
public class FTableColumn implements CellEditorFactory {

	private final static int DEFAULT_WIDTH = 50; // Ĭ���п�

	private String columnName = ""; // ����

	private String propertyName; // ������

	private int width = DEFAULT_WIDTH; // �п�

	private int style = SWT.LEFT; // �е���ʾ����

	private boolean resizable = true; // �Ƿ�������������п�

	private boolean moveable = true; // �Ƿ�����ƶ���

	private CellEditor cellEditor; // �еĵ�Ԫ��༭��

	private boolean canModify = true; // ���Ƿ��ܹ��༭, �ṩCellModifierByColumnʹ��

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
	 * ���������.
	 * 
	 * @param table
	 *            ��
	 * @param columnName
	 *            ����
	 * @param style
	 *            �е�����
	 * @param data
	 *            �еĲ�������
	 * @return ����
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
	 * �õ������ڸ��ж�Ӧ���Ե�ֵ.
	 * 
	 * @param element
	 *            ����
	 * @return �ж�Ӧ���Ե�ֵ
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
	 * ���ö����ڸ��ж�Ӧ���Ե�ֵ.
	 * 
	 * @param element
	 *            ����
	 * @param value
	 *            ����ֵ
	 */
	public void setColumnValue(Object element, Object value) {
		if (element != null) {
			PropertyUtils.setProperty(element, propertyName, value);
		}
	}

	/**
	 * �õ������ڸ�����ʾ���ı�.
	 * 
	 * @param element
	 *            ����
	 * @return ��ʾ���ı�
	 */
	public String getColumnText(Object element) {
		Object value = getColumnValue(element);
		// toStringConvertor.getText(value);
		return value == null ? "" : value.toString();
		// TODO ��ͬ���Ͷ����չ����ʽ��ͬ,��boolean��Date��.
	}

	/**
	 * �õ������ڸ�����ʾ��ͼ��.
	 * 
	 * @param element
	 *            ����
	 * @return ��ʾ��ͼ��
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
