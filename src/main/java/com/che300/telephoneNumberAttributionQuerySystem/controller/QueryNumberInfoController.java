package com.che300.telephoneNumberAttributionQuerySystem.controller;

import com.che300.telephoneNumberAttributionQuerySystem.filter.validator.LegitimateNumber;
import com.che300.telephoneNumberAttributionQuerySystem.model.dto.vo.NumberInfoQueryResult;
import com.che300.telephoneNumberAttributionQuerySystem.service.intf.NumberInfoQueryService;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Validated
@RestController
@RequestMapping(path = "/query")
public class QueryNumberInfoController {
	@NonNull
	private final NumberInfoQueryService numberInfoQueryService;
	
	public QueryNumberInfoController(@NonNull NumberInfoQueryService numberInfoQueryService) {
		this.numberInfoQueryService = numberInfoQueryService;
	}
	
	@GetMapping(value = "/belongingToTheOperator", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<String> queryOperatorName(@RequestParam("netIDNum") @LegitimateNumber(3) final String networkIdentificationNumber) {
		try {
			return ResponseEntity.ok()
					       .cacheControl(
							       CacheControl.maxAge(Duration.ofHours(8))
									       .cachePublic()
									       .noTransform()
									       .immutable()
					       ).body(numberInfoQueryService.queryOperatorName(networkIdentificationNumber).get());
		} catch (final ExecutionException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (final InterruptedException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/detailNumberInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<NumberInfoQueryResult> queryDetailNumberInfo(@RequestParam("telNum") @LegitimateNumber final String telephoneNumber) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok()
				       .cacheControl(CacheControl.maxAge(Duration.ofHours(2)))
				       .body(numberInfoQueryService.queryNumberInfo(telephoneNumber).get());
	}
}
