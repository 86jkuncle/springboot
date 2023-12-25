package org.lybaobei.service;

import java.util.List;
import org.lybaobei.entity.UserOnline;


public interface SessionService {
	
	List<UserOnline> list();
	boolean forceLogout(String sessionId);
}
