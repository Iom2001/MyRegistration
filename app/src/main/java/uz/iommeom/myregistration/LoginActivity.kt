package uz.iommeom.myregistration

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import uz.iommeom.mycontact.utils.MySharedPreferences
import uz.iommeom.myregistration.models.User

class LoginActivity : AppCompatActivity() {

    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        gson = Gson()
        MySharedPreferences.init(this)

        go_sign_up.setOnClickListener {
            startActivityForResult(Intent(this, SignupActivity::class.java), 1)
        }

        log_in.setOnClickListener {
            val filt = main.text.toString()
            val password = password.text.toString()
            if (filt.isNotEmpty() && password.isNotEmpty()) {
                val userData = MySharedPreferences.getUser(this)
                val type = object : TypeToken<ArrayList<User>>() {}.type
                var userList = gson.fromJson<ArrayList<User>>(userData, type)
                var boolean = true
                for (i in userList.indices) {
                    if (userList[i].filt == filt && userList[i].password == password) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        boolean = false
                    }
                }
                if (boolean) Toast.makeText(this, "Username or password incorrect!!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fill the blanks!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            var user = data.getSerializableExtra("user") as User
            main.setText(user.filt)
            password.setText(user.password)
        }
    }
}