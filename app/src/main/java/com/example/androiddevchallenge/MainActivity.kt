/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        CountDown()
    }
}



@ExperimentalAnimationApi
@Composable
fun CountDown(){
    Column(modifier = Modifier
        .padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

        var timeValue: Long = 90000


        var timeCt:String by rememberSaveable { mutableStateOf(miliToTime(timeValue))}
        var startTimer: Boolean by rememberSaveable{ mutableStateOf(false)}
        var btnText: String by rememberSaveable { mutableStateOf("START")}

        var magenta by rememberSaveable {mutableStateOf(true)}
        val color by animateColorAsState(if(magenta) Magenta else Green)

        var countTimer = object: CountDownTimer(timeValue,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeCt = miliToTime(millisUntilFinished)
                magenta = !magenta
            }

            override fun onFinish() {
                timeCt = "00:00"
                btnText = "RESTART"
                startTimer = false


            }
        }


        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = timeCt,color= color, style= MaterialTheme.typography.h1)
        }

        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom){
            Button(modifier = Modifier.padding(5.dp),onClick = {
                if(!startTimer) {
                    countTimer.start()
                    startTimer = true
                    btnText = "START"
                }
            }) {
                Text(text = btnText, style = MaterialTheme.typography.h6)
            }


        }


    }
}

fun miliToTime(milisec: Long): String{
    val numberFormat = DecimalFormat("00")
    val second: Long = (milisec/1000) % 60
    val minute: Long = (milisec/60000) % 60

    return "${numberFormat.format(minute)}:${numberFormat.format(second)}"
}


@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
