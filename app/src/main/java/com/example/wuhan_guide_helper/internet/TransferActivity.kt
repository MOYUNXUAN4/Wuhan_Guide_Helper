package com.example.wuhan_guide_helper.internet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TransferActivity : ComponentActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://v6.exchangerate-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val exchangeRateService = retrofit.create(ExchangeRateService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TransferScreen(exchangeRateService = exchangeRateService)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(exchangeRateService: ExchangeRateService) {
    var rmbAmount by remember { mutableStateOf("") }
    var myrAmount by remember { mutableStateOf("") }
    var exchangeRate by remember { mutableStateOf(0.0) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var lastUpdated by remember { mutableStateOf("") }
    var isInputValid by remember { mutableStateOf(true) }

    // Fetch exchange rate from the network
    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null
        try {
            val response = exchangeRateService.getExchangeRate()
            if (response.result == "success") {
                val usdToCny = response.conversion_rates["CNY"] ?: 0.0
                val usdToMyr = response.conversion_rates["MYR"] ?: 0.0
                exchangeRate = if (usdToCny != 0.0) usdToMyr / usdToCny else 0.0
                lastUpdated = response.time_last_update_utc
            } else {
                errorMessage = "Unable to fetch exchange rate."
            }
        } catch (e: Exception) {
            errorMessage = "Network request failed: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Custom theme color
    val customPrimaryColor = Color(0xFFB497BD) // Hex: #B497BD

    // Main layout with TopBar
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp) // 设置 TopBar 高度
                    .background(customPrimaryColor), // 设置背景色
                contentAlignment = Alignment.CenterStart // 文字居左
            ) {
                Text(
                    text = "Currency Converter",
                    color = Color.White, // 文字颜色
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold), // 加大字体并加粗
                    modifier = Modifier.padding(start = 16.dp) // 左边距
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Input/Output Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // RMB Input
                            TextField(
                                value = rmbAmount,
                                onValueChange = {
                                    rmbAmount = it
                                    isInputValid = it.toDoubleOrNull() != null
                                },
                                label = { Text("Amount in RMB (CNY)") },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clickable { rmbAmount = "" }, // 点击时清空内容
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    focusedIndicatorColor = customPrimaryColor,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                                ),
                                isError = !isInputValid
                            )

                            if (!isInputValid) {
                                Text(
                                    text = "Please enter a valid number.",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }

                            // MYR Output
                            TextField(
                                value = myrAmount,
                                onValueChange = { },
                                label = { Text("Amount in MYR (MYR)") },
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    focusedIndicatorColor = customPrimaryColor,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                                )
                            )
                        }
                    }

                    // Convert Button
                    ElevatedButton(
                        onClick = {
                            val rmb = rmbAmount.toDoubleOrNull() ?: 0.0
                            myrAmount = String.format("%.2f", rmb * exchangeRate)
                        },
                        enabled = !isLoading && exchangeRate > 0 && isInputValid,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = customPrimaryColor,
                            contentColor = Color.White,
                            disabledContainerColor = customPrimaryColor.copy(alpha = 0.5f)
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Loading...")
                        } else {
                            Text("Convert")
                        }
                    }

                    // Last Updated Time
                    if (lastUpdated.isNotEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Last Updated: $lastUpdated",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "API Support: ExchangeRate-API",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    // Error Message
                    if (errorMessage != null) {
                        AssistChip(
                            onClick = { errorMessage = null },
                            label = { Text(errorMessage ?: "") },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                labelColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        )
                    }
                }
            }
        }
    )
}



