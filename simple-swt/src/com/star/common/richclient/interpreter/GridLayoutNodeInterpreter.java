package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.NodeInterpreter;
import com.star.common.richclient.layout.GridLayoutTemplate;
import com.star.common.util.Node;

public class GridLayoutNodeInterpreter implements NodeInterpreter {

	// twoColumnFormLayout : (gridLayoutTemplate)
	// -data: h=l:p:n | h=f:p:g || h=l:p:n | h=f:p:g
	// -swap: true

	public Object createObject(Node node, String valueString,
			String interpreterName, Map<Object, Object> context,
			BeanFactory beanFactory) {
		String dataStr = node.findChildByKey("data").getValue();
		String[] datas = dataStr.split("\\s+");
		boolean swap = Boolean.valueOf(node.findChildByKey("swap").getValue());
		GridLayoutTemplate temp = new GridLayoutTemplate(datas, swap);
		return temp;
	}
}
