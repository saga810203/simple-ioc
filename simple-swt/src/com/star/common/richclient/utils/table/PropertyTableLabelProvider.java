package com.star.common.richclient.utils.table;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.star.common.util.bean.PropertyUtils;

/**
 * ��ǩ�������˵�������Ƕ�setInput��������ݼ���������ô��ǩ �����Ƕ����ݼ���ʵ������е��ֶ����ݽ��д����ɱ�ǩ�����������ݼ�¼�ڱ���ÿ
 * һ������ô��ʾ��
 * 
 * @author liuwei
 * 
 */
public class PropertyTableLabelProvider implements ITableLabelProvider {

	protected String[] properties;

	public PropertyTableLabelProvider(String... properties) {
		this.properties = properties;
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
		Object o = PropertyUtils.getProperty(element, properties[columnIndex]);
		String text = o == null ? "" : o.toString();
		return text == null ? "" : text;
	}

	/**
	 * ����ÿ����¼ǰ���ͼ��
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	// -------------���·����ô�����,�����ǿ�ʵ�֣����ù�-----------------
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