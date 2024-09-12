package com.example.bank

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import java.util.Date


class MainActivity : AppCompatActivity() {
    private var balance = 50000.0
    private lateinit var showBalanceView: TextView

    private lateinit var operationInputView: TextInputEditText
    private lateinit var confirmButton: Button

    private lateinit var amountCategoryView: TextView
    private lateinit var allExpensesView: TextView

    private val types = arrayOf("Пополнение", "Супермаркеты", "Транспорт", "Подписки", "Здоровье")
    private lateinit var typeSelector: Spinner
    private lateinit var selectedCategory: String

    private val listOfExpenses: ListOfExpenses = ListOfExpenses()
    private lateinit var expenseObject: Expenses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializingComponents()
        updateHistoryData()

        typeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedType = parent.getItemAtPosition(position).toString()
                selectedCategory = selectedType
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedCategory = "Нет"
            }
        }

        confirmButton.setOnClickListener {
            manipulateBalance(selectedCategory)
        }
    }

    private fun initializingComponents() {
        showBalanceView = findViewById(R.id.account_balance_show)
        amountCategoryView = findViewById(R.id.amount_all_time_show)
        allExpensesView = findViewById(R.id.all_expenses_show)
        operationInputView = findViewById(R.id.number_input)
        confirmButton = findViewById(R.id.confirm_button)
        typeSelector = findViewById(R.id.category_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSelector.adapter = adapter
    }

    private fun updateHistoryData() {
        amountCategoryView.text = listOfExpenses.countDeposits()
        allExpensesView.text = listOfExpenses.displayAllExpenses()
        showBalanceView.text = "$balance"
    }

    private fun manipulateBalance(category: String) {
        var operationInput: Double? = operationInputView.text.toString().toDoubleOrNull()
        val date: Date = Calendar.getInstance().time

        if (operationInput != null) {
            if (!category.equals("Пополнение"))
                operationInput *= -1
            expenseObject = Expenses(operationInput, category, date.toString())
            listOfExpenses.addExpenses(expenseObject)
            balance += operationInput
        }

        updateHistoryData()
    }
}