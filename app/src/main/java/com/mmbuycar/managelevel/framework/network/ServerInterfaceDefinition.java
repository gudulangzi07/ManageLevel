package com.mmbuycar.managelevel.framework.network;

public enum ServerInterfaceDefinition {

    /**
     * 2.2	用户----手机注册
     */
    OPT_REGISTER("app/user/updateRegister.do"),
    //底部
    ;

    private String opt;

    ServerInterfaceDefinition(String opt) {
        this.opt = opt;
    }

    public String getOpt() {
        return opt;
    }

}
