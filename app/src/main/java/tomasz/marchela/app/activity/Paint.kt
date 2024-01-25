package tomasz.marchela.app.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import tomasz.marchela.app.R
import java.util.*
import kotlin.random.Random.Default.nextInt

class Paint : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Paint";
        setContentView(R.layout.activity_paint)
        var drawing_view = findViewById<com.mihir.drawingcanvas.drawingView>(R.id.drawing_view)

        val btn_undo = findViewById<ImageButton>(R.id.btn_undo)
        btn_undo.setOnClickListener {
            drawing_view.undo()
        }
        val btn_redo = findViewById<ImageButton>(R.id.btn_redo)
        btn_redo.setOnClickListener {
            drawing_view.redo()
        }
        val btn_color = findViewById<ImageButton>(R.id.btn_color)
        btn_color.setOnClickListener {
            drawing_view.setBrushColor(Color.argb(255, nextInt(256), nextInt(256), nextInt(256)))
        }
        val btn_brush = findViewById<ImageButton>(R.id.btn_brush)
        btn_brush.setOnClickListener {
            drawing_view.setSizeForBrush(nextInt( 36))//0-35
            drawing_view.setBrushAlpha(nextInt(256)) //0-255
        }
        val btn_clearscreen = findViewById<ImageButton>(R.id.btn_clearscreen)
        btn_clearscreen.setOnClickListener {
            drawing_view.clearDrawingBoard()
        }
        val buttonTicTacToe = findViewById<Button>(R.id.buttonPaintTicTacToe)
        buttonTicTacToe.setOnClickListener{
            val intent = Intent(this, TicTacToe::class.java)
            startActivity(intent)
        }
        val buttonBible = findViewById<Button>(R.id.buttonPaintBible)
        buttonBible.setOnClickListener{
            val intent = Intent(this, BibleQuotes::class.java)
            startActivity(intent)
        }
    }
}