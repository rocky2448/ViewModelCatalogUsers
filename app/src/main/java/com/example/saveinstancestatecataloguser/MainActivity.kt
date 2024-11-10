package com.example.saveinstancestatecataloguser

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var toolbarMain: Toolbar
    private lateinit var nameET: EditText
    private lateinit var ageET: EditText
    private lateinit var saveBTN: Button
    private lateinit var listUsersLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Каталог пользователей"
        toolbarMain.subtitle = "by Rocky"
        toolbarMain.setLogo(R.drawable.ic_toolbar_logo_app)

        nameET = findViewById(R.id.nameET)
        ageET = findViewById(R.id.ageET)
        saveBTN = findViewById(R.id.saveBTN)
        listUsersLV = findViewById(R.id.listUsersLV)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userViewModel.users)
        listUsersLV.adapter = adapter

        userViewModel.currentUser.observe(this, Observer { it ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
            adapter.notifyDataSetChanged()
        })

        saveBTN.setOnClickListener {
            if (nameET.text.isEmpty() ||
                ageET.text.isEmpty() ||
                ageET.text.toString().toInt() < 0 ||
                ageET.text.toString().toInt() > 200) {
                Toast.makeText(this, "Данные введены некорректно!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            userViewModel.addUser(User(nameET.text.toString(), ageET.text.toString().toInt()))
            adapter.notifyDataSetChanged()
            nameET.text.clear()
            ageET.text.clear()

        }

        listUsersLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val user = adapter.getItem(position)
                adapter.remove(user)
                Toast.makeText(this, "Пользователь $user удалён", Toast.LENGTH_LONG).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> {
                finish()
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}