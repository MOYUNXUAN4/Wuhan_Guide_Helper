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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserRegister : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance() // 初始化 Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            Wuhan_Guide_HelperTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var isLoading by remember { mutableStateOf(false) } // 加载状态
                    var showSuccessDialog by remember { mutableStateOf(false) } // 注册成功弹窗状态
                    val snackbarHostState = remember { SnackbarHostState() } // Snackbar 状态
                    val coroutineScope = rememberCoroutineScope() // CoroutineScope

                    UserRegisterScreen(
                        isLoading = isLoading,
                        snackbarHostState = snackbarHostState,
                        showSuccessDialog = showSuccessDialog,
                        onRegisterClick = { email, password, username ->
                            isLoading = true // 开始加载
                            registerUser(email, password, username, onError = { errorMessage ->
                                isLoading = false // 停止加载
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(errorMessage) // 显示错误提示
                                }
                            }, onSuccess = {
                                isLoading = false // 停止加载
                                showSuccessDialog = true // 显示注册成功弹窗
                            })
                        },
                        onDismissSuccessDialog = {
                            showSuccessDialog = false // 关闭注册成功弹窗
                            val intent = Intent(this, UserSignIn::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }

    private fun registerUser(
        email: String,
        password: String,
        username: String,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 注册成功
                    val user = auth.currentUser
                    val userId = user?.uid ?: ""
                    val userData = hashMapOf(
                        "email" to email,
                        "username" to username,
                        "createdAt" to System.currentTimeMillis()
                    )

                    // 将用户信息存储到 Firestore
                    db.collection("users")
                        .document(userId)
                        .set(userData)
                        .addOnSuccessListener {
                            println("User data saved!")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            println("Failed to save user data: ${e.message}")
                            onError("Failed to save user data: ${e.message}")
                        }
                } else {
                    // 注册失败
                    val error = task.exception
                    val errorMessage = when {
                        error?.message?.contains("The email address is badly formatted") == true -> "Invalid email format."
                        error?.message?.contains("The password is too weak") == true -> "Password is too weak."
                        error?.message?.contains("The email address is already in use") == true -> "Email already exists."
                        else -> error?.message ?: "Unknown error"
                    }
                    println("Registration failed: $errorMessage")
                    onError(errorMessage)
                }
            }
    }
}

@Composable
fun UserRegisterScreen(
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    showSuccessDialog: Boolean,
    onRegisterClick: (String, String, String) -> Unit,
    onDismissSuccessDialog: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") } // 用户名输入框

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 用户名输入框
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 注册按钮
        Button(
            onClick = { onRegisterClick(email, password, username) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Register")
            }
        }

        // SnackbarHost
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    // 注册成功弹窗
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = onDismissSuccessDialog,
            title = { Text("Registration Successful") },
            text = { Text("You have successfully registered!") },
            confirmButton = {
                Button(onClick = onDismissSuccessDialog) {
                    Text("OK")
                }
            }
        )
    }
}