package com.xunxin.config;

import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月19日 -- 上午10:15:35
 * @Version 1.0
 * @Description		注入RedisTemplate配置
 */
@Component
public class MyjedisPoolConfig extends JedisPoolConfig {

	private int maxIdle=300;
    private int maxTotal=600;
    private boolean testOnBorrow=true;

	public MyjedisPoolConfig() {
	}

	public MyjedisPoolConfig(int maxIdle, int maxTotal, boolean testOnBorrow) {
		this.maxIdle = maxIdle;
		this.maxTotal = maxTotal;
		this.testOnBorrow = testOnBorrow;
	}

	@Override
	public int getMaxIdle() {
		return maxIdle;
	}

	@Override
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	@Override
	public int getMaxTotal() {
		return maxTotal;
	}

	@Override
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	@Override
	public boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
}