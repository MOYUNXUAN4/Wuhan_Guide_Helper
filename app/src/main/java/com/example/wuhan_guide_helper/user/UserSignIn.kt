package com.example.wuhan_guide_helper.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.wuhan_guide_helper.R
import com.example.wuhan_guide_helper.databaseUi.MainActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserSignIn : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            Wuhan_Guide_HelperTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var isLoading by remember { mutableStateOf(false) } // 加载状态
                    var showSuccessDialog by remember { mutableStateOf(false) } // 登录成功弹窗状态
                    val snackbarHostState = remember { SnackbarHostState() } // Snackbar 状态
                    val coroutineScope = rememberCoroutineScope() // CoroutineScope

                    UserSignInScreen(
                        isLoading = isLoading,
                        snackbarHostState = snackbarHostState,
                        showSuccessDialog = showSuccessDialog,
                        onLoginClick = { email, password ->
                            isLoading = true // 开始加载
                            loginUser(email, password, onError = { errorMessage ->
                                isLoading = false // 停止加载
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(errorMessage) // 显示错误提示
                                }
                            }, onSuccess = {
                                isLoading = false // 停止加载
                                showSuccessDialog = true // 显示登录成功弹窗
                            })
                        },
                        onRegisterClick = {
                            val intent = Intent(this, UserRegister::class.java)
                            startActivity(intent)
                        },
                        onDismissSuccessDialog = {
                            showSuccessDialog = false // 关闭登录成功弹窗
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }

    private fun loginUser(
        email: String,
        password: String,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 登录成功
                    println("Login successful!")
                    onSuccess()
                } else {
                    // 登录失败
                    val error = task.exception
                    val errorMessage = when {
                        error?.message?.contains("password is invalid") == true -> "Invalid password."
                        error?.message?.contains("no user record") == true -> "Email does not exist."
                        else -> error?.message ?: "Unknown error"
                    }
                    println("Login failed: $errorMessage")
                    onError(errorMessage)
                }
            }
    }
}

@Composable
fun UserSignInScreen(
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    showSuccessDialog: Boolean,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onDismissSuccessDialog: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 邮箱输入框
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 密码输入框
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                        ),
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 登录按钮
        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 注册按钮
        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Register")
        }

        // SnackbarHost
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    // 登录成功弹窗
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = onDismissSuccessDialog,
            title = { Text("Login Successful") },
            text = { Text("You have successfully logged in!") },
            confirmButton = {
                Button(onClick = onDismissSuccessDialog) {
                    Text("OK")
                }
            }
        )
    }
}