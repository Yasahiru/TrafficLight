package com.cmc.traficlight

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cmc.traficlight.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val red = binding.red
        val orange = binding.orange
        val green = binding.green
        val stop = binding.stop
        var stopColor:String? = null

        fun setBackground(color:String){
            when(color){
                "red" -> {
                    red.background = ContextCompat.getDrawable(this, R.drawable.red_light_blur)
                    orange.background = ContextCompat.getDrawable(this,R.drawable.dark_orange_light_blur)
                    green.background = ContextCompat.getDrawable(this,R.drawable.dark_green_light_blur)
                    stopColor = "red"
                }
                "orange" -> {
                    orange.background = ContextCompat.getDrawable(this, R.drawable.orange_light_blur)
                    green.background = ContextCompat.getDrawable(this,R.drawable.dark_green_light_blur)
                    red.background = ContextCompat.getDrawable(this,R.drawable.dark_red_light_blur)
                    stopColor = "orange"
                }
                "green" -> {
                    green.background = ContextCompat.getDrawable(this, R.drawable.green_light_blur)
                    orange.background = ContextCompat.getDrawable(this,R.drawable.dark_orange_light_blur)
                    red.background = ContextCompat.getDrawable(this,R.drawable.dark_red_light_blur)
                    stopColor = "green"
                }
            }
        }

        fun reset(){
            red.background = ContextCompat.getDrawable(this, R.drawable.red_light_blur)
            orange.background = ContextCompat.getDrawable(this,R.drawable.dark_orange_light_blur)
            green.background = ContextCompat.getDrawable(this,R.drawable.dark_green_light_blur)
            stop.setBackgroundColor(Color.parseColor("#FF0000"))
        }

        val scenario1 = binding.scenario1
        val scenario2 = binding.scenario2
        val stopScenario = binding.stop

        var job1: Job? = null
        var job2: Job? = null

        fun traficlightScenario1(){
            stop.setBackgroundColor(Color.parseColor("#4D0000"))
            job1 = CoroutineScope(Dispatchers.Main).launch{
                while(true){
//                    when(stopColor){
//                        "red" -> {
//                            setBackground("red")
//                            delay(1400)
//                            setBackground("orange")
//                            delay(1000)
//                            setBackground("green")
//                            delay(2000)
//                        }
//                        "orange" -> {
//                            setBackground("orange")
//                            delay(1000)
//                            setBackground("green")
//                            delay(2000)
//                            setBackground("red")
//                            delay(2000)
//                        }
//                        "green" -> {
//                            setBackground("green")
//                            delay(2000)
//                            setBackground("red")
//                            delay(2000)
//                            setBackground("orange")
//                            delay(1000)
//                        }
                        //else->{}
//                  }
                    setBackground("red")
                    delay(2000)
                    setBackground("orange")
                    delay(1500)
                    setBackground("green")
                    delay(2000)
                }
            }
        }

        fun traficlightScenario2(){
            stop.setBackgroundColor(Color.parseColor("#4D0000"))
            green.background = ContextCompat.getDrawable(this,R.drawable.dark_green_light_blur)
            red.background = ContextCompat.getDrawable(this,R.drawable.dark_red_light_blur)
            job2 = CoroutineScope(Dispatchers.Main).launch{
                while(true){
                    orange.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.orange_light_blur)
                    delay(1000)
                    orange.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.dark_orange_light_blur)
                    delay(1000)
                }
            }
        }

        fun stoptraficlight(){
            job1?.cancel()
            job2?.cancel()
            reset()
        }
        traficlightScenario1()

        scenario1.setOnClickListener{
            job2?.cancel()
            traficlightScenario1()
        }
        scenario2.setOnClickListener{
            job1?.cancel()
            traficlightScenario2()
        }
        stopScenario.setOnClickListener{
            stoptraficlight()
        }

    }
}