package tomasz.marchela.app.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import com.google.gson.Gson
import tomasz.marchela.app.R
import tomasz.marchela.app.Request
import tomasz.marchela.app.databinding.ActivityBibleQuotesBinding
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import java.io.InputStreamReader

class BibleQuotes : AppCompatActivity() {
    private lateinit var binding: ActivityBibleQuotesBinding
    private var arr = arrayOf(
        "matthew+28:19",
        "2corinthians+12:9",
        "1peter+5:7",
        "hebrews+11:6",
        "isaiah+53:5",
        "john+16:33",
        "philippians+4:8",
        "john+3:16",
        "romans+3:23",
        "psalm+23"
    )
    private lateinit var detector: GestureDetectorCompat
    private var i=0;
//    private lateinit var eventFragment: EventFragment
//    private lateinit var mainFragment: MainFragment
//    private lateinit var activeFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Bible quotes";
        binding = ActivityBibleQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detector = GestureDetectorCompat(this, QuoteGestureListener())

        val buttonTicTacToe = findViewById<Button>(R.id.buttonBibleTicTacToe)
    buttonTicTacToe.setOnClickListener{
            val intent = Intent(this, TicTacToe::class.java)
            startActivity(intent)
        }


        val buttonPaint = findViewById<Button>(R.id.buttonBiblePaint)
    buttonPaint.setOnClickListener{
            val intent = Intent(this, Paint::class.java)
            startActivity(intent)
        }

        fetchQuote(arr[i]).start()
    }
    @SuppressLint("SetTextI18n")
    private fun fetchQuote(address:String): Thread{
        return Thread {
            val url = URL("https://bible-api.com/$address")
            val connection  = url.openConnection() as HttpsURLConnection

            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                binding.quote.text = "Failed Connection"
            }
        }
    }
    private fun updateUI(request: Request)
    {
        runOnUiThread {
            kotlin.run {
                binding.quote.text = request.text
            }
        }
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent(event!!)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    inner class QuoteGestureListener : GestureDetector.SimpleOnGestureListener() {

        private val  SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var diffX = e2?.x?.minus(e1!!.x) ?: 0.0F
            var diffY = e2?.y?.minus(e1!!.y) ?: 0.0F

            return if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0 ) {
                        this@BibleQuotes.onSwipeRight()
                    } else {
                        this@BibleQuotes.onLeftSwipe()
                    }
                    true
                } else  {
                    super.onFling(e1, e2, velocityX, velocityY)
                }
            }else false
        }
    }

    internal fun onLeftSwipe() {
        if(i==0)
            i=arr.size-1
        else
            i--
        this@BibleQuotes.fetchQuote(arr[i]).start()
    }

    internal fun onSwipeRight() {
        if(i==arr.size-1)
            i=0
        else
            i++
        this@BibleQuotes.fetchQuote(arr[i]).start()
    }
}
