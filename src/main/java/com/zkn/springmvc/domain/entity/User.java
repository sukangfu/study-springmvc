package com.zkn.springmvc.domain.entity;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.zkn.springmvc.domain.enums.UserStatus;

public class User {
	@JsonProperty("userId")
	private Integer userId;
	@JsonProperty("userName")
	private String userName;
	@JsonIgnore
	private String password;
	@JsonProperty("realName")
	private String realName;
	@JsonProperty("alias")
	private String alias;
	@JsonIgnore
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date birth; 
	private UserStatus status;
	@JsonIgnore
	@DateTimeFormat(iso=ISO.DATE)
	private Date registerDate;
	@JsonIgnore
	private boolean isSigned;
	/** 加入钻石计划时间 */
	@JsonIgnore
	private Date signedTime;
	/** 修改时间 */
	@JsonIgnore
	private Date updateTime;
	/** 最后登录时间 */
	@JsonIgnore
	private Date lastLoginTime;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	public boolean isSigned() {
		return isSigned;
	}
	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}
	public Date getSignedTime() {
		return signedTime;
	}
	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
