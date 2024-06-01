package com.joon.d_day

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.GregorianCalendar

class MainActivity : AppCompatActivity() {

    private lateinit var endDate: LocalDate
    private lateinit var startDate: LocalDate
    private lateinit var startSeasonDate: TextView

    private var isStartDateInitialized = false
    private var isEndDateInitialized = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val endBtn = findViewById<Button>(R.id.endBtn)
        val startBtn = findViewById<Button>(R.id.startBtn)
        startSeasonDate = findViewById(R.id.startSeasonDate)

        endBtn.setOnClickListener {
            showDatePicker { selectedDate ->
                endDate = selectedDate
                isEndDateInitialized = true
                endBtn.text =
                    "23/24閉幕日 \n ${selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}"
                if (isStartDateInitialized && isEndDateInitialized) {
                    calculateDaysBetweenDates()
                }
            }
        }

        startBtn.setOnClickListener {
            showDatePicker { selectedDate ->
                startDate = selectedDate
                isStartDateInitialized = true
                startBtn.text =
                    "24/25開幕日 \n  ${selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}"
                if (isStartDateInitialized && isEndDateInitialized) {
                    calculateDaysBetweenDates()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker(onDateSelected: (LocalDate) -> Unit) {
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                onDateSelected(selectedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDaysBetweenDates() {
        if (isStartDateInitialized && isEndDateInitialized) {
            val daysBetween = endDate.toEpochDay() - startDate.toEpochDay()
            startSeasonDate.text = "次Season開幕まで: $daysBetween 日"
        }
    }
}