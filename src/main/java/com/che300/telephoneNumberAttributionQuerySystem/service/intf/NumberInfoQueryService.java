package com.che300.telephoneNumberAttributionQuerySystem.service.intf;


import com.che300.telephoneNumberAttributionQuerySystem.model.dto.vo.NumberInfoQueryResult;
import org.springframework.lang.NonNull;

import java.util.concurrent.CompletableFuture;

public interface NumberInfoQueryService {
	CompletableFuture<String> queryOperatorName(@NonNull final String networkIdentificationNumber);
	CompletableFuture<NumberInfoQueryResult> queryNumberInfo(@NonNull final String telephoneNumber);
}
