package com.star.common.richclient.factory.composite.support;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.composite.CompositeFactory;

/**
 * ����ĸ��ϵĿؼ������֣�������
 * ���԰���һЩ�ӿؼ������֣����������ÿؼ�������ʱ��ͬʱ�����ӿؼ��������ӿؼ����в��֡�
 * @author liuwei
 * @version 1.0
 */
public abstract class ParameterCompositeFactory extends CompositeFactory{

	private static final Parameters NULL_PARAM = new Parameters(null,null);
	
    private Parameters parameters = NULL_PARAM;
   
    public ParameterCompositeFactory(){}
    
    /**
     * �����塣
     * @param parameters �����ַ���
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
     * ����ӿؼ������֣�������Ŀ�Ƿ�Ϸ���
     * @param expected ϣ�����ӿؼ������֣�������Ŀ
     * @param actual ʵ�ʵ��ӿؼ������֣�������Ŀ
     */
    protected final void checkChildrenNumber(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalArgumentException("args's number[" + actual + "] must == " + expected
                    + ".");
        }
    }

    /**
     * ����ӿؼ������֣�������Ŀ���Ƿ񳬹����ֵ��
     * @param max �ӿؼ������֣�������Ŀ�����ֵ
     * @param actual ʵ�ʵ��ӿؼ������֣�������Ŀ
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
