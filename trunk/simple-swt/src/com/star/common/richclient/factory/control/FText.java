package com.star.common.richclient.factory.control;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

	public class FText extends AbstractFControl {
		
		public FText() {
			this(null);
		}

		public FText(int style) {
			this(style,null);
		}

		public FText(String text) {
			this(SWT.BORDER,text);
		}
		
		public FText(int style, String text) {
			super(style);
			setText(text);
		}

		public Text doCreateControl(Composite parent) {
			Text c = new Text(parent, getStyle());
			if (getText() != null) {
				c.setText(getText());
			}
			if (getToolTipText() != null) {
				c.setToolTipText(getToolTipText());
			}
			return c;
		}
	}
