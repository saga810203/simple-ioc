package com.star.common.ioc.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.star.common.ioc.BeanFactory;
import com.star.common.util.ClassUtils;
import com.star.common.util.Node;
import com.star.common.util.StringUtils;

/**
 * 默认的对象工厂。 <br>
 * 通过加入节点解析器（NodeInterpreter），可以扩展能够解析的节点。<br>
 * 一般需要注册@default节点解析器，该解析器必须指定某个解析器（一般使用boot解析器）加载。<br>
 * Node.value属性如果存在"(...)"前缀，则DefaultBeanFactory会首先查找是否有相应的"@..."节点解析器，
 * 如果存在则使用该节点解析器处理节点。<br>
 * 参考“default-interpreter-config.properties”文件注册节点解析器。
 * 
 * @see com.star.common.ioc.support.interpreter.*
 * @author liuwei
 * @version 1.1
 */
public class DefaultBeanFactory implements BeanFactory {

    private static final char INTERPRETER_KEY_PREFIX = '@';

    private static final String DEFAULT_INTERPRETER_NAME = "default";

    private static final String BOOT_INTERPRETER_NAME = "boot";

    /**
     * 引导节点解释器。
     */
    private NodeInterpreter bootNodeInterpreter;

    /**
     * 配置树Map。
     */
    private Map<String, Node> configMap;

    /**
     * 缓存bean的Map。
     */
    private Map<Node, Object> beanCacheMap;

    /**
     * 父BeanFactory。
     */
    private BeanFactory parent;
    
    private List<BeanFactory> children;


