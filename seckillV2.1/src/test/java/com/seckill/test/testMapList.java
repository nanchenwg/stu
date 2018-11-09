package com.seckill.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.seckill.pojo.Seckill;

public class testMapList {

	@Test
	public void test(){
		List<Seckill> list = new ArrayList<>();
		Seckill s1 = new Seckill();
		s1.setSeckillId("1000");
		s1.setName("apple");
		s1.setNumber(100);
		
		Seckill s2 = new Seckill();
		s2.setSeckillId("1001");
		s2.setName("xiaomi");
		s2.setNumber(99);
		
		list.add(s1);
		list.add(s2);
		
		Map<String, Seckill> map = list.stream().collect(Collectors.toMap(Seckill::getSeckillId, seckill -> seckill,(k1,k2)->k1));
		System.out.println(map);
		Seckill seckill = map.get("1000");
		System.out.println(seckill);
		System.out.println("库存;"+seckill.getNumber());
		
	}
	
	@Test
	public void testList(){
		List<Seckill> list = new ArrayList<>();
		Seckill s1 = new Seckill();
		s1.setSeckillId("1000");
		s1.setName("apple");
		s1.setNumber(100);
		
		Seckill s2 = new Seckill();
		s2.setSeckillId("1001");
		s2.setName("xiaomi");
		s2.setNumber(99);
		
		list.add(s1);
		list.add(s2);
		
		Seckill sss1 = list.get(0);
		System.out.println(sss1);
		sss1.setNumber(22);
		System.out.println("-----------------------------");
		System.out.println(list);
	}
}
