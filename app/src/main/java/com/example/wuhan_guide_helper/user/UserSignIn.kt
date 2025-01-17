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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.R
import com.example.wuhan_guide_helper.MainActivity
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
                    var isLoading by remember { mutableStateOf(false) }
                    var showSuccessDialog by remember { mutableStateOf(false) }
                    val snackbarHostState = remember { SnackbarHostState() }
                    val coroutineScope = rememberCoroutineScope()

                    UserSignInScreen(
                        isLoading = isLoading,
                        snackbarHostState = snackbarHostState,
                        showSuccessDialog = showSuccessDialog,
                        onLoginClick = { email, password ->
                            isLoading = true
                            loginUser(email, password, onError = { errorMessage ->
                                isLoading = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(errorMessage)
                                }
                            }, onSuccess = {
                                isLoading = false
                                showSuccessDialog = true
                            })
                        },
                        onRegisterClick = {
                            val intent = Intent(this, UserRegister::class.java)
                            startActivity(intent)
                        },
                        onDismissSuccessDialog = {
                            showSuccessDialog = false
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
                    println("Login successful!")
                    onSuccess()
                } else {
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
    val tianOneFontFamily = FontFamily(
        Font(R.font.sigmaroneregular)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Wuhan Travel Guide\nLog In",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = tianOneFontFamily,
                fontSize = 60.sp,
                lineHeight = 60.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB497BD)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB497BD)
            )
        ) {
            Text("Register")
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

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