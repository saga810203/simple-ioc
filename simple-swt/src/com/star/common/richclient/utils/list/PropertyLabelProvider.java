package com.star.common.richclient.utils.list;

import org.eclipse.jface.viewers.LabelProvider;

import com.star.common.util.bean.PropertyUtils;

public class PropertyLabelProvider extends LabelProvider {

	private String renderPropertyName;

	public PropertyLabelProvider(){}

	public PropertyLabelProvider(String propertyName){
		this.renderPropertyName=propertyName;
	}

	public String getText(Object element) {
		if(renderPropertyName==null){
			return super.getText(element);
		}

    	if(element!=null){
	    	try {
				Object o = PropertyUtils.getProperty(element,renderPropertyName);
				if(o!=null){
					return o.toString();
				}
			} catch (NullPointerException e) {
				// nothing
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
    	}
        return "";
	}

	public String getRenderPropertyName() {
		return renderPropertyName;
	}

	public void setRenderPropertyName(String propertyName) {
		this.renderPropertyName = propertyName;
	}



}