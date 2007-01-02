package com.star.common.richclient.factory.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.factory.ControlCreateListener;
import com.star.common.richclient.factory.ControlFactory;
import com.star.common.richclient.factory.IControlFactory;
/**
 * ռλ���ؼ�������
 * @author liuwei
 * @version 1.0
 */
public abstract class CompositeFactory extends ControlFactory{

	/**
	 * �ӿؼ������֣�������
	 * ���Ǵ����Ŀؼ��Ǹÿؼ������֣����������ؼ����ӿؼ���
	 */
	private IControlFactory[] children = new IControlFactory[0];
	
	public CompositeFactory(){
	}
	
	public CompositeFactory(String id){
		super(id);
	}
	
	public CompositeFactory(String id, IControlFactory... children){
    	this(id);
    	this.children=children;
	}

	public Composite createControl(Composite parent){
		return (Composite)super.createControl(parent);
	}
	
	/**
	 * ������ǰ�ؼ������֣������Ŀؼ���
	 * @param parent
	 * @return
	 */
	protected final Composite doCreateControl(Composite parent){
		Composite control = doCreateComposite(parent);
		if(control!=null)
		createChildren(control);
		return control;
	}
	
	protected abstract Composite doCreateComposite(Composite parent);
	
	protected void createChildren(Composite control) {
		IControlFactory[] children = getChildren();
		Object[] layoutDatas = createLayoutDatas(children.length);
		for(int i=0;i<children.length;i++){
			Control c = createChild(control, i);
			if(c!=null && layoutDatas!=null && layoutDatas[i]!=null){
				c.setLayoutData(layoutDatas[i]);
			}
		}
	}

	/**
	 * �����ӿؼ��Ĳ������ݡ�
	 * @param n �ӿؼ���Ŀ
	 * @return ������������
	 */
	protected Object[] createLayoutDatas(int n) {
		return null;
	}

	protected Control createChild(Composite control, int i) {
		Control c =getChildren()[i].createControl(control);
		return c;
	}
	
	public CompositeFactory clone(){
		CompositeFactory c= (CompositeFactory)super.clone();
		c.children = new IControlFactory[this.children.length];
		for (int i = 0; i < this.children.length; i++) {
			c.children[i] = this.children[i].clone();
		}
		return c;
	}
	//============ GET/SET ==============
   
    public void addCreateListener(ControlCreateListener listener) {
    	super.addCreateListener(listener);
		for(IControlFactory c : getChildren()){		
			c.addCreateListener(listener);
		}	
    }
    
    public void removeCreateListener( ControlCreateListener listener) {
    	super.removeCreateListener(listener);
		for(IControlFactory c : getChildren()){
			c.removeCreateListener(listener);
		}	
    }
	
	public void removeAllCreateListener() {
		super.removeAllCreateListener();
		for(IControlFactory c : getChildren()){
			c.removeAllCreateListener();
		}
	}

	public IControlFactory[] getChildren() {
		return children;
	}

	public void setChildren(IControlFactory[] children) {
		this.children = children;
	}

}
