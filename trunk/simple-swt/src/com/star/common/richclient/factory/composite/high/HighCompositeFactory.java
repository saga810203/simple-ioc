package com.star.common.richclient.factory.composite.high;

import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.ControlCreateListener;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.composite.CompositeFactory;

public class HighCompositeFactory extends CompositeFactory {

	private CompositeFactory compositeFactory;

	private CompositeFactory lamdar;

	private int number;

	/**
	 * �����塣
	 * 
	 * @param componentFunction
	 *            ԭ���ֺ���
	 * @param n
	 *            ��Ƕ��Ĳ��ֺ�����ÿ�δ���Ŀؼ���Ŀ
	 * @param lamdar
	 *            ҪǶ��Ĳ��ֺ���
	 */
	public HighCompositeFactory(CompositeFactory compositeFactory, int n,
			CompositeFactory lamdar) {
		this(n, lamdar);
		this.compositeFactory = compositeFactory;
	}

	/**
	 * �����塣
	 * 
	 * @param n
	 *            ��Ƕ��Ĳ��ֺ�����ÿ�δ���Ŀؼ���Ŀ
	 * @param lamdar
	 *            ҪǶ��Ĳ��ֺ���
	 */
	public HighCompositeFactory(int n, CompositeFactory lamdar) {
		this.lamdar = lamdar;
		this.number = n;
	}

	public Composite createControl(Composite parent) {
		CompositeFactory cf = getCompositeFactory();
		if (getId() != null) {
			cf.setId(getId());
		}
		if (number == 0 || lamdar == null) {
			cf.setChildren(this.getChildren());
		} else if (number < 0) {
			lamdar.setChildren(this.getChildren());
			//TODO ��Ҫ�ع�
			IControlFactory[] m = new IControlFactory[cf.getChildren().length+1];
			System.arraycopy(cf.getChildren(),0,m,0,cf.getChildren().length);
			m[m.length-1]=lamdar;
			cf.setChildren(m);
			return cf.createControl(parent);
		} else {
			for (int i = 0; i < getChildren().length; i += number) {
				CompositeFactory newlamdar = null;
				try {
					newlamdar = lamdar.getClass().newInstance();
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				if (lamdar.getId() != null) {
					newlamdar.setId(lamdar.getId() + "["
							+ cf.getChildren().length + "]");
				}
				IControlFactory[] m = new IControlFactory[number];
				System.arraycopy(getChildren(),i,m,0,number);
				newlamdar.setChildren(m);
				for(Object t: createListeners.getListeners()){
					newlamdar.addCreateListener((ControlCreateListener)t);
				}

				IControlFactory[] m2 = new IControlFactory[cf.getChildren().length+1];
				System.arraycopy(cf.getChildren(),0,m2,0,cf.getChildren().length);
				m2[m2.length-1]=newlamdar;
				cf.setChildren(m2);
				
			}
		}
		return cf.createControl(parent);
	}
	
	@Override
	protected Composite doCreateComposite(Composite parent) {
		return null;
	}

	/**
	 * �õ�ԭ���ֺ�����
	 * 
	 * @return ԭ���ֺ���
	 */
	public CompositeFactory getCompositeFactory() {
		return compositeFactory;
	}

	/**
	 * ����ԭ���ֺ�����
	 * 
	 * @param componentFunction
	 *            ԭ���ֺ���
	 */
	public void setCompositeFactory(CompositeFactory componentFunction) {
		this.compositeFactory = componentFunction;
	}

	public CompositeFactory getLamdar() {
		return lamdar;
	}

	public void setLamdar(CompositeFactory lamdar) {
		this.lamdar = lamdar;
	}


    
    public void addCreateListener(ControlCreateListener listener) {
    	super.addCreateListener(listener);
    	compositeFactory.addCreateListener(listener);
    	lamdar.addCreateListener(listener);
    }
    
    public void removeCreateListener( ControlCreateListener listener) {
    	super.removeCreateListener(listener);
    	compositeFactory.removeCreateListener(listener);
    	lamdar.removeCreateListener(listener);
    }
	
}
