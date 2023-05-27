package com.demo.springApp.dto;

import java.util.ArrayList;

public class Comments {
	
	ArrayList list = (ArrayList)new ArrayList<PostComments>();

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

}
