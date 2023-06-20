package com.che300.telephoneNumberAttributionQuerySystem.model.dto.vo;

import com.fasterxml.jackson.databind.type.SimpleType;

public record NumberInfoQueryResult(int code, Data data) {
	public record Data(
		String city, String province, String sp
	) { }
	public static final SimpleType TYPE = SimpleType.constructUnsafe(NumberInfoQueryResult.class);
}
