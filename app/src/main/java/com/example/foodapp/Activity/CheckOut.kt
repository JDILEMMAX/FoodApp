package com.example.foodapp.Activity

import android.content.Intent
import com.example.foodapp.R
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.foodapp.Helper.ManagementCart
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class CheckOut : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        var managementCart = ManagementCart(this)
        var show = findViewById<TextView>(R.id.sumTotal)
        show.setText(""+managementCart.total().toInt())
        println(managementCart.total())
        /*return*//* When you return a value or result from a function on top of some other code, The code will be highlighted
        * in yellow and termed as Unreachable Code,this is because return statements halt any code compilation below
        * the statements(This is due to code compilation logic,code is read line by line in hierarchy beginning from the top)...The code will be ignored*/

        var mpesa = findViewById<LinearLayout>(R.id.mpesa)
        var arrow = findViewById<ImageView>(R.id.arrow)
        var phone = findViewById<EditText>(R.id.editTextPhone)
        var add = findViewById<ConstraintLayout>(R.id.add)
        var order = findViewById<ConstraintLayout>(R.id.order)
        var progressbar = findViewById<ProgressBar>(R.id.progressbar)
        var code = findViewById<TextView>(R.id.code)
        var codeString = "254"

        add.setOnClickListener {
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        order.setOnClickListener {
            order.visibility=View.GONE
            mpesa.visibility=View.VISIBLE
            progressbar.visibility=View.VISIBLE
        }

        //Mpesa integration
        arrow.setOnClickListener {
            order.visibility=View.VISIBLE
            mpesa.visibility=View.GONE
            val client = AsyncHttpClient(true,80,443)
            val body= JSONObject()
//
            body.put("amount",managementCart.total().toInt().toString())
            body.put("phone",codeString+phone.text.toString())
            val con_body= StringEntity(body.toString())
            client.post(this,"http://jdilemmax.pythonanywhere.com/mpesa_payment",con_body,
                "application/json",

                object : JsonHttpResponseHandler() {

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        println("Printing after accessing On success")
                        if (statusCode == 200){
                            Toast.makeText(applicationContext,"Please Confirm Order Through Mpesa Pin",
                                Toast.LENGTH_LONG).show()
                            progressbar.visibility=View.GONE
                        }
                        else{
                            Toast.makeText(applicationContext,"Mpesa Payment Not Successful $statusCode",
                                Toast.LENGTH_LONG).show()
                            progressbar.visibility=View.GONE
                        }
                        //super.onSuccess(statusCode, headers, response)
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse: JSONObject?
                    ) {
                        println("On failure")
                        Toast.makeText(applicationContext,"Something Went Wrong $statusCode", Toast.LENGTH_LONG).show()
                        progressbar.visibility=View.GONE
                    }

                }
            )//End Post
        }//End Handler
    }
}