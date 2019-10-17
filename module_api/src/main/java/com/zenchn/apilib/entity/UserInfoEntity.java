package com.zenchn.apilib.entity;

import java.io.Serializable;

/**
 * @author HZJ
 */

public class UserInfoEntity implements Serializable {

    private static final long serialVersionUID = 4075623417052041363L;


    /**
     * accountId : 00000000000000000000000000000002
     * createTime : 2018-08-01 09:16:01
     * account : zenchn
     * password : 91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203
     * status : 1
     * realName : 正呈管理员
     * sex : null
     * tel : 13000000000
     * email : null
     * portraitFileId : null
     * roleId : 00000000000000000000000000000001
     * orgId : 00000000000000000000000000000001
     * postTypeId : null
     * isDefault : true
     * expireTime : null
     * memo : null
     * finalLoginIp : 122.224.145.42
     * finalLoginTime : 2019-07-29 16:09:52
     * finalLoginLocation : 中国浙江杭州
     * statusName : 启用
     * portraitUrl : null
     * roleName : 正呈管理员
     * orgName : 浙大正呈科技有限公司
     * postTypeName : null
     */

    private String accountId;
    private String createTime;
    private String account;
    private String password;
    private int status;
    private String realName;
    private String sex;
    private String tel;
    private String email;
    private String roleId;
    private String orgId;
    private String portraitUrl;
    private String roleName;
    private String orgName;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "accountId='" + accountId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", roleId='" + roleId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", roleName='" + roleName + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
