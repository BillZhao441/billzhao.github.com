package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatusEnum;


public class SeckillExcution {

    private long seckillId;

    private int status;

    private String statusInfo;

    private SuccessKilled successKilled;

    public SeckillExcution(long seckillId, SeckillStatusEnum statusEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.status = statusEnum.getStatus();
        this.statusInfo = statusEnum.getStatusInfo();
        this.successKilled = successKilled;
    }

    public SeckillExcution(long seckillId, SeckillStatusEnum statusEnum) {
        this.seckillId = seckillId;
        this.status = statusEnum.getStatus();
        this.statusInfo = statusEnum.getStatusInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExcution{" +
                "seckillId=" + seckillId +
                ", status=" + status +
                ", statusInfo='" + statusInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
