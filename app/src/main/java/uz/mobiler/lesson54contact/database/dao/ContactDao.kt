package uz.mobiler.lesson54contact.database.dao

import androidx.room.*
import uz.mobiler.lesson54contact.database.entity.Contact

@Dao
interface ContactDao {
    @Query("select * from contact")
    fun getAllStudents():List<Contact>

    @Insert
    fun addContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Update
    fun editContact(contact: Contact)

    @Query("select * from contact where id=:id")
    fun getContactById(id:Int):Contact

    @Query("select * from contact where name like '%' || :word || '%'")
    fun getContactsByName(word:String):List<Contact>
}