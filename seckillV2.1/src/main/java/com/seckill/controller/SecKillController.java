package com.seckill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.common.vo.SysResult;
import com.seckill.pojo.Seckill;
import com.seckill.service.SecKillService;

@Controller
public class SecKillController {
	
	@Autowired
	private SecKillService secKillService;
	
	@RequestMapping("/seckill")
	public String findSeckillList(Model modle){
		List<Seckill> list = secKillService.findSeckillList();
		modle.addAttribute("list", list);
		return "list";
	}

	/**
	 * 查询秒杀活动特价商品的信息
	 */
	@RequestMapping(value="/query/{productId}",produces="text/html;charset=utf-8")
	@ResponseBody
	public String query(@PathVariable String productId) throws Exception {
		return secKillService.querySecKillProductInfo(productId);
	}
	
	/**
	 * 执行秒杀
	 */	
	@RequestMapping(value="/seckill/{productId}",produces="text/html;charset=utf-8")
	public String skill(@PathVariable String productId,Model model){
		SysResult sysResult = secKillService.seckill(productId);
		if(sysResult!=null){
			switch (sysResult.getStatus()) {
			case 0:
				secKillService.updateSuccessKill(productId);
				secKillService.updateSecKillByProductId(productId);
				model.addAttribute("productId", productId);
				return "gameover";
			case 1:
				return "success";
			}
		}
		return "again";	
	}
}
