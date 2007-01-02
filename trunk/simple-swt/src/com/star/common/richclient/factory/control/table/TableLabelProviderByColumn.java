package com.star.common.richclient.factory.control.table;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * ��ǩ�������˵�������Ƕ�setInput��������ݼ���������ô��ǩ �����Ƕ����ݼ���ʵ������е��ֶ����ݽ��д����ɱ�ǩ�����������ݼ�¼�ڱ���ÿ
 * һ������ô��ʾ��

 * @author liuwei
 *
 */
public class TableLabelProviderByColumn implements ITableLabelProvider {
	
	private List<FTableColumn> columnList;
	
	public TableLabelProviderByColumn(){}
	
	public TableLabelProviderByColumn(List<FTableColumn> columnList){
		this.columnList=columnList;
	}
	
    /**
     * �ɴ˷����������ݼ�¼�ڱ���ÿһ������ʾʲô����
     * 
     * @param element
     *            ʵ�������
     * @param col
     *            �кţ�0�ǵ�һ��
     * @return ����ֵһ��Ҫ����NULLֵ,�������
     */
    public String getColumnText(Object element, int columnIndex) {
    	FTableColumn column = columnList.get(columnIndex);
        String text = column.getColumnText(element);
        return text==null?"":text;
    }

    /**
     * ����ÿ����¼ǰ���ͼ��
     */
    public Image getColumnImage(Object element, int columnIndex) {
    	FTableColumn column = columnList.get(columnIndex);
    	return column.getColumnImage(element);
    }

	public void setColumnList(List<FTableColumn> columnList) {
		this.columnList = columnList;
	}

	//-------------���·����ô�����,�����ǿ�ʵ�֣����ù�-----------------
    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
    }
}