package com.cbmobile.inventory.compose.ui.composable.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmobile.inventory.compose.R
import com.cbmobile.inventory.compose.ui.composable.MainActivity

import com.cbmobile.inventory.compose.ui.theme.InventoryTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val viewModel = LoginViewModel(this.applicationContext)
                    SetupLogin(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun SetupLogin(viewModel: LoginViewModel){
    val username = viewModel.username.observeAsState("")
    val password =  viewModel.password.observeAsState("")
    val isError = viewModel.isError.observeAsState(false)

    Login(username = username.value,
        password = password.value,
        isLoginError = isError.value,
        onUsernameChanged = viewModel.onUsernameChanged,
        onPasswordChanged = viewModel.onPasswordChanged,
        login = viewModel::login)
}

@Composable
fun Login(
    username: String,
    password: String,
    isLoginError: Boolean,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    login: () -> Boolean) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Image (
            painter = painterResource(id = R.drawable.acmelogo),
            contentDescription = "Logo",
            modifier = Modifier.padding(bottom = 32.dp)
        ) 
        OutlinedTextField(value = username,
            onValueChange = { onUsernameChanged(it) },
            label = { Text("username") }
        )
        OutlinedTextField(modifier =  Modifier.padding(top = 16.dp),
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text("password")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(modifier = Modifier.padding(top = 32.dp),
            onClick = {
                if (login()){
                    context.startActivity(Intent(context, MainActivity::class.java))
                }

            })
        {
            Text("Login", style = MaterialTheme.typography.h5)
        }
        if (isLoginError) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.error,
                text = "Error: username or password is incorrect"
            )
        }
   }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = LoginViewModel(LocalContext.current)
    val username : String by viewModel.username.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val isError: Boolean by viewModel.isError.observeAsState(false)

    InventoryTheme {
        Login(username = username,
            password = password,
            isLoginError = isError,
            onUsernameChanged = viewModel.onUsernameChanged,
            onPasswordChanged = viewModel.onPasswordChanged,
            login = viewModel::login)
    }
}