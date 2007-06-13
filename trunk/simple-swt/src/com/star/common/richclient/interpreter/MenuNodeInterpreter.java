package com.star.common.richclient.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.ioc.support.interpreter.AbstractNodeInterpreter;
import com.star.common.util.Node;
/**
 * �˵��ڵ��������
 * ֧��������ʽ�˵��ڵ��ע�룺
 * <pre>
menuBar							:	(menu)
	-file 		
		--a
	
#��һ����ʽ��ע�룬ע�뵽menuBar��	
menuBar2						:	(menu)
	-file
		--b
	-help
	
#�ڶ�����ʽ��ע�룬ע�뵽menuBar�ķ�file�ڵ��£����������file�򴴽���	
menuBar.file					:	(menu)
	-c
  </pre>
 * @author myace
 *
 */
public class MenuNodeInterpreter extends AbstractNodeInterpreter {
	//��ֹǶ�׵Ĳ˵����ء�
	private boolean extendsSearchLock = true; // XXX �̲߳���ȫ��
	
	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		node = node.clone();
		setBeanFactory(node ,beanFactory);
		
		if(extendsSearchLock){
			extendsSearchLock = false;
			try {
				String patten = node.getKey().replace(".", "\\.")+".+";//FIXME �������������ʽ�е��ַ���
				String patten2 = node.getKey().replace(".", "\\.")+".*";//FIXME �������������ʽ�е��ַ���
				List<Node> extendList = new ArrayList<Node>();
				addExtends(patten,patten2, extendList, beanFactory);
				
				for (Object o : extendList) {
					if(o instanceof Node){
						Node sub = (Node)o;
						injectSub(node,sub);
					}
				}
			} finally {
				extendsSearchLock=true;
			}
		}
		
		return node;
	}
	
	private void setBeanFactory(Node node, BeanFactory beanFactory) {
		node.setObject(beanFactory);
		for(Node n:node.getChildren()){
			setBeanFactory(n, beanFactory);
		}
	}

	private void addExtends(String patten,String patten2, List<Node> extendList, BeanFactory bf) {		
		extendList.addAll(bf.getBeansByPatten(patten, Node.class));
		if (bf instanceof DefaultBeanFactory) {
			List<BeanFactory> bfs = ((DefaultBeanFactory) bf)
					.getChildren();
			for (BeanFactory c : bfs) {
				addExtends(patten2, patten2, extendList, c);
			}
		}
	}
	
	private void injectSub(Node parent, Node sub) {
		int index = sub.getKey().indexOf('.',parent.getKey().length());
		if(index>=0){
			String kstr = sub.getKey().substring(index+1);
			String[] ks = kstr.split("\\.");
			for (String k : ks) {
				if(parent.findChildByKey(k)==null){
					Node c = new Node();
					c.setKey(k);
					parent.addChild(c);
					parent = c;
				}else{
					parent = parent.findChildByKey(k);
				}
			}
		}
		addTo(parent, sub.getChildren());
	}

	private void addTo(Node parent, List<Node> children) {
		for (Node c : children) {
			if(parent.findChildByKey(c.getKey())==null){
				parent.addChild(c);
			}else{
				addTo(parent.findChildByKey(c.getKey()),c.getChildren());
			}
		}
	}

}
