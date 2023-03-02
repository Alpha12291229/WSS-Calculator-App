package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Var to set for the text display
    var text = ""

    // Set buttons to add to text display
    fun onclick(view: View) {
        text += (view as Button).text
        binding.text.text = text
    }

    //Clears text display
    fun clearall(view: View){
        text = ""
        binding.text.text = text
    }

    //Computing expression
    fun equals(view: View) {
        //Splits the String into a mutable list
        var parts = text.split(" ").toMutableList()
        println(parts)

        // Check if it's a proper expression
        if (parts.size % 2 == 0){ println("Invalid Expression!"); return;}

        // Check if it is a valid expression
        var counter = 0
        for (x in parts) {
            if ((counter == 0 && !isNumeric(x)) || (counter == 1 && !isOperator(x))) { println("Invalid Expression!"); return;}
            counter = (counter + 1) % 2
        }

        //computing
        var count = 0
        while (parts.size > 1)
        {
            parts = compute(parts)
            count++
        }

        //setting the text display to show the answer
        println(parts[0])
        text = parts[0]
        binding.text.text = text
    }

    // Check if the item is numeric
    fun isNumeric(str: String) : Boolean {
        try {
            val num = str.toFloat()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    // Check if item is a valid operator
    fun isOperator(str: String) = if (str == "+" || str == "-" || str == "*" || str == "/" || str == "%" ) true else false

    //Check for times divide modulus to follow PEMDAS
    fun isTimesDivideModulus(str: String) = if (str == "*" || str == "/" || str == "%" ) true else false

    fun compute(parts: MutableList<String>) : MutableList<String> {

        // Check for multiply and divide
        for (i in 1..parts.size-1 step 2) {

            if (isTimesDivideModulus(parts.get(i)))
            {
                val result =  when (parts.get(i)) {
                    "*" -> parts.get(i-1).toFloat() * parts.get(i+1).toFloat()
                    "/" -> parts.get(i-1).toFloat() / parts.get(i+1).toFloat()
                    else -> parts.get(i-1).toFloat() % parts.get(i+1).toFloat()
                }
                parts.set(i, "" + result)
                parts.removeAt(i+1)
                parts.removeAt(i-1)
                return parts
            }
        }

        // check for plus and minus
        for (i in 1..parts.size-1 step 2) {

            val result =  when (parts.get(i)) {
                "+" -> parts.get(i-1).toFloat() + parts.get(i+1).toFloat()
                else -> parts.get(i-1).toFloat() - parts.get(i+1).toFloat()
            }
            parts.set(i, "" + result)
            parts.removeAt(i+1)
            parts.removeAt(i-1)
            return parts

        }
        //output
        return parts
    }

}


