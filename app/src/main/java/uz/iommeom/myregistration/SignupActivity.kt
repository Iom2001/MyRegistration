package uz.iommeom.myregistration

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_signup.*
import uz.iommeom.mycontact.utils.MySharedPreferences
import uz.iommeom.myregistration.models.User
import com.google.gson.reflect.TypeToken

class SignupActivity : AppCompatActivity() {

    lateinit var gson: Gson
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        MySharedPreferences.init(this)
        gson = Gson()

        next.setOnClickListener {
            val filt = number_email.text.toString()
            val password = password_edit.text.toString()
            if (filt.isNotEmpty() && password.isNotEmpty()) {
                var user = User(filt, password)
                val userData =MySharedPreferences.getUser(this)
                val type = object : TypeToken<ArrayList<User>>() {}.type
                gson = Gson()
                var userList = gson.fromJson<ArrayList<User>>(userData, type)
                if (userList == null) userList = ArrayList()
                userList.add(user)
                val toJson = gson.toJson(userList)
                MySharedPreferences.setUser(toJson)
                var intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("user", user)
                setResult(RESULT_OK,intent)
                finish()
            } else {
                Toast.makeText(this, "Fill the blank!!!", Toast.LENGTH_SHORT).show()
            }
        }

        go_log_in.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        go_number.setOnClickListener {
            country.visibility = View.VISIBLE
            line_design.visibility = View.VISIBLE
            go_email_text.setTextColor(R.color.black_line)
            line_email.setBackgroundColor(R.color.black_line)
            go_number_text.setTextColor(Color.BLACK)
            line_number.setBackgroundColor(Color.BLACK)
            number_email.hint = ""
            number_email.setText("")
        }

        go_email.setOnClickListener {
            country.visibility = View.GONE
            line_design.visibility = View.GONE
            go_email_text.setTextColor(Color.BLACK)
            line_email.setBackgroundColor(Color.BLACK)
            go_number_text.setTextColor(R.color.black_line)
            line_number.setBackgroundColor(R.color.black_line)
            number_email.hint = "Email"
            number_email.setText("")
        }
    }
}