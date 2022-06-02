package uz.mobiler.lesson54contact.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.lesson54contact.database.entity.Contact
import uz.mobiler.lesson54contact.databinding.ItemContactBinding
import kotlin.random.Random

class ContactAdapter(var list: List<Contact>) : RecyclerView.Adapter<ContactAdapter.Vh>() {
    inner class Vh(var itemContactBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root) {
        fun onBind(student: Contact, position: Int) {
            val list= arrayOf("#1F2936","#3311FF","#FF00FF","#00FF00","#FFE4B5","#FF0000","#CD5C5C","#7CFC00","#00CED1","#FF018786")
            itemContactBinding.apply {
                nameTv.text = student.name
                if (student.name.length>=2) {
                    if (student.name.substring(0, 2) == "Sh" || student.name.substring(
                            0,
                            2
                        ) == "Ch"
                    ) {
                        iconName.text = student.name.substring(0, 2)
                    } else iconName.text = student.name.substring(0, 1)
                }else iconName.text = student.name.substring(0, 1)
                val random= Random
                val index=random.nextInt(10)
                cardView.setCardBackgroundColor(Color.parseColor(list[index]))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun filterList(filteredList: List<Contact>) {
        list= filteredList
        notifyDataSetChanged()
    }
}