package com.star.common.richclient.layout;

import junit.framework.TestCase;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import com.star.common.richclient.Application;
import com.star.common.richclient.factory.ControlCollector;
import com.star.common.richclient.factory.control.FButton;
import com.star.common.richclient.factory.control.FLabel;
import com.star.common.richclient.factory.control.FText;
import com.star.common.richclient.factory.control.combo.FCombo;

public class GridLayoutBuilderTest extends TestCase {

	public static void main(String[] args) {
		ControlCollector collector = new ControlCollector();
		
		final GridLayoutBuilder b = new GridLayoutBuilder();
		b.addCreateListener(collector);
		
		b.cell(new FLabel("Name:"));
		b.cell("name",new FText(),"h=f:p:t");
		b.cell(new FLabel("Password:"));
		b.cell("password",new FText(SWT.PASSWORD|SWT.BORDER),"h=f:p:t");
		b.row();
		b.cell(new FLabel("Sex:"));
		b.cell("sex",new FCombo(),"h=f");
		b.row();
		b.cell().cell().cell();
		b.cell(new FButton("Commit"),"h=r:p:n");
		
		Application app = new Application();
		app.setContentsFactory(b);
		app.create();
		((Text)collector.get("name")).setText("bcd");
		ComboViewer v =(ComboViewer)((Combo)collector.get("sex")).getData();
		v.add("ÄÐ");
		v.add("Å®");
		app.open();
		
	}

}
