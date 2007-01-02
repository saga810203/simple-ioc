package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.IControlFactory;

/**
 * 边框布局函数。
 * 
 * @author liuwei
 * @version 1.0
 */
public class BorderLayoutFactory extends ParameterCompositeFactory {

	private String direction ;
	
	public BorderLayoutFactory(){}
	/**
	 * 构造体。
	 * 
	 * @param parameters
	 *            参数字符串
	 */
	public BorderLayoutFactory(String id,String parameters,
			IControlFactory... children) {
		super(id,parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		
		GridLayout layout = new GridLayout();
		
		direction = getStrParam("model","n");
		if("n".equals(direction) || "s".equals(direction) ){
			layout.numColumns=1;
		}
		else{
			layout.numColumns=2;
		}
		
		layout.marginLeft = getIntParam("marginLeft",layout.marginLeft);
		layout.marginTop = getIntParam("marginTop",layout.marginTop);
		layout.marginRight = getIntParam("marginRight",layout.marginRight);
		layout.marginBottom = getIntParam("marginBottom",layout.marginBottom);
		layout.marginWidth = getIntParam("marginWidth",layout.marginWidth);
		layout.marginHeight = getIntParam("marginHeight",layout.marginHeight);
		
		layout.horizontalSpacing = getIntParam("horizontalSpacing",layout.horizontalSpacing);
		layout.verticalSpacing = getIntParam("verticalSpacing",layout.verticalSpacing);
		
		layout.makeColumnsEqualWidth = getBoolParam("makeColumnsEqualWidth",layout.makeColumnsEqualWidth);

		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(layout);
		return c;
	}	

	@Override
	protected GridData[] createLayoutDatas(int n) {
		checkChildrenNumber(2,n);
		
		GridData[] d = new GridData[2];
		
		if("n".equals(direction)){
			d[0] = new GridData(GridData.FILL_HORIZONTAL);
			d[1] = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		}
		else if("s".equals(direction)){
			d[0] = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
			d[1] = new GridData(GridData.FILL_HORIZONTAL);
		}
		else if("w".equals(direction)){
			d[0] = new GridData(GridData.FILL_VERTICAL);
			d[1] = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		}
		else if("e".equals(direction)){
			d[0] = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
			d[1] = new GridData(GridData.FILL_VERTICAL);
		}

		return d;
	}

	
	
	@Override
	protected String[] doGetValidParamKey() {
		return new String[] { "model", "marginLeft", "marginTop",
				"marginRight", "marginBottom", "marginWidth", "marginHeight",
				"horizontalSpacing", "verticalSpacing" ,"makeColumnsEqualWidth"};
	}

}
