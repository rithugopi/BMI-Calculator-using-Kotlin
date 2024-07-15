package com.example.bmicalculator
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var editTextHeight: EditText
    private lateinit var editTextWeight: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var textViewResult: TextView
    private lateinit var textViewHeightLabel: TextView
    private lateinit var textViewWeightLabel: TextView
    private var isImperial: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.spinner)
        editTextHeight = findViewById(R.id.editTextHeight)
        editTextWeight = findViewById(R.id.editTextWeight)
        buttonCalculate = findViewById(R.id.button)
        textViewResult = findViewById(R.id.textViewResult)
        textViewHeightLabel = findViewById(R.id.textViewHeightLabel)
        textViewWeightLabel = findViewById(R.id.textViewWeightLabel)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.system_types,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                val selectedSystem =
                    parent.getItemAtPosition(position).toString()
                isImperial = selectedSystem == "Imperial System"
                updateLabels()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        buttonCalculate.setOnClickListener {
            calculateBMI()
        }
    }

    private fun updateLabels() {
        if (isImperial) {
            textViewHeightLabel.text = "Height (inches)"
            textViewWeightLabel.text = "Weight (pounds)"
        } else {
            textViewHeightLabel.text = "Height (meters)"
            textViewWeightLabel.text = "Weight (kilograms)"
        }
    }
    private fun calculateBMI() {
        val heightInput = editTextHeight.text.toString()
        val weightInput = editTextWeight.text.toString()
        if (heightInput.isNotEmpty() && weightInput.isNotEmpty()) {
            val height = heightInput.toDouble()
            val weight = weightInput.toDouble()
            val bmi: Double = if (isImperial) {
                (weight / (height * height)) * 703
            } else {
                weight / (height * height)
            }
            textViewResult.text = String.format("Your BMI is: %.2f", bmi)
        } else {
            Toast.makeText(this, "Please enter both height and weight",
                Toast.LENGTH_SHORT).show()
        }
    }
}
