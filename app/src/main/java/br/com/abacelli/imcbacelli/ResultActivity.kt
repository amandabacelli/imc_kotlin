package br.com.abacelli.imcbacelli

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val imc = intent.getFloatExtra("IMC", 0f)

        val data = getSharedPreferences(
            "calculo-$imc",
            Context.MODE_PRIVATE
        )

        val resultClass = data.getString("CLASSIFICATION", "")
        val resultIMC = data.getFloat("IMC", 0f)

        txvResultIMC.text = resultIMC.toString()
        txvResultClass.text = resultClass

        btnVoltar.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }


    }

}