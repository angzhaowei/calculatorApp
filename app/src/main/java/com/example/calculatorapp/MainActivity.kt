package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null

    var lastNumeric :Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        // view doesnt have text property
        // the button is the view in this case,
        // so it has be cast to a button, then can get its text property
        tvInput?.append((view as Button).text)
        lastNumeric=true
        lastDot=false

    }

    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimal(view:View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator (view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }


    }

    private fun isOperatorAdded(value: String) :Boolean {
        // doesnt allow for more than one operator in the eqn
        // unless its a -ve at the start
        return if(value.startsWith("-")){
            false
        }
        else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    private fun removeZeroAfterDot(result :String): String {
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length-2)
        }
        return value
    }

    fun onEqual(view: View){
        if(lastNumeric){

            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try{
                // taking out the -ve sign if have
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                // minus
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() - second.toDouble()).toString())
                }

                // plus
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() + second.toDouble()).toString())
                }

                // divide
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() / second.toDouble()).toString())
                }

                // multiply
                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() * second.toDouble()).toString())
                }

                else {
                    tvInput?.text = tvValue
                }

            }
            catch(e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}