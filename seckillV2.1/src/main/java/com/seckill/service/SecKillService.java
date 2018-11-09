package com.seckill.service;

import java.util.List;

import com.seckill.common.vo.SysResult;
import com.seckill.pojo.Seckill;
import com.seckill.pojo.SuccessKill;

public interface SecKillService {
	
	List<Seckill> findSeckillList();
	
	void updateSuccessKill(String productId);
	
	void updateSecKillByProductId(String productId);
	
	SysResult seckill(String productId);
	
	/*SysResult seckillHandle(String productId);*/
	
	String querySecKillProductInfo(String productId);
}
