package com.star.common.richclient.layout;

import com.star.common.richclient.factory.IControlFactory;

public class GridLayoutTemplate {
	// -data: h=l:p:n | h=f:p:g || h=l:p:n | h=f:p:g
	// -swap: true

	private String[] datas;

	private int n;
	
	private boolean swap = true;
	
	private int i = 0;
	
	private boolean firstRow = true;
	
	public GridLayoutTemplate(String[] datas,boolean swap){
		this.swap=swap;
		this.datas=datas;
		n = datas.length;
	}
	
	public void add(GridLayoutBuilder builder, IControlFactory cf) {
		while (true) {
			if(swap && !firstRow && i==0){
				builder.row();
			}
			
			String data = datas[i];
			i = (i + 1) % n;
			if ("|".equals(data)) {
				// TODO 加入空隙
			} else if ("||".equals(data)) {
				// 加入空隙
			} else if("|||".equals(data)){
				builder.row();
			} 
			else {
				builder.cell(cf.getId(),cf, data);
				break;
			}
		}
		firstRow = false;
	}
	
	
}
