package com.star.common.richclient.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.star.common.richclient.factory.ControlFactory;
import com.star.common.richclient.factory.IControlFactory;

public class GridLayoutBuilder extends ControlFactory{

	public boolean makeColumnsEqualWidth = false;

 	public int marginWidth = 5;

 	public int marginHeight = 5;

	public int marginLeft = 0;

	public int marginTop = 0;

	public int marginRight = 0;

	public int marginBottom = 0;

 	public int horizontalSpacing = 5;

 	public int verticalSpacing = 5;

 	
	private final static IControlFactory NULL = new ControlFactory() {

		protected Control doCreateControl(Composite parent) {
			return new Label(parent, SWT.NONE);
		}

	};

	private List<List<Cell>> cellRowColumn = new ArrayList<List<Cell>>();

	private List<Cell> cellCurrentRow = null;
 	
	public GridLayoutBuilder() {
		row();
	}
	
 	
	public GridLayoutBuilder(String id) {
		this();
		setId(id);
	}
	
	public GridLayoutBuilder cell() {
		return cell(null,NULL, null);
	}
	
	public GridLayoutBuilder cell(String attr) {
		return cell(null,NULL, attr);
	}
	
	public GridLayoutBuilder cell(IControlFactory cf) {
		return cell(null, cf, null);
	}
	
	public GridLayoutBuilder cell(String id, IControlFactory cf) {
		return cell(id, cf, null);
	}
	public GridLayoutBuilder cell(IControlFactory cf, String attr) {
		return cell(null, cf, attr);
	}
	public GridLayoutBuilder cell(String id, IControlFactory cf, String attr) {
		Cell c = new Cell(id, cf, attr);
		cellCurrentRow.add(c);
		return this;
	}

	public GridLayoutBuilder row() {
		cellCurrentRow = new ArrayList<Cell>();
		cellRowColumn.add(cellCurrentRow);
		return this;
	}
	
	public Composite createControl(Composite parent){
//		for(List<Cell> cellList :cellRowColumn){
//			for(Cell cell :cellList){
//				Object[] listeners = createListeners.getListeners();
//				for (int i = 0; i < listeners.length; i++) {
//					cell.cf.addCreateListener((ControlCreateListener) listeners[i]);
//				}
//			}
//		}
		
		return (Composite)super.createControl(parent);
	}
		
	protected final Composite doCreateControl(Composite parent){
		int maxNumColumns = 0;
		for(List<Cell> cellList :cellRowColumn){
			int numColumns = 0;
			for(Cell cell :cellList){
				numColumns +=(cell.data==null?1:cell.data.horizontalSpan);
			}	
			if(maxNumColumns<numColumns){
				maxNumColumns = numColumns;
			}
		}

		Composite control=new Composite(parent, SWT.NONE);
		
		GridLayout g = new GridLayout();
		g.numColumns = maxNumColumns;
		g.makeColumnsEqualWidth = makeColumnsEqualWidth;
		g.marginWidth = marginWidth;
		g.marginHeight = marginHeight;
		g.marginLeft = marginLeft;
		g.marginTop = marginTop;
		g.marginRight = marginRight;
		g.marginBottom = marginTop;
		g.horizontalSpacing =horizontalSpacing;
		g.verticalSpacing =verticalSpacing;
		control.setLayout(g);
		
		for(List<Cell> cellList :cellRowColumn){
			int numColumns = 0;
			for(Cell cell :cellList){
				fireCreateListener(true,cell.cf,null);
				Control c = cell.cf.createControl(control);
				c.setLayoutData(cell.data);			
				fireCreateListener(false,cell.cf,c);
				numColumns +=(cell.data==null?1:cell.data.horizontalSpan);
			}
			while(numColumns++<maxNumColumns){
				NULL.createControl(control);
			}
		}		
		return control;
	}
    
	// alignment£ºhint£ºgrabExcess£ºspan£ºminimum£ºindent
	// f -1 true 1 0 0
	// b false
	// c
	// e
	//
	// horizontalAlignment verticalAlignment
	// f: FILL
	// b: BEGINNING
	// c: CENTER
	// e: END
	//
	// widthHint heightHint
	// -1 (SWT.DEFAULT)
	//
	// grabExcessHorizontalSpace grabExcessVerticalSpace
	//
	// horizontalSpan verticalSpan
	//
	// minimumWidth minimumHeight
	//
	// horizontalIndent verticalIndent

	public static GridData interp(String hSpec, String vSpec) {
		GridData data = new GridData();
		interp(data,true,hSpec);
		interp(data,false,vSpec);
		return data;
	}
	
	private static void interp(GridData d, boolean horizontal, String spec) {
		if(spec==null || spec.length()==0){
			return;
		}
		String[] ss = spec.split(":");
		
		int alignment = horizontal?SWT.BEGINNING:SWT.CENTER;
		int hint = SWT.DEFAULT;
		boolean grabExcess = false;
		int span = 1;
		int minimum = 0 ;
		int indent = 0;
		
		if (ss.length > 0) {
			switch (ss[0].charAt(0)) {
			case 'f':
				alignment = SWT.FILL;break;
			case 'b':
				alignment = SWT.BEGINNING;break;
			case 'c':
				alignment = SWT.CENTER;break;
			case 'e':
				alignment = SWT.END;break;
			case 'l':
				alignment = SWT.LEFT;break;
			case 'r':
				alignment = SWT.RIGHT;break;
			}
		}
		if (ss.length > 1 && ss[1].charAt(0)!='p') {			
			hint = Integer.parseInt(ss[1]);
		}
		if (ss.length > 2 && (ss[2].charAt(0)=='t' || ss[2].charAt(0)=='g') ) {
			grabExcess = true;
		}
		if (ss.length > 3) {			
			span = Integer.parseInt(ss[3]);
		}
		if (ss.length > 4) {			
			minimum = Integer.parseInt(ss[4]);
		}
		if (ss.length > 5) {			
			indent = Integer.parseInt(ss[5]);
		}
		if(horizontal){
			d.horizontalAlignment = alignment;
			d.widthHint= hint;
			d.grabExcessHorizontalSpace = grabExcess;
			d.horizontalSpan = span;
			d.minimumWidth = minimum;
			d.horizontalIndent = indent;
		}
		else{
			d.verticalAlignment = alignment;
			d.heightHint= hint;
			d.grabExcessVerticalSpace = grabExcess;
			d.verticalSpan = span;
			d.minimumHeight = minimum;
			d.verticalIndent = indent;
		}
	}
	
	class Cell {
		Cell(String id, IControlFactory cf, String attr) {
			this.cf = cf;
			cf.setId(id);
			if (attr != null && attr.length() > 0) {
				this.data = new GridData();
				String[] specs = attr.split("\\s+");
				for (int i = 0; i < specs.length; i++) {
					boolean horizontal = specs[i].charAt(0) == 'h';
					String spec = specs[i].substring(2);
					interp(data, horizontal, spec);
				}
			}

		}

		IControlFactory cf;

		GridData data;
	}

}
