package com.example.supplementsonlineshopproject.ui.features.profile


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.supplementsonlineshopproject.R
import com.example.supplementsonlineshopproject.ui.theme.Blue
import com.example.supplementsonlineshopproject.ui.theme.Shapes
import com.example.supplementsonlineshopproject.util.MyScreens

import com.example.supplementsonlineshopproject.util.NetworkChecker
import com.example.supplementsonlineshopproject.util.styleTime
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import org.w3c.dom.Text


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<ProfileViewModel>()


    viewModel.loadUserData()

    Box {
//        part1
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileToolbar() {
                navigation.popBackStack()

            }
            MainAnimation()
            Spacer(modifier = Modifier.padding(top = 6.dp))
            ShowDataSection(subject = "Email Address", text = viewModel.email.value, null)
            ShowDataSection(subject = "Address",text = viewModel.address.value) { viewModel.ShowLocationDialog.value = true }
            ShowDataSection(subject = "Postal Code",text = viewModel.postalcode.value) { viewModel.ShowLocationDialog.value = true }
            ShowDataSection(subject = "Login Time", text = styleTime(viewModel.loginTime.value.toLong()) , null)
            Button(onClick = {
                    Toast.makeText(context, "Hope to see you again :)", Toast.LENGTH_SHORT).show()
                    viewModel.signOut()
                    navigation.navigate(MyScreens.MainScreen.route) {
                        popUpTo(MyScreens.MainScreen.route) {
                            inclusive = true
                        }
                        navigation.popBackStack()
                        navigation.popBackStack()
                    }
                },modifier = Modifier.fillMaxWidth(0.8f).padding(top = 32.dp)){Text(text = "Sign Out")}

        }

//        part2
        if (viewModel.ShowLocationDialog.value){
            AddUserLocationDataDialog(
                showSaveLocation = false,
                onDismiss = { viewModel.ShowLocationDialog.value=false},
                onSubmitClicked = {address,postalCode,_->
                    viewModel.setUserLocation(address,postalCode)
                }
            )

        }

    }
}



@Composable
fun ShowDataSection(
    subject: String,
    text: String,
    OnLocationClicked: (() -> Unit)?

) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clickable { OnLocationClicked?.invoke() },
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = subject,
            style = TextStyle(fontSize = 18.sp, color = Blue, fontWeight = FontWeight.Bold)
        )
        Text(
            text = text,
            modifier = Modifier.padding(top = 2.dp),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )
        Divider(color = Blue, thickness = 0.5.dp, modifier = Modifier.padding(top = 16.dp))

    }
}

@Composable
fun AddUserLocationDataDialog(
    showSaveLocation: Boolean,
    onDismiss: () -> Unit,
    onSubmitClicked: (String, String, Boolean) -> Unit
) {

    val context = LocalContext.current
    val checkedState = remember { mutableStateOf(true) }
    val userAddress = remember { mutableStateOf("") }
    val userPostalCode = remember { mutableStateOf("") }
    val fraction = if (showSaveLocation) 0.695f else 0.625f

    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = Modifier.fillMaxHeight(fraction),
            elevation = 8.dp,
            shape = Shapes.medium
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = "Add Location Data",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                MainTextField(userAddress.value,"your address...") {
                    userAddress.value = it
                }

                MainTextField(userPostalCode.value,"your postal code...") {
                    userPostalCode.value = it
                }

                if (showSaveLocation) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                        )

                        Text(text = "Save To Profile")

                    }

                }


                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {

                        if (
                            (userAddress.value.isNotEmpty() || userAddress.value.isNotBlank()) &&
                            (userPostalCode.value.isNotEmpty() || userPostalCode.value.isNotBlank())
                        ) {
                            onSubmitClicked(
                                userAddress.value,
                                userPostalCode.value,
                                checkedState.value
                            )
                            onDismiss.invoke()
                        } else {
                            Toast.makeText(context, "please write first...", Toast.LENGTH_SHORT)
                                .show()
                        }


                    }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}


@Composable
fun MainAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.profile_anim)
    )
    LottieAnimation(
        modifier = Modifier
            .size(270.dp)
            .padding(top = 36.dp, bottom = 16.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun ProfileToolbar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackClick.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        elevation = 2.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = "My Profile",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 58.dp),
            )
        }
    )
}

@Composable
fun MainTextField(
    edtValue: String,
    hint: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = {Text(hint)},
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChange,
        placeholder = {Text(hint)},
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
    )
}






