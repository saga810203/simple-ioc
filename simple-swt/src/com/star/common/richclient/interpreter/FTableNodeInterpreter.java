package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.richclient.factory.control.table.FTable;
import com.star.common.richclient.factory.control.table.FTableColumn;
import com.star.common.util.Node;
import com.star.common.util.StringUtils;
import com.star.common.util.bean.PropertyUtils;

public class FTableNodeInterpreter extends
		AbstractControlFactoryNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		FTable table = null;
		if(valueString.trim().length()!=0){
			table = beanFactory.getBean(valueString.trim(),FTable.class).clone();
		}
		else{
			table = new FTable();
		}
		
		table.setId(node.getKey());

		for (Node n : node.getChildren()) {
			String key = n.getKey();
			String value = n.getValue();
			if (key.startsWith(".(")) {
				Object attributeValue = beanFactory.getBean(value);
				table.setAttribute(key.substring(2, key.length() - 1),
						attributeValue);
			} else if (key.startsWith(".")) {
				Object propertyValue = beanFactory.getBean(value);
				PropertyUtils.setProperty(table, key.substring(1),
						propertyValue);
			} else if ("<<".equals(key)) {
				FTable mix = beanFactory.getBean(value, FTable.class);
				for (FTableColumn col : mix.getColumnList()) {
					if (!table.hasProperty(col.getPropertyName())) {
						table.addColumn(col);
					}
				}
			} else {
				// name(50) => [name,50]
				String[] temps = StringUtils.splite2(key);
				String propertyName = temps[0];
				int width = StringUtils.parseToInt(temps[1], -1);
				String columnName = getText(value, propertyName);

				FTableColumn col = new FTableColumn();
				col.setPropertyName(propertyName);
				col.setWidth(width);
				col.setColumnName(columnName);

				for (Node cn : n.getChildren()) {
					Object propertyValue = beanFactory.getBean(cn, context);
					PropertyUtils.setProperty(col, cn.getKey(), propertyValue);
				}

				table.addColumn(col);
			}
		}
		return table;
	}

}
