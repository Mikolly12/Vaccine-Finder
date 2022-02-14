package com.example.vaccinefinder

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter : listAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener {
            val pincode = dcode.text.toString()

            recycleView.layoutManager = LinearLayoutManager(this)

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                    this, { _, year, monthOfYear, dayOfMonth ->
                val dateStr = """$dayOfMonth-${monthOfYear + 1}-${year}"""

                Log.d("TAG", "number  first value of pincode $pincode")
                closeKeyboard()
                if (pincode.length != 6) {
                    Toast.makeText(this, "Please enter valid pincode", Toast.LENGTH_SHORT).show()
                } else {
                    fetchData(pincode, dateStr)
                }
            },
                    year,
                    month,
                    day
            )
            dpd.show()
        }
    }

   private fun fetchData(dicode: String, date: String){

        val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=$dicode&date=$date"
        Log.d("TAG", "number sixth value of pincode $dicode")
        Log.d("TAG", "number seventh value of pincode $date")
        Log.d("TAG", "number : $url")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                {
                    val jsonArray = it.getJSONArray("centers")
                    if (jsonArray.length() == 0) {
                        Toast.makeText(this, "No center available ", Toast.LENGTH_SHORT).show()
                    }
                    val centerArray = ArrayList<dataCenter>()
                    for (i in 0 until jsonArray.length()) {
                        val centerJSONObject = jsonArray.getJSONObject(i)
                        Log.d("TAG", "number eight value of pincode $dicode")
                        Log.d("TAG", "number nine value of pincode $date")
                        val sessionJSONObject = centerJSONObject.getJSONArray("sessions").getJSONObject(0)
                        val avail = sessionJSONObject.getString("available_capacity")
                        val vacc = sessionJSONObject.getString("vaccine")
                        val dat = sessionJSONObject.getString("date")
                        val age = sessionJSONObject.getString("min_age_limit")
                        val center = dataCenter(
                                centerJSONObject.getString("name"),
                                centerJSONObject.getString("address"),
                                centerJSONObject.getString("from"),
                                centerJSONObject.getString("to"),
                                centerJSONObject.getString("fee_type"),
                                vacc,
                                avail,
                                dat,
                                age
                        )
                        centerArray.add(center)
                    }
                    mAdapter = listAdapter(centerArray)
                    recycleView.adapter = mAdapter
                    recycleView.setHasFixedSize(true)
                    mAdapter.notifyDataSetChanged()

                },
                {
                    Toast.makeText(this, "Failed to get response", Toast.LENGTH_SHORT).show()
                }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    private fun closeKeyboard(){
        val view: View? = this.currentFocus

        // if nothing is currently
        // focus then this will protect
        // the app from crash

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            val manager: InputMethodManager = getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager
                    .hideSoftInputFromWindow(
                            view.windowToken, 0)
        }
    }
}