package org.seckill.service.impl;

import org.seckill.dao.Seckilldao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatusEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

//@Componnent @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Seckilldao seckilldao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private final String slat="nct843qntrx87y98rem32u9exu3209rexm932uex98mryyr784%^RGujjsj";

    @Override
    public List<Seckill> getSeckillList() {
        return seckilldao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckilldao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill=redisDao.getSeckill(seckillId);
        if(seckill==null){
            seckill=seckilldao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(false, seckillId);
            }else{
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5=getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        if(md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data has been rewritten");
        }
        Date nowTime=new Date();
        try {
            int insertCount=successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if(insertCount<=0){
                throw new RepeatKillException("repeat seckilling");
            }else{
                int updateCount=seckilldao.reduceNumber(seckillId, nowTime);
                if(updateCount<=0){
                    throw new SeckillCloseException("seckilling is over");
                }else{
                    SuccessKilled successKilled=successKilledDao.queryByIdwithSeckill(seckillId, userPhone);
                    return new SeckillExcution(seckillId, SeckillStatusEnum.SUCCEED, successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }
}
