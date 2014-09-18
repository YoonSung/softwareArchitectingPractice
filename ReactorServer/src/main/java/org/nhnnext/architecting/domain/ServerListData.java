package org.nhnnext.architecting.domain;

import java.util.List;

import org.simpleframework.xml.ElementList;

/**
 * Object와 XML데이터와 맵핑시키기 위한 Domain함수
 * @see src/main/resources/HandlerList.xml
 * @author Yoonsung
 * @date 2014-09-17
 */
public class ServerListData {
	@ElementList(entry="server", inline=true)
	private List<HandlerListData> server;
	
	public List<HandlerListData> getServer() {
		return server;
	}
}
