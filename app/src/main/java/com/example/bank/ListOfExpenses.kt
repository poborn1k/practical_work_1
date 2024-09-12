package com.example.bank

class ListOfExpenses {
    private val expensesList = mutableListOf<Expenses>()

    fun addExpenses(item: Expenses) {
        expensesList.add(item)
    }

    fun displayAllExpenses(): String {
        if (expensesList.isEmpty())
            return "Информация не найдены"

        val amountString = StringBuilder()
        for (item in expensesList)
            amountString.append(item.displayDetails() + "\n")

        return amountString.toString()
    }

    fun countDeposits(): String {
        if (expensesList.isEmpty())
            return "Информация не найдены"

        val amountByCategory =  mutableMapOf("Супермаркеты" to 0.0, "Транспорт" to 0.0, "Подписки" to 0.0, "Здоровье" to 0.0)
        for (item in expensesList)
            if (amountByCategory.containsKey(item.category))
                amountByCategory[item.category] = amountByCategory[item.category]!! + item.amount

        val amountString = StringBuilder()
        for  (item in amountByCategory)
            amountString.append("${item.key}: ${item.value}\n")

        return amountString.toString()
    }
}