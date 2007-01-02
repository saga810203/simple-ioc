package com.star.sms.richclient.view;

import java.util.Map;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;

import com.star.common.richclient.component.ControlMapAware;
import com.star.common.richclient.component.Form;
import com.star.common.richclient.component.Listener;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.utils.table.DefaultTableHandler;
import com.star.common.richclient.utils.table.TableHandler;
import com.star.common.util.Node;
import com.star.common.util.TreeMap;
import com.star.sms.richclient.Launch;

public class MyViewHandler2 implements ControlMapAware {

	private Tree treeA;
	
	public Table tableA;

	private Map<String, Control> controlMap;

	private Form aform1;
	
	private Form aform2;
	
	public void setControlMap(Map<String, Control> controlMap) {
		this.controlMap = controlMap;
		aform1 = new Form();
		aform1.setControlMap(controlMap);
		aform1.setFormId("form1");
		aform2 = new Form();
		aform2.setControlMap(controlMap);
		aform2.setFormId("form2");
	}

	@Listener(value = "addSelectionListener", paramType = SelectionListener.class)
	public void tableA_widgetSelected(SelectionEvent e) {
		if (tableA != null)
			System.out.println(e);
	}

	@Listener(value = "addSelectionChangedListener", paramType = ISelectionChangedListener.class)
	public void tableA_selectionChanged(SelectionChangedEvent event) {
//		System.out.println(event);
//		Tree treeA = (Tree) controlMap.get("treeA");
		TreeViewer viewer = (TreeViewer) treeA.getData("viewer");
		viewer.setInput(((StructuredSelection) event.getSelection()).toArray());

	}
	
	@Listener(value = "addSelectionListener", paramType = SelectionListener.class)
	public void add_widgetSelected(SelectionEvent e) {
		User user=new User();
		aform1.setValueToObject(user);
		System.out.println(user.name);
		
		aform2.setValueToControl(user);
	}

	public class User{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	// public void treeA_preCreate(IControlFactory factory) {
	//
	// FTableTree v = (FTableTree) factory;
	//		
	// FTableColumn c = new FTableColumn();
	// c.setPropertyName("key");
	// c.setColumnName("Key");
	// v.addColumn(c);
	//		
	// c = new FTableColumn();
	// c.setPropertyName("value");
	// c.setColumnName("Value");
	// v.addColumn(c);
	//
	// }

	public void tableA_postCreate(IControlFactory factory, Table control) {

		TableViewer v = (TableViewer) ((Table) control).getData("viewer");

		TreeMap treeMap = new TreeMap();
		treeMap.load(Launch.class, "bean-config-app.properties");

		TableHandler<Node> handler = DefaultTableHandler.getTableHandler(v);

		for (Node n : treeMap.values()) {
			add(handler, n);
		}
	}

	private void add(TableHandler<Node> handler, Node node) {
		handler.add(node);
		for (Node n : node.getChildren()) {
			add(handler, n);
		}
	}

	// public void postCreate_treeA(Tree control){
	// Node n1 = new Node();
	// n1.setKey("n1");
	//
	// Node n11 = new Node();
	// n11.setKey("n11");
	//
	// Node n12 = new Node();
	// n12.setKey("n12");
	//
	// Node n111 = new Node();
	// n111.setKey("n111");
	//
	// Node n112 = new Node();
	// n112.setKey("n112");
	//
	// n1.addChild(n11);
	// n1.addChild(n12);
	// n11.addChild(n111);
	// n11.addChild(n112);
	//
	// TreeViewer v = (TreeViewer) ((Tree) control).getData("viewer");
	// v.setInput(new Object[] { n1 });
	// }
}
