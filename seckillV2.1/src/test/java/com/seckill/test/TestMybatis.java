package com.seckill.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.seckill.mapper.SeckillMapper;
import com.seckill.pojo.Seckill;

public class TestMybatis {
    @Test
    public void hello(){

         ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("spring/applicationContext-mybatis.xml");
         SqlSessionFactory factory = (SqlSessionFactory) cxt.getBean("sqlSessionFactoryBean");
         SqlSession session = factory.openSession();
         SeckillMapper mapper = session.getMapper(SeckillMapper.class);
         List<Seckill> list = mapper.findItemList();
         System.out.println(list);
         session.close();
    }
}
