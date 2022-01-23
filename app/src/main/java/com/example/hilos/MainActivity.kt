package com.example.hilos

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var entrada:EditText
    lateinit var salida:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calcular:Button = findViewById(R.id.calcular)
        calcular.setOnClickListener { calcularOperacion() }
    }

    fun calcularOperacion(){
        entrada = findViewById<EditText>(R.id.entrada)
        val n:Int = entrada.text.toString().toInt()
        //salida.append("$n! = ")

        //hilo
        //MiThread(n).start()

        //AsynTask
        /*val asyncTask = MiTareaAsyncTask()
        asyncTask.execute(n)*/

        //AsyncTask con cuadro de progreso
        val asyncTaskCuadro = MiTareaAsyncConProgreso()
        asyncTaskCuadro.execute(n)

        //Este es el codigo que ejecutan el AsynTask y el Hilo
        /*val res:Int = factorial(n)
        salida.append("$res \n")*/
    }

    fun factorial(n:Int):Int{
        var res:Int=1

        for (i in 1..n){
            res *= i
            SystemClock.sleep(1000)
        }
        return res
    }

    //Hilo nuevo que ejecuta el metodo factorial()
    inner class MiThread(n:Int) : Thread() {
        var res = 1
        val salida = findViewById<TextView>(R.id.salida)
        override fun run() {
            val entrada = findViewById<EditText>(R.id.entrada)
            val n = entrada.text.toString().toInt()
            res = factorial(n)
            salida.append("$res \n")

            //Codigo que indica al sistema que ejecute parte de nuestro codigo en el hilo principal
            /*
            runOnUiThread(Runnable() {
                fun run(){
                salida.append("$res \n")}
            })
            */
        }
    }

    //AsyncTask
    inner class MiTareaAsyncTask: AsyncTask<Int, Unit, Int>() {
        val salida = findViewById<TextView>(R.id.salida)

        override fun doInBackground(vararg n: Int?): Int {
            return factorial(n[0]!!)
        }

        override fun onPostExecute(res:Int) {
            val n = entrada.text.toString().toInt()
            val res = factorial(n)
            salida.append("$n! = $res\n")
        }
    }

    //AsyncTask con cuadro de progreso
    @SuppressLint("StaticFieldLeak")
     inner class MiTareaAsyncConProgreso : AsyncTask<Int?, Int?, Int>() {
        lateinit var progreso: ProgressDialog

        override fun onPreExecute() {
            progreso = ProgressDialog(this@MainActivity)
            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progreso.setMessage("Calculando...")
            progreso.setCancelable(false)
            /*progreso.setOnCancelListener(DialogInterface.OnCancelListener() {
                fun onCancel(dialog:DialogInterface){
                    MiTareaAsyncConProgreso().cancel(true)
                }
            })*/
            progreso.max = 100
            progreso.progress = 0
            progreso.show()
        }

        override fun doInBackground(vararg n: Int?): Int? {
            var res:Int = 1

            //if (!progreso.equals(isCancelled)){
                for (i in 1..n[0]!!) {
                    res *= i
                    SystemClock.sleep(1000)
                    publishProgress(i * 100 / n[0]!!)
                }
                return res
        /*    } else { MiTareaAsyncConProgreso().cancel(true) }
        return null*/
        }

        override fun onProgressUpdate(vararg porc: Int?) {
            progreso.progress = porc[0]!!
        }

        override fun onPostExecute(res: Int) {
            val n = entrada.text.toString().toInt()
            val salida = findViewById<TextView>(R.id.salida)
            progreso.dismiss()
            salida.append(" $n! = $res\n")
        }

        /*override fun onCancelled() {
            val salida = findViewById<TextView>(R.id.salida)
            salida.append("cancelado \n")
        }*/
    }
}