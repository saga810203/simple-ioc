package com.star.common.richclient.layout;

import junit.framework.TestCase;

import net.ffxml.swtforms.factories.Borders;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.star.common.richclient.Application;
import com.star.common.richclient.factory.ControlFactory;

public class TableLayoutBuilderTest extends TestCase {

	public static void main(String[] args) {
		
		Application app = new Application();
		app.setContentsFactory(new ControlFactory(){

			@Override
			protected Control doCreateControl(Composite parent) {
				Composite c = new Composite(parent,SWT.NONE);
				TableLayoutBuilder b = new TableLayoutBuilder(c);
				b.separator("БъЬт");
				b.row();
				b.cell(createLabel("Name:",parent),TableLayoutBuilder.DEFAULT_LABEL_ATTRIBUTES);
				b.gapCol();
				b.cell(new Text(parent,SWT.BORDER));
				b.row();
				b.cell(createLabel("Descript:",parent));

				b.cell(new Text(parent,SWT.MULTI|SWT.BORDER));
				b.setBorder(Borders.DLU7_BORDER);
				return b.getPanel();
			}

			private Label createLabel(String text,Composite parent) {
				Label l = new Label(parent,SWT.NONE);
				l.setText(text);
				return l;
			}
			
		});
		
		app.create();
		app.open();
	}

}
