package com.seckill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seckill.pojo.SuccessKill;

public interface SuccessKillMapper {
	
	void updateSuccessKillItems(@Param("orders")List<SuccessKill> orders);

}
