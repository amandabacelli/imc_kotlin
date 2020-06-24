package br.com.abacelli.imcbacelli

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        seekPeso.max = 2000
        seekPeso.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val pesoNumber: Float = progress*10/100.toFloat()
                txvPesoNum.text = pesoNumber.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        seekAltura.max = 500
        seekAltura.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val alturaNumber: Float = progress/2.toFloat()
                txvAlturaNum.text = alturaNumber.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        btnCalcular.setOnClickListener {
            val peso = txvPesoNum.text.toString().toFloat()
            val altura = txvAlturaNum.text.toString().toFloat()

            if(peso == 0f || altura == 0f) {
                Toast.makeText(this, "Informe todos os campos", Toast.LENGTH_LONG).show()
            } else {
                val imc = (peso / (altura * altura)) * 10000

                val data = getSharedPreferences(
                    "calculo-$imc",
                    Context.MODE_PRIVATE
                )

                var resultClass = ""
                if(imc < 18.5) resultClass = "Abaixo do peso"
                if(imc > 18.5 && imc < 24.9) resultClass = "Peso normal"
                if(imc > 24.9 && imc < 29.9) resultClass = "Sobrepeso"
                if(imc > 29.9 && imc < 34.9) resultClass = "Obesidade grau 1"
                if(imc > 34.9 && imc < 39.9) resultClass = "Obesidade grau 2"
                if(imc > 39.9) resultClass = "Obesidade grau 3"
                
                val results = data.edit()
                results.putFloat("IMC", imc)
                    results.putString("CLASSIFICATION", resultClass)

                results.apply()

                val mIntent = Intent(this, ResultActivity::class.java)

                mIntent.putExtra("IMC", imc)
                mIntent.putExtra("CLASSIFICATION", resultClass)

                startActivity(mIntent)
                finishAffinity()
            }
        }
    }

}