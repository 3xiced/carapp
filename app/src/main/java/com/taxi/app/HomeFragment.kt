package com.taxi.app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var etSearch: EditText
    private lateinit var rvCars: RecyclerView
    private lateinit var adapter: CarAdapter

    // Изначальный список автомобилей (пример)
    private val allCars = listOf(
        Car("S 500 Sedan", "Mercedes-Benz", 2500, "A/T", "Бензин", R.drawable.mercedes_s500),
        Car("X5", "BMW", 2200, "A/T", "Дизель", R.drawable.bmw_x5),
        Car("Model 3", "Tesla", 3000, "A/T", "Электро", R.drawable.tesla_model3),
        // добавьте ещё
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearch = view.findViewById(R.id.etSearch)
        rvCars = view.findViewById(R.id.rvCars)

        adapter = CarAdapter(allCars,
            onBookClick = { car ->
                // Обработка нажатия "Забронировать"

            },
            onDetailsClick = { car ->
                // Обработка нажатия "Детали"

            }
        )

        rvCars.layoutManager = LinearLayoutManager(requireContext())
        rvCars.adapter = adapter

        // Поиск (фильтрация по марке)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()
                val filtered = allCars.filter { car ->
                    car.brand.lowercase().contains(query) ||
                            car.model.lowercase().contains(query)
                }
                adapter.updateList(filtered)
            }
        })
    }
}