    public DefaultBeanFactory(Map<String, Node> configTreeMap) {
    	this.children=new ArrayList<BeanFactory>();
        this.configMap = configTreeMap;
        this.bootNodeInterpreter = new BootNodeInterpreter();
        this.beanCacheMap = new WeakHashMap<Node, Object>();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.star.common.ioc.BeanFactory#getBean(java.lang.String)
     */
    public Object getBean(String beanId) {
        return getBean(beanId, (Map<Object, Object>) null);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.star.common.ioc.BeanFactory#getBean(java.lang.String,
     *      java.lang.Class)
     */
    public <T> T getBean(String beanId, Class<T> requiredType) {
        return getBean(beanId, null, requiredType);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanId, Map<Object, Object> context, Class<T> requiredType) {
        Object o = getBean(beanId, context);
        if (o == null || requiredType == null || requiredType.isAssignableFrom(o.getClass())) {
            return (T) o;
        }
        throw new RuntimeException("bean(beanId=" + beanId + ") isn't assignable from "
                + requiredType.getName());
    }

    /**
     * 该方法如果传入的beanId中一个字符是'('，则认为不是真是的beanId，而是一个需要处理的valueString。
     * 此时将创建一个临时Node，它的value等于beanId，并处理该节点，将解释器处理的结果返回。
     * <p>
     * 有时处理节点时，下一个节点解释器需要一个上下文环境，此时可以调用该方法，并传入一个上下文信息。
     * 可以参见BeanNodeInterpreter和FactoryRefNodeInterpreter中的配合使用方法。
     * 
     * @param beanId
     * @param context
     *            上下文Map
     * @return
     */
    public Object getBean(String beanId, Map<Object, Object> context) {
        if (beanId == null || beanId.length() == 0) {
            throw new IllegalArgumentException("beanId can't be null or empty.");
        }
        Node node = null;
        if (beanId.charAt(0) == '(') {
            node = new Node();
            node.setValue(beanId);
        }
        else if (containsBean(beanId)) {
            node = getConfigMap().get(beanId);
        }
        else {
            if (parent != null) {
                return parent.getBean(beanId, context);
            }
            throw new IllegalArgumentException("bean[" + beanId + "] haven't a define.");
        }
        return getBean(node, node.getValue(), context);
    }

    public Object getBean(Node node) {
        return getBean(node, null);
    }

    public Object getBean(Node node, Map<Object, Object> context) {
        return getBean(node, node.getValue(), context);
    }

    /**
     * 得到node对应的对象。通过传入valueString，可以递规的处理node.value中包含的前缀“(...)”。 <br>
     * 如：node.value="(a)(b)(c)text"，首先node将被a节点解释器处理，
     * 此时a节点解释器，可以调用本方法并将它处理时的valueString（此时应为"(b)(c)text"）传入， 就可以使用b阶段解释器处理节点。
     * 
     * @param node
     *            被处理的节点
     * @param valueString
     *            处理的value字符串
     * @param context
     * @return
     */
    public Object getBean(Node node, String valueString, Map<Object, Object> context) {
        String[] ics = StringUtils.split1(valueString);
        String valueString2 = ics[0];
        String interpreterName = ics[1];

        return interprete(node, valueString2, interpreterName, context);
    }

    private Object interprete(Node node, String valueString, String interpreterName,
            Map<Object, Object> context) {
        if (interpreterName == null) {
            interpreterName = DEFAULT_INTERPRETER_NAME;
        }
        NodeInterpreter interpreter = null;
        if (BOOT_INTERPRETER_NAME.equals(interpreterName)) {
            interpreter = bootNodeInterpreter;
        }
        else {
            interpreter = getBean(INTERPRETER_KEY_PREFIX + interpreterName, NodeInterpreter.class);
        }
        return interpreter.createObject(node, valueString, interpreterName, context, this);
    }

    // ===========================================

    /**
     * (non-Javadoc)
     * 
     * @see com.star.common.ioc.BeanFactory#containsBean(java.lang.String)
     */
    public boolean containsBean(String beanId) {
        return getConfigMap().get(beanId) != null;
    }

    protected Map<String, Node> getConfigMap() {
        return configMap;
    }

    public BeanFactory getParent() {
        return parent;
    }

    public void setParent(BeanFactory parent) {
        removeFormParent();
        this.parent = parent;
        addToParent();
    }
    /**
     * 引导解析器。简单的使用节点的value作为类名进行实例化并作为结果返回。
     * @author liuwei
     * @version 1.0
     */
	private void addToParent() {
		if(parent instanceof DefaultBeanFactory){
        	((DefaultBeanFactory)parent).children.add(this);
        }
	}

	private void removeFormParent() {
		if(parent instanceof DefaultBeanFactory){
        	((DefaultBeanFactory)parent).children.remove(this);
        }
	}
    
    public List<BeanFactory> getChildren(){
    	return this.children;
    }
    
    private class BootNodeInterpreter implements NodeInterpreter {

        private Map<String, Object> cache = new HashMap<String, Object>();

        public Object createObject(Node node, String valueString, String interpreterName,
                Map<Object, Object> context, BeanFactory beanFactory) {
            try {
                Object r = cache.get(node.getKey());
                if (r == null) {
                    r = ClassUtils.getClassForName(valueString).newInstance();
                    cache.put(node.getKey(), r);
                }
                return r;
            }
            catch (Exception e) {
                throw new RuntimeException("boot error.", e);
            }
        }
    }
    /**
     * (non-Javadoc)
     * @see com.star.common.ioc.BeanFactory#getBeanIds()
     */
    public String[] getBeanIds() {
        if (configMap == null) {
            return new String[0];
        }
        Set<String> keys = configMap.keySet();
        return keys.toArray(new String[keys.size()]);
    }
    
    /**
     * (non-Javadoc)
     * @see com.star.common.ioc.BeanFactory#getBeansByPatten(String, Class)
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getBeansByPatten(String regex, Class<T> type) {
        List<T> list = new ArrayList<T>();
        if (configMap == null) {
            return list;
        }
        Pattern p = Pattern.compile(regex);

        for (String beanId : getBeanIds()) {
            Matcher m = p.matcher(beanId);
            if(m.matches()){
                Object bean = getBean(beanId);
                if (bean == null || type == null || type.isAssignableFrom(bean.getClass())) {
                    list.add((T)bean);
                }
            }
        }
        return list;
    }
    
    public <T> List<T> getBeansByType(Class<T> type) {
        List<T> list = new ArrayList<T>();
        for (String beanId : getBeanIds()) {
            T bean = getBean(beanId, type);
            list.add(bean);
        }
        return list;
    }

    public <T> T getBeanByType(Class<T> type) {
        List<T> list = getBeansByType(type);
        if (list.size() != 1) {
            throw new RuntimeException("getBeansByType(type)!=1");
        }
        return list.get(0);
    }

    public Object putCacheBean(Node node, Object bean) {
        return this.beanCacheMap.put(node, bean);
    }

    public Object removeCacheBean(Node node) {
        return this.beanCacheMap.remove(node);
    }

    public Object getCacheBean(Node node) {
        return this.beanCacheMap.get(node);
    }

    public boolean containsCacheBean(Node node) {
        return this.beanCacheMap.containsKey(node);
    }


}