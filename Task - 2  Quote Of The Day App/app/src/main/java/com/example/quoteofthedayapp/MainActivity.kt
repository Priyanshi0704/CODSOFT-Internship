package com.example.quoteofthedayapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    lateinit var dialog: Dialog

    private var quoteOfTheDay = mutableListOf(
        "“Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.”\n― Albert Einstein\n" +
                "“A room without books is like a body without a soul.”\n― Marcus Tullius Cicero",
        "“Be who you are and say what you feel, because those who mind don't matter, and those who matter don't mind.”\n― Bernard M. Baruch",
        "“You've gotta dance like there's nobody watching,\nLove like you'll never be hurt,\nSing like there's nobody listening,\nAnd live like it's heaven on earth.”\n― William W. Purkey",
        "“If you want to know what a man's like, take a good look at how he treats his inferiors, not his equals.”\n― J.K. Rowling, Harry Potter and the Goblet of Fire",
        "“It is better to be hated for what you are than to be loved for what you are not.”\n― Andre Gide, Autumn Leaves",
        "“Live as if you were to die tomorrow. Learn as if you were to live forever.”\n― Mahatma Gandhi",
        "“Twenty years from now you will be more disappointed by the things that you didn't do than by the ones you did do. So throw off the bowlines. Sail away from the safe harbor. Catch the trade winds in your sails. Explore. Dream. Discover.”\n― H. Jackson Brown Jr., P.S. I Love You",
        "“We accept the love we think we deserve.”\n― Stephen Chbosky, The Perks of Being a Wallflower",
        "“You know you're in love when you can't fall asleep because reality is finally better than your dreams.”\n― Dr. Seuss"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentQuote = findViewById<TextView>(R.id.textQQ)
        val shareBtn = findViewById<ImageView>(R.id.share)
        val addBtn = findViewById<ImageView>(R.id.addIV)
        val refreshBtn = findViewById<ImageView>(R.id.refresh)

        // Show random quote
        setRandomQuote(currentQuote)

        refreshBtn.setOnClickListener {
            setRandomQuote(currentQuote)
        }

        shareBtn.setOnClickListener {
            val quote = currentQuote.text.toString()
            if (quote.isNotEmpty()) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, quote)
                }
                startActivity(Intent.createChooser(shareIntent, "Share via"))
            } else {
                Toast.makeText(this, "No Quote to Share", Toast.LENGTH_SHORT).show()
            }
        }

        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogue)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.bg_alertbox))
        val addButton = dialog.findViewById<Button>(R.id.add_Button)
        val cancelButton = dialog.findViewById<Button>(R.id.cancel_button)
        val quoteInput = dialog.findViewById<TextInputEditText>(R.id.AddQuotTT)

        addButton.setOnClickListener {
            val newQuote = quoteInput.text.toString().trim()
            if (newQuote.isNotEmpty()) {
                quoteOfTheDay.add(newQuote)
                currentQuote.text = newQuote
                Toast.makeText(this, "Quote added!", Toast.LENGTH_SHORT).show()
                quoteInput.text?.clear()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please enter a quote", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            dialog.show()
        }
    }

    private fun setRandomQuote(textView: TextView) {
        if (quoteOfTheDay.isNotEmpty()) {
            val index = Random.nextInt(quoteOfTheDay.size)
            textView.text = quoteOfTheDay[index]
        }
    }
}
