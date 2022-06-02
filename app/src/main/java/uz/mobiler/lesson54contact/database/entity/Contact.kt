package uz.mobiler.lesson54contact.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    val email:String
)