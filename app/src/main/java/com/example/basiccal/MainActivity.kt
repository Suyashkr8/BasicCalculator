package com.example.basiccal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    private var tvInput: TextView? = null
    private var tvHistory: TextView? = null

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    var history = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
        tvHistory = findViewById(R.id.tvHistory)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)//here view is a button but we cannot directly
        //use .text property so we say view is a button but we need to convert
        //it into button to use .text property

        lastNumeric = true
        lastDot = false

    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true

        }
    }

    fun onOperator(view: View)
    {
        (tvInput?.text)?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View)
    {
        if (lastNumeric)
        {
            var tvValue= tvInput?.text.toString()
            var prefix=""

            try {
                if (tvValue.startsWith("-"))
                {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-"))
                {
                    val splitValue = tvValue.split("-")

                    var one=splitValue[0]
                    val two=splitValue[1]

                    if(prefix.isNotEmpty())
                    {
                        one= prefix + one
                    }

                    val afterDotRemoval= removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    history.add("$one - $two = $afterDotRemoval")

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if(tvValue.contains("+"))
                {
                    val splitValue = tvValue.split("+")

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty())
                    {
                        one= prefix + one
                    }

                    val afterDotRemoval= removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    history.add("$one + $two = $afterDotRemoval")

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("*"))
                {
                    val splitValue = tvValue.split("*")

                    var one=splitValue[0]
                    val two=splitValue[1]

                    if(prefix.isNotEmpty())
                    {
                        one= prefix + one
                    }

                    val afterDotRemoval= removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    history.add("$one * $two = $afterDotRemoval")

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
                else if(tvValue.contains("/"))
                {
                    val splitValue = tvValue.split("/")

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty())
                    {
                        one= prefix + one
                    }

                    val afterDotRemoval= removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    history.add("$one / $two = $afterDotRemoval")

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }
            catch (e: ArithmeticException)
            {
                e.printStackTrace()
            }
        }
    }

    fun onBack(view: View)
    {
        val tvData = tvInput?.text.toString()

        if(tvData!="")
        {
            if('+'==tvData.last() || '-'==tvData.last() || '*'==tvData.last() || '/'==tvData.last())
            {
                lastNumeric = true
            }
            tvInput?.text = tvData.substring(0, tvData.length - 1)
        }

    }

    fun onHistory(view: View)
    {
        if(history.isNotEmpty())
        {
            val lastItem = history.removeLast()
            tvHistory?.text = lastItem
        }



    }

    private fun removeZeroAfterDot(result: String ) : String
    {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length-2)

        return value
    }

    private fun isOperatorAdded( value: String ) : Boolean
    {

        return if(value.startsWith("-")) {
            false
        }
        else
        {
            // if any of the below statements is true than it will return true
            value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
                    || value.contains("/")
        }
    }




}
