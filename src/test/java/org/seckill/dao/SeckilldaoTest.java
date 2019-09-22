package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckilldaoTest {

    @Resource
    private Seckilldao seckilldao;

    @Test
    public void queryById() {
        long id=1000;
        Seckill seckill=seckilldao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills=seckilldao.queryAll(0,100);
        for(Seckill s: seckills){
            System.out.println(s);
        }
    }

    @Test
    public void reduceNumber() {
        Date killTime=new Date();
        int updateCount=seckilldao.reduceNumber(1000L, killTime);
        System.out.println("updateCount:"+updateCount);
    }

}