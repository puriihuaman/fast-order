package fast_order.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleType {
	ADMIN("admin"), USER("user"), INVITED("invited");

	private final String value;

	RoleType(String roleName) {
		this.value = roleName;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
