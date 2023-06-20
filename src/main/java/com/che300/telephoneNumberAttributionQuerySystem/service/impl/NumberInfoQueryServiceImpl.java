package com.che300.telephoneNumberAttributionQuerySystem.service.impl;

import com.che300.telephoneNumberAttributionQuerySystem.model.dto.vo.NumberInfoQueryResult;
import com.che300.telephoneNumberAttributionQuerySystem.service.intf.NumberInfoQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;

@Service
public class NumberInfoQueryServiceImpl implements NumberInfoQueryService {
	@NonNull
	private final ObjectMapper objectMapper;
	@NonNull
	private final HttpClient queryInfoHTTPClient;
	@NonNull
	private final HttpRequest.Builder httpRequestBuilder;
	@NonNull
	private final WeakHashMap<String, String> prefixCache;
	
	public NumberInfoQueryServiceImpl(@NonNull final ObjectMapper objectMapper) {
		this.queryInfoHTTPClient = HttpClient.newBuilder()
				                           .connectTimeout(Duration.ofSeconds(8))
				                           .version(Version.HTTP_2)
				                           .build();
		this.httpRequestBuilder = HttpRequest.newBuilder()
				                          .GET()
				                          .version(Version.HTTP_2)
				                          .expectContinue(true);
		this.objectMapper = objectMapper;
		this.prefixCache = new WeakHashMap<>();
	}
	
	@Override
	public CompletableFuture<String> queryOperatorName(@NonNull final String networkIdentificationNumber) {
		return this.prefixCache.containsKey(networkIdentificationNumber)
				       ?
				       CompletableFuture.supplyAsync(() -> this.prefixCache.get(networkIdentificationNumber))
				       :
				       this.queryInfoHTTPClient.sendAsync(
						       httpRequestBuilder.copy()
								       .uri(URI.create("https://cx.shouji.360.cn/phonearea.php?number=" + networkIdentificationNumber + "0000000"))
								       .build(),
						       BodyHandlers.ofInputStream()
				       ).handle((response, ignored) -> {
					       if ( ignored != null ) throw new RuntimeException(ignored);
					       try {
						       return this.numberInfoMap(response).data().sp();
					       } catch (final IOException e) {
						       throw new RuntimeException(e);
					       }
				       }).whenCompleteAsync((result, e) -> prefixCache.put(networkIdentificationNumber, result));
	}
	
	@Override
	public CompletableFuture<NumberInfoQueryResult> queryNumberInfo(@NonNull final String telephoneNumber) {
		return queryInfoHTTPClient.sendAsync(
				httpRequestBuilder.copy()
						.uri(URI.create("https://cx.shouji.360.cn/phonearea.php?number=" + telephoneNumber))
						.build(),
				BodyHandlers.ofInputStream()
		).handle((response, ignored) -> {
			if ( ignored != null ) throw new RuntimeException(ignored);
			try {
				return this.numberInfoMap(response);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	private NumberInfoQueryResult numberInfoMap(@NonNull final HttpResponse<InputStream> response) throws IOException {
		return this.objectMapper.readValue(response.body(), NumberInfoQueryResult.TYPE);
	}
}
