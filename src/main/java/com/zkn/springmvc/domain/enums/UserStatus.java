package com.zkn.springmvc.domain.enums;

/**
 * 用户状态枚举
 * 
 * @author MR.SU
 * 
 */
public enum UserStatus {

	/** 注册 */
	REGISTE {
		public String getName() {
			return "注册";
		}
	},
	/** 已激活 */
	ACTIVED {
		public String getName() {
			return "已激活";
		}
	},
	/** 已锁定 */
	LOCKING {
		public String getName() {
			return "已锁定";
		}
	},
	/** 已停用 */
	STOPED {
		public String getName() {
			return "已停用";
		}
	},
	/** 已作废 */
	DELETED {
		public String getName() {
			return "已作废";
		}
	};

	public abstract String getName();
}
