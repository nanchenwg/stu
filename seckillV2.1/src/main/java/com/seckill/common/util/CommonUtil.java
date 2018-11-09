package com.seckill.common.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seckill.common.redisLock.RedissonLockUtil;
import com.seckill.pojo.Seckill;

@Service
public class CommonUtil<T> {

	@Autowired
	private RedissonLockUtil redissonLockUtil;

	@Autowired
	private static ObjectMapper objectMapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public List<T> getListFromRedis(String key, Class<T> t){
		try {
			String jsonResult = (String) redissonLockUtil.get(key);
			if(!StringUtils.isEmpty(jsonResult)){
				T[] object = (T[]) objectMapper.readValue(jsonResult, t);
				return Arrays.asList(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}
