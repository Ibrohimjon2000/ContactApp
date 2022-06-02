package uz.mobiler.lesson54contact

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
    private var bol = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            list = ArrayList(appDatabase.contactDao().getAllStudents())
            contactAdapter = ContactAdapter(
                lottie,
                appDatabase,
                this@MainActivity,
                list,
                object : ContactAdapter.OnItemClickListener {
                    override fun onItemClick(contact: Contact, position: Int) {
                        val intent = Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:" + Uri.encode(contact.phoneNumber))
                        )
                        startActivity(intent)
                    }
                })
            rv.adapter = contactAdapter
            contactAdapter.notifyDataSetChanged()
            if (list.isNotEmpty()) {
                lottie.visibility = View.INVISIBLE
            } else {
                lottie.visibility = View.VISIBLE
            }
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
                        list.add(contact)
                        if (list.isNotEmpty()) {
                            lottie.visibility = View.INVISIBLE
                        } else {
                            lottie.visibility = View.VISIBLE
                        }
                        appDatabase.contactDao().addContact(contact)
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
        }
    }
}