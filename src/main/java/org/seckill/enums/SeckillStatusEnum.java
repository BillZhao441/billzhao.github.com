package org.seckill.enums;

public enum SeckillStatusEnum {
    SUCCEED(1,"seckill success"),
    END(0,"seckilling is over"),
    REPEAT_SECKILL(-1,"repeat seckilling"),
    INNER_ERROR(-2,"system error"),
    DATA_REWRITE(-3,"data has been rewritten");

    private int status;

    private String statusInfo;

    SeckillStatusEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
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

    public static SeckillStatusEnum stateOf(int index){
        for(SeckillStatusEnum status: values()){
            if(index==status.status){
                return status;
            }
        }
        return null;
    }
}
