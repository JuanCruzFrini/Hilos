package com.example.hilos

/*
internal class MiTarea : AsyncTask<Int?, Int?, Int>() {
    private var progreso: ProgressDialog? = null
    override fun onPreExecute() {
        progreso = ProgressDialog(this@MainActivity)
        progreso!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progreso!!.setMessage("Calculando...")
        progreso!!.setCancelable(false)
        progreso!!.max = 100
        progreso!!.progress = 0
        progreso!!.show()
    }

    protected override fun doInBackground(vararg n: Int): Int {
        var res = 1
        for (i in 1..n[0]) {
            res *= i
            SystemClock.sleep(1000)
            publishProgress(i * 100 / n[0])
        }
        return res
    }

    protected override fun onProgressUpdate(vararg porc: Int) {
        progreso!!.progress = porc[0]
    }

    override fun onPostExecute(res: Int) {
        progreso!!.dismiss()
        salida.append("""
    $res
    
    """.trimIndent())
    }
}*/
