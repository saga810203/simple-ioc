package com.star.common.richclient.factory.composite.support;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.composite.CompositeFactory;

/**
 * 抽象的复合的控件（布局）函数。
 * 可以包含一些子控件（布局）函数，当该控件被创建时，同时创建子控件，并将子控件进行布局。
 * @author liuwei
 * @version 1.0
 */
public abstract class ParameterCompositeFactory extends CompositeFactory{

	private static final Parameters NULL_PARAM = new Parameters(null,null);
	
    private Parameters parameters = NULL_PARAM;
   
    public ParameterCompositeFactory(){}
    
    /**
     * 构造体。
     * @param parameters 参数字符串
     */
    public ParameterCompositeFactory(String id, String parameters, IControlFactory... children) {
    	super(id,children);
    	setParameters(parameters);
    }

    public void setParameters(String parameters){
    	this.parameters = new Parameters(doGetValidParamKey(), parameters);
    }
       
	protected abstract Composite doCreateComposite(Composite parent);

	//============ ======== ==============
	
    /**
     * @see Parameters
     */
    protected String[] doGetValidParamKey() {
        return null;
    }
    
    /**
     * 检查子控件（布局）函数数目是否合法。
     * @param expected 希望的子控件（布局）函数数目
     * @param actual 实际的子控件（布局）函数数目
     */
    protected final void checkChildrenNumber(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalArgumentException("args's number[" + actual + "] must == " + expected
                    + ".");
        }
    }

    /**
     * 检查子控件（布局）函数数目的是否超过最大值。
     * @param max 子控件（布局）函数数目的最大值
     * @param actual 实际的子控件（布局）函数数目
     */
    protected final void checkChildrenNumberMax(int max, int actual) {
        if (actual > max) {
            throw new IllegalArgumentException("args's number[" + actual + "] must < " + max
                    + ".");
        }
    }

	
    // ==========================================


	/**.@see */
    protected final boolean containsParamKey(String key) {
        return parameters.containsParamKey(key);
    }
    /**.@see */
    protected final boolean getBoolParam(String key, boolean defaultValue) {
        return parameters.getBoolParam(key, defaultValue);
    }
    /**.@see */
    protected final boolean getBoolParam(String key) {
        return parameters.getBoolParam(key);
    }
    /**.@see */
    protected final int getIntParam(String key, int defaultValue) {
        return parameters.getIntParam(key, defaultValue);
    }
    /**.@see */
    protected final int getIntParam(String key) {
        return parameters.getIntParam(key);
    }
    /**.@see */
    protected final float getFloatParam(String key, float defaultValue) {
        return parameters.getFloatParam(key, defaultValue);
    }
    /**.@see */
    protected final float getFloatParam(String key) {
        return parameters.getFloatParam(key);
    }
    /**.@see */
    protected final Object getParam(String key, Object defaultValue) {
        return parameters.getParam(key, defaultValue);
    }
    /**.@see */
    protected final Object getParam(String key) {
        return parameters.getParam(key);
    }
    /**.@see */
    protected final Map<String, Object> getParamMap() {
        return parameters.getParamMap();
    }
    /**.@see */
    protected final String getStrParam(String key, String defaultValue) {
        return parameters.getStrParam(key, defaultValue);
    }
    /**.@see */
    protected final String getStrParam(String key) {
        return parameters.getStrParam(key);
    }
    /**.@see */
    protected final void throwParameterError(String parameterKey) {
        parameters.throwParameterError(parameterKey);
    }
}
