package com.seckill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.seckill.pojo.Seckill;

public interface SeckillMapper{
	@Select("select * from seckill")
	List<Seckill> findItemList();
	
	@Update("update seckill set number = #{stockNum} where seckill_id=#{productId}")
	void updateSeckillNumberById(@Param("productId") String productId,
						  @Param("stockNum") Integer stockNum);
	
}
