package mx.edu.ittepic.tpdm_u2_practica3



import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    var n : EditText?=null
    var m : EditText?=null
    var boton : Button?=null
    var etiqueta : TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        n = findViewById(R.id.numerosN)
        m = findViewById(R.id.numerosM)
        boton = findViewById(R.id.boton)
        etiqueta = findViewById(R.id.primos)

        boton?.setOnClickListener{
            etiqueta?.setText("")
            GenerarNumeros(n?.text.toString(), m?.text.toString(),applicationContext).execute()
            val abrirArchivo = BufferedReader(InputStreamReader(openFileInput("primos.txt")))
            var cadena = ""
            cadena = abrirArchivo.readLine()
            etiqueta?.setText(cadena)
        }

    }

    class GenerarNumeros(n: String, m: String, context: Context) : AsyncTask<Void, Void, List<Int>>(){
        var n = n.toInt()
        var m = m.toInt()
        var c = context

        override fun doInBackground(vararg p0: Void?): List<Int>? {
            val gen = List(2000){ Random.nextInt(n,m)}
            println(gen)

            return gen
        }

        override fun onPostExecute(result: List<Int>?) {
            super.onPostExecute(result)
            var cont = 0
            var et=""
            var numerosPrimos="Números primos:  "
            (0..1999).forEach {
                cont=0
                et=result?.get(it).toString()
                (1..et.toInt()).forEach {
                    if (et.toInt() % it == 0) {
                        cont++
                    }
                }
                if (cont <= 2 && et.toInt()>1) {
                    numerosPrimos=numerosPrimos+et+", "
                }
            }
            println(numerosPrimos)
            guardar(numerosPrimos)

        }

        fun guardar(numerosPrimos:String){
            val guardarArchivo = OutputStreamWriter(c.openFileOutput("primos.txt", Activity.MODE_PRIVATE)) //construye un archivo en memoria interna o externa, output para guardar
            guardarArchivo.write(numerosPrimos)
            guardarArchivo.flush()
            guardarArchivo.close()
            Toast.makeText(c,"¡Números generados",Toast.LENGTH_LONG).show()
        }
    }


}
