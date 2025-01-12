package com.example.wuhan_guide_helper.internet

import retrofit2.http.GET

interface ExchangeRateService {
    @GET("v6/873b4804ca86f88dd7f84ff3/latest/USD")
    suspend fun getExchangeRate(): ExchangeRateResponse
}