package com.taniltekdemir.n11.dischargeSystem.debt.enums;

public enum EnumDebtType {

    NORMAL("NORMAL"),
    LATE_FEE("LATE_FEE")
    ;
    private String type;

    EnumDebtType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
