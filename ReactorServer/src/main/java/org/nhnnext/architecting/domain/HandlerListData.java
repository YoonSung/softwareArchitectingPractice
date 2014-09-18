package org.nhnnext.architecting.domain;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

/**
 * Object와 XML데이터와 맵핑시키기 위한 Domain함수
 * @see src/main/resources/HandlerList.xml
 * @author Yoonsung
 * @date 2014-09-17
 */
public class HandlerListData {
	
	@ElementList(entry="handler", inline=true)
	private List<String> handler;
	
	@Attribute
	private String name;
	
	public List<String> getHandler() {
		return handler;
	}
	
	public String getName() {
		return name;
	}
}
