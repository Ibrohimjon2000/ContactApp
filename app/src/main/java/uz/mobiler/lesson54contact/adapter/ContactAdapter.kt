package uz.mobiler.lesson54contact.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.lesson54contact.R
import uz.mobiler.lesson54contact.database.AppDatabase
import uz.mobiler.lesson54contact.database.entity.Contact
import uz.mobiler.lesson54contact.databinding.ItemContactBinding

class ContactAdapter(
    val appDatabase: AppDatabase,
    val c: Context,
    var list: ArrayList<Contact>,
    val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ContactAdapter.Vh>() {
    inner class Vh(var itemContactBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root) {
        fun onBind(contact: Contact, position: Int) {
            val list = arrayOf(
                "#1F2936",
                "#3311FF",
                "#FF00FF",
                "#00FF00",
                "#FFE4B5",
                "#FF0000",
                "#CD5C5C",
                "#7CFC00",
                "#00CED1",
                "#FF018786"
            )
            itemContactBinding.apply {
                menu.setOnClickListener { popupMenus(it, contact, position) }
                nameTv.text = contact.name
                phoneTv.text = contact.phoneNumber
                if (contact.name.length >= 2) {
                    if (contact.name.substring(0, 2) == "Sh" || contact.name.substring(
                            0,
                            2
                        ) == "Ch"
                    ) {
                        iconName.text = contact.name.substring(0, 2)
                    } else iconName.text = contact.name.substring(0, 1)
                } else iconName.text = contact.name.substring(0, 1)
                if (contact.name.isNotEmpty()) {
                    when (contact.name[0]) {
                        'A', 'a' -> cardView.setCardBackgroundColor(Color.parseColor(list[0]))
                        'B', 'b' -> cardView.setCardBackgroundColor(Color.parseColor(list[1]))
                        'C', 'c' -> cardView.setCardBackgroundColor(Color.parseColor(list[2]))
                        'D', 'd' -> cardView.setCardBackgroundColor(Color.parseColor(list[3]))
                        'E', 'e' -> cardView.setCardBackgroundColor(Color.parseColor(list[4]))
                        'F', 'f' -> cardView.setCardBackgroundColor(Color.parseColor(list[5]))
                        'G', 'g' -> cardView.setCardBackgroundColor(Color.parseColor(list[6]))
                        'H', 'h' -> cardView.setCardBackgroundColor(Color.parseColor(list[7]))
                        'I', 'i' -> cardView.setCardBackgroundColor(Color.parseColor(list[8]))
                        'J', 'j' -> cardView.setCardBackgroundColor(Color.parseColor(list[9]))
                        'K', 'k' -> cardView.setCardBackgroundColor(Color.parseColor(list[0]))
                        'L', 'l' -> cardView.setCardBackgroundColor(Color.parseColor(list[1]))
                        'M', 'm' -> cardView.setCardBackgroundColor(Color.parseColor(list[2]))
                        'N', 'n' -> cardView.setCardBackgroundColor(Color.parseColor(list[3]))
                        'O', 'o' -> cardView.setCardBackgroundColor(Color.parseColor(list[4]))
                        'P', 'p' -> cardView.setCardBackgroundColor(Color.parseColor(list[5]))
                        'Q', 'q' -> cardView.setCardBackgroundColor(Color.parseColor(list[6]))
                        'R', 'r' -> cardView.setCardBackgroundColor(Color.parseColor(list[7]))
                        'S', 's' -> cardView.setCardBackgroundColor(Color.parseColor(list[8]))
                        'T', 't' -> cardView.setCardBackgroundColor(Color.parseColor(list[9]))
                        'U', 'u' -> cardView.setCardBackgroundColor(Color.parseColor(list[0]))
                        'V', 'v' -> cardView.setCardBackgroundColor(Color.parseColor(list[1]))
                        'W', 'w' -> cardView.setCardBackgroundColor(Color.parseColor(list[2]))
                        'X', 'x' -> cardView.setCardBackgroundColor(Color.parseColor(list[3]))
                        'Y', 'y' -> cardView.setCardBackgroundColor(Color.parseColor(list[4]))
                        'Z', 'z' -> cardView.setCardBackgroundColor(Color.parseColor(list[5]))
                        '0' -> cardView.setCardBackgroundColor(Color.parseColor(list[6]))
                        '1' -> cardView.setCardBackgroundColor(Color.parseColor(list[7]))
                        '2' -> cardView.setCardBackgroundColor(Color.parseColor(list[8]))
                        '3' -> cardView.setCardBackgroundColor(Color.parseColor(list[9]))
                        '4' -> cardView.setCardBackgroundColor(Color.parseColor(list[0]))
                        '5' -> cardView.setCardBackgroundColor(Color.parseColor(list[1]))
                        '6' -> cardView.setCardBackgroundColor(Color.parseColor(list[2]))
                        '7' -> cardView.setCardBackgroundColor(Color.parseColor(list[3]))
                        '8' -> cardView.setCardBackgroundColor(Color.parseColor(list[4]))
                        '9' -> cardView.setCardBackgroundColor(Color.parseColor(list[5]))
                    }
                }
            }
        }

        private fun popupMenus(v: View?, contact: Contact, position: Int) {
            val popupMenus = PopupMenu(c, v)
            popupMenus.inflate(R.menu.popup_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.edit_contact_dialog, null)
                        val mBuilder = AlertDialog.Builder(c).setView(v)
                        val name1 = v.findViewById<TextView>(R.id.name)
                        val number1 = v.findViewById<TextView>(R.id.number)
                        val email1 = v.findViewById<TextView>(R.id.email)
                        mBuilder.setCancelable(false)
                        val mAlterDialog = mBuilder.show()
                        mAlterDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                        name1.text = contact.name
                        number1.text = contact.phoneNumber
                        email1.text = contact.email
                        v.findViewById<TextView>(R.id.save).setOnClickListener {
                            val name = name1.text.toString()
                            val number = number1.text.toString()
                            val email = email1.text.toString()
                            val c = Contact(
                                id = contact.id,
                                name = name,
                                phoneNumber = number,
                                email = email
                            )
                            appDatabase.contactDao().editContact(c)
                            list[position] = c
                            notifyDataSetChanged()
                            mAlterDialog.dismiss()
                        }
                        v.findViewById<TextView>(R.id.exit).setOnClickListener {
                            mAlterDialog.dismiss()
                        }
                        true
                    }
                    R.id.delete -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.delete_contact_dialog, null)
                        val mBuilder = AlertDialog.Builder(c).setView(v)
                        mBuilder.setCancelable(false)
                        val mAlterDialog = mBuilder.show()
                        mAlterDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                        v.findViewById<TextView>(R.id.save).setOnClickListener {
                            appDatabase.contactDao().deleteContact(contact)
                            list.remove(contact)
                            notifyDataSetChanged()
                            mAlterDialog.dismiss()
                        }
                        v.findViewById<TextView>(R.id.exit).setOnClickListener {
                            mAlterDialog.dismiss()
                        }
                        true
                    }
                    else -> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
        holder.itemContactBinding.call.setOnClickListener {
            listener.onItemClick(
                list[position],
                position
            )
        }
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onItemClick(contact: Contact, position: Int)
    }
}