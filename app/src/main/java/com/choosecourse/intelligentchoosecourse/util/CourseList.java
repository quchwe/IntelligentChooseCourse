package com.choosecourse.intelligentchoosecourse.util;

/**
 * Created by quchwe on 2015/11/30.
 */
public class CourseList {
	public String name;
	public String courseother;
	//public CourseList(String cname,String id){
//
		//this.name=cname;
		//this.coursecn=id;
	//

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
