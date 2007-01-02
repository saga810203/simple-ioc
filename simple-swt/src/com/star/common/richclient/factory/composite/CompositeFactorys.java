package com.star.common.richclient.factory.composite;

import com.star.common.richclient.factory.ControlFactory;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.composite.support.BorderLayoutFactory;
import com.star.common.richclient.factory.composite.support.FillLayoutFactory;
import com.star.common.richclient.factory.composite.support.RowLayoutFactory;
import com.star.common.richclient.factory.composite.support.SashFormFactory;
import com.star.common.richclient.factory.composite.support.ScrolledCompositeFactory;
import com.star.common.richclient.factory.composite.support.SeparatorCompositeFactory;
import com.star.common.richclient.factory.composite.support.StackLayoutFactory;
import com.star.common.richclient.factory.composite.support.TabFolderFactory;
import com.star.common.richclient.factory.control.FNull;

/**
 * CompositeFunction工厂.
 * 
 * @author liuwei
 * @version 1.0
 */
public class CompositeFactorys {

	protected CompositeFactorys() {
	}

	public static CompositeFactory border(String id, String parameters,
			IControlFactory... children) {
		return new BorderLayoutFactory(id, parameters, children);
	}

	public static CompositeFactory border(String id,
			IControlFactory... children) {
		return border(id, null, children);
	}

	public static CompositeFactory fill(String id, String parameters,
			IControlFactory... children) {
		return new FillLayoutFactory(id, parameters, children);
	}

	public static CompositeFactory fill(String id, IControlFactory... children) {
		return fill(id, null, children);
	}

	public static CompositeFactory row(String id, String parameters,
			IControlFactory... children) {
		return new RowLayoutFactory(id, parameters, children);
	}

	public static CompositeFactory row(String id, IControlFactory... children) {
		return row(id, null, children);
	}

	public static CompositeFactory sash(String id, String parameters,
			IControlFactory... children) {
		return new SashFormFactory(id, parameters, children);
	}

	public static CompositeFactory sash(String id, IControlFactory... children) {
		return sash(id, null, children);
	}

	public static CompositeFactory scoll(String id, String parameters,
			IControlFactory... children) {
		return new ScrolledCompositeFactory(id, parameters, children);
	}

	public static CompositeFactory scoll(String id, IControlFactory... children) {
		return scoll(id, null, children);
	}

	public static CompositeFactory separator(String id, String parameters,
			IControlFactory... children) {
		return new SeparatorCompositeFactory(id, parameters, children);
	}

	public static CompositeFactory separator(String id,
			IControlFactory... children) {
		return separator(id, null, children);
	}

	public static CompositeFactory stack(String id, String parameters,
			IControlFactory... children) {
		return new StackLayoutFactory(id, parameters, children);
	}

	public static CompositeFactory stack(String id, IControlFactory... children) {
		return stack(id, null, children);
	}

	public static CompositeFactory tab(String id, String parameters,
			IControlFactory... children) {
		return new TabFolderFactory(id, parameters, children);
	}

	public static CompositeFactory tab(String id, IControlFactory... children) {
		return tab(id, null, children);
	}

	/**
	 * 创建占位符。
	 * 
	 * @param id
	 *            占位符Id
	 * @return 占位符
	 */
	public static ControlFactory ph(String id) {
		return new FNull(id);
	}

	/**
	 * 替换占位符。遍历根ControlFactory，按照Id替换相同的对象。
	 * 
	 * @param root
	 *            根IControlFactory
	 * @param newControlFactorys
	 *            新的ControlFactory数组
	 */
	public static void replacePlaceholder(IControlFactory root,
			IControlFactory... newControlFactorys) {
		if (root instanceof CompositeFactory) {
			IControlFactory[] children = ((CompositeFactory) root)
					.getChildren();
			for (int i = 0; i < children.length; i++) {
				IControlFactory c = children[i];
				for (IControlFactory t : newControlFactorys) {
					if (c.getId() != null && c.getId().equals(t.getId())) {
						children[i] = t;
					}
				}
				replacePlaceholder(c, newControlFactorys);
			}
		}
	}

	public static void replacePlaceholder(IControlFactory root, String id,
			IControlFactory newControlFactory) {
		if (root instanceof CompositeFactory) {
			IControlFactory[] children = ((CompositeFactory) root)
					.getChildren();
			for (int i = 0; i < children.length; i++) {
				IControlFactory c = children[i];
				if (id.equals(c.getId())) {
					children[i] = newControlFactory;
				} else {
					replacePlaceholder(c, id, newControlFactory);
				}
			}
		}
	}

	public static void setAttribute(IControlFactory root,
			String controlFactoryId, String attributeKey, Object attribute) {
		if (root instanceof CompositeFactory) {
			IControlFactory[] children = ((CompositeFactory) root)
					.getChildren();
			for (int i = 0; i < children.length; i++) {
				IControlFactory c = children[i];
				if (controlFactoryId.equals(c.getId())) {
					c.setAttribute(attributeKey, attribute);
					break;
				} else {
					setAttribute(c, controlFactoryId, attributeKey, attribute);
				}
			}
		}
	}

}
