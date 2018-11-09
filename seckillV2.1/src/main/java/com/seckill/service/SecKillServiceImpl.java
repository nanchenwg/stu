package com.seckill.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seckill.common.annotation.DistributedLock;
import com.seckill.common.redisLock.RedissonLockUtil;
import com.seckill.common.util.CommonUtil;
import com.seckill.common.vo.SysResult;
import com.seckill.mapper.SeckillMapper;
import com.seckill.mapper.SuccessKillMapper;
import com.seckill.pojo.Seckill;
import com.seckill.pojo.SuccessKill;

@Service
public class SecKillServiceImpl implements SecKillService {

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private SeckillMapper seckillMapper;
	
	@Autowired
	private SuccessKillMapper successKillMapper;
	
	@Autowired
	private RedissonLockUtil redissonLockUtil;
	
	@Autowired
	private static ObjectMapper objectMapper = new ObjectMapper();

	static Map<String, Integer> products = new HashMap<>();

	@Override
	public List<Seckill> findSeckillList() {
		String key = "seckill_productList";
		List<Seckill> list = null;
		String jsonResult = (String) redissonLockUtil.get(key);
		try {
			if(StringUtils.isEmpty(jsonResult)){
				//如果第一次查询,则先查询数据库数据,
				list = seckillMapper.findItemList();
				String json = objectMapper.writeValueAsString(list);
				redissonLockUtil.set(key, json);
				// 保留商品原有数量
				for (Seckill kill : list) {
					products.put(kill.getSeckillId(), kill.getNumber());
				}
				System.out.println("第一次从数据库查询商品列表信息");
			}else{
				//再次查询时,则通过缓存获取数据
				list = commonUtil.getListFromRedis(key, Seckill[].class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;		
	}
	

	@Override
	public void updateSuccessKill(String productId) {
		try {
			String skKey = productId+"seckill_successList";
			List<SuccessKill> skList = commonUtil.getListFromRedis(skKey, SuccessKill[].class);
			if(skList!=null){
				successKillMapper.updateSuccessKillItems(skList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateSecKillByProductId(String productId) {
		try {
			String pkey = "seckill_productList";
			List<Seckill> list = commonUtil.getListFromRedis(pkey, Seckill[].class);
			int stockNum = 0;				
			for (Seckill kill : list) {
				if(productId.equals(kill.getSeckillId())){
					stockNum = kill.getNumber();
				}					
			}
			seckillMapper.updateSeckillNumberById(productId, stockNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@DistributedLock(argNum=1,tryLock=true,waitTime=2L,leaseTime=10L)
	@Override
	public SysResult seckill(String productId) {
		try {
			String pkey = "seckill_productList";
			List<Seckill> list = commonUtil.getListFromRedis(pkey, Seckill[].class);
			int stockNum = 0;			
			Seckill seckill = null;	
			for (Seckill kill : list) {
				if(productId.equals(kill.getSeckillId())){
					//查询缓存获取商品数量
					stockNum = kill.getNumber();
					//获取秒杀商品
					seckill = kill;
				}					
			}
			if (stockNum == 0) {
				return SysResult.build(0, "活动已经结束,请留意下次活动");	
			}
			
			//存入成功订单到redis缓存
			String userId = UUID.randomUUID().toString();
			SuccessKill sk  = new SuccessKill(productId, userId, 1, new Date());
			String skKey = productId+"seckill_successList";
			List<SuccessKill> skList = commonUtil.getListFromRedis(skKey, SuccessKill[].class);
			if(skList ==null)
				skList = new ArrayList<>();
			List<SuccessKill> skArrList = new ArrayList<>(skList);
			skArrList.add(sk);
			redissonLockUtil.set(skKey, objectMapper.writeValueAsString(skArrList));
			
			//更新库存到redis缓存
			stockNum = stockNum - 1;
			seckill.setNumber(stockNum);			
			redissonLockUtil.set(pkey, objectMapper.writeValueAsString(list));
			
			System.out.println("第"+skArrList.size()+"个"+"用户"+userId+"抢购成功，商品库存剩余："+stockNum);
			
			return SysResult.build(1, userId+"抢购成功");
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(2, "没抢到，换个姿势再来一遍");
		}
	}

	
	@Override
	public String querySecKillProductInfo(String productId) {
		return queryMap(productId);
	}
	
	private String queryMap(String productId) {
		int productNum=0;
		int stockNum =0;
		int orderNum =0;
		String productName = null;
		try {
			String pkey = "seckill_productList";		
			List<Seckill> list = commonUtil.getListFromRedis(pkey,Seckill[].class);
			for (Seckill kill : list) {
				if(productId.equals(kill.getSeckillId())){
					stockNum = kill.getNumber();
					productName = kill.getName();
				}					
			}
			
			String skKey = productId+"seckill_successList";
			List<SuccessKill> skList = commonUtil.getListFromRedis(skKey, SuccessKill[].class);
			if(skList!=null)
				orderNum = skList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  productName+"特价，限量份" + products.get(productId) + " 还剩：" + stockNum + " 份" + " 该商品成功下单用户数目："
				+ orderNum + " 人";
	}
}
