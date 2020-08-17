package com.car.data.enums;

public enum NoticeEnum {
    Notice_1("1","摇号结果公告"),
    Notice_2("2","竞价情况"),
    Notice_3("3","摇号公告"),
    Notice_4("4","竞价公告"),
    Notice_5("5","配置数量公告"),
    ;
    private String code;
    private String desc;

    NoticeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static NoticeEnum getEnum(String name){
        NoticeEnum[] values = values();
        for (NoticeEnum noticeEnum : values) {
            if (name.contains(noticeEnum.getDesc()))
                return noticeEnum;
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
