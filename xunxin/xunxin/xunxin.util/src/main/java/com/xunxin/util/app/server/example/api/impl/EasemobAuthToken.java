package com.xunxin.util.app.server.example.api.impl;

import com.xunxin.util.app.server.example.api.AuthTokenAPI;
import com.xunxin.util.app.server.example.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI{

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
