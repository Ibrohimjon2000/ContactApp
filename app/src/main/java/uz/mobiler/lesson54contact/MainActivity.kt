package uz.mobiler.lesson54contact

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uz.mobiler.lesson54contact.adapter.ContactAdapter
import uz.mobiler.lesson54contact.database.AppDatabase
import uz.mobiler.lesson54contact.database.entity.Contact
import uz.mobiler.lesson54contact.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val appDatabase: AppDatabase by lazy {

        AppDatabase.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var list: ArrayList<Contact>
    private lateinit var contactAdapter: ContactAdapter
    private var bol=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            list = ArrayList(appDatabase.contactDao().getAllStudents())
            contactAdapter = ContactAdapter(list)
            rv.adapter = contactAdapter
            add.setOnClickListener {
                val customDialog = layoutInflater.inflate(R.layout.add_contact_dialog, null)
                val mBuilder = AlertDialog.Builder(this@MainActivity).setView(customDialog)
                mBuilder.setCancelable(false)
                val mAlertDialog = mBuilder.show()
                val name = customDialog.findViewById<EditText>(R.id.name)
                val number = customDialog.findViewById<EditText>(R.id.number)
                val email = customDialog.findViewById<EditText>(R.id.email)
                mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                customDialog.findViewById<TextView>(R.id.save).setOnClickListener {
                    if (name.text.toString().isNotEmpty() && number.text.toString()
                            .isNotEmpty() && email.text.toString().isNotEmpty()
                    ) {
                        val nameTv = name.text.toString()
                        val numberTv = number.text.toString()
                        val emailTv = email.text.toString()
                        val contact =
                            Contact(name = nameTv, phoneNumber = numberTv, email = emailTv)
                        appDatabase.contactDao().addContact(contact)
                        list.add(contact)
                        contactAdapter.notifyItemInserted(list.size)
                        mAlertDialog.dismiss()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Ma'lumotlar to'liq kiritilmagan!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                customDialog.findViewById<TextView>(R.id.exit).setOnClickListener {
                    mAlertDialog.cancel()
                }
            }
            searchIcon.setOnClickListener {
                if (bol){
                    kontakt.visibility= View.INVISIBLE
                    add.visibility= View.INVISIBLE
                    search.visibility= View.VISIBLE
                    searchIcon.setImageResource(R.drawable.ic_baseline_clear_24)
                    bol=false
                }else{
                    search.setText("")
                    kontakt.visibility= View.VISIBLE
                    add.visibility= View.VISIBLE
                    search.visibility= View.INVISIBLE
                    searchIcon.setImageResource(R.drawable.ic_baseline_search_24)
                    bol=true
                }
            }
            search.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    filter(p0.toString())
                }

            })
        }
    }

    private fun filter(text: String) {
        val filteredList = java.util.ArrayList<Contact>()
        for (value in list) {
            if (value.name.lowercase().contains(text.lowercase())) {
                filteredList.add(value)
            }
        }
        contactAdapter.filterList(filteredList)
    }
}