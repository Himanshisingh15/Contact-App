package com.example.contactapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapplication.databinding.ListItemBinding
import com.example.contactapplication.databinding.UpdateCustomDialogBinding

class MyRecyclerViewAdapter( var context: MainActivity, var list: ArrayList<UserData>,  var setOnItmeChanged: SetOnItmeChanged, var requstPermissions : requstPermissions) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    var rowId :Int = 0
    var fName : String = ""
    var lName : String = ""
    var mobileNo : String = ""
    var email : String = ""
    lateinit var mobile: String

    var dbAdapter :DBAdapter = DBAdapter(context)

    inner class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var person = list[position]
//        holder.binding.image.setImageResource(person.imageId)
        holder.binding.tvName.text = "${person.fName} ${person.lName}"
        holder.binding.tvMobile.text = "+91 ${person.mobileNo}"


        holder.binding.tvCall.setOnClickListener{
            requstPermissions.onCall(person.mobileNo)
        }

        holder.binding.tvMenu.setOnClickListener(object : View.OnClickListener,
            PopupMenu.OnMenuItemClickListener {
            override fun onClick(p0: View?) {
                rowId = person.id

                var popUpMenu = PopupMenu(context,p0)
                popUpMenu.menuInflater.inflate(R.menu.context_menu,popUpMenu.menu)
                popUpMenu.show()
                popUpMenu.setOnMenuItemClickListener(this)
            }

            override fun onMenuItemClick(p0: MenuItem?): Boolean {
                when(p0!!.itemId) {
                    R.id.item_delete -> {
                        dbAdapter = DBAdapter(context)
                        dbAdapter.deleteSingleRow(rowId)
                        setOnItmeChanged.onItemChange()
                    }
                    R.id.item_update ->{
//                        Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show()
                        var udBinding : UpdateCustomDialogBinding = UpdateCustomDialogBinding.inflate(context.layoutInflater)
                        var udialog = Dialog(context)
                        udialog.setContentView(udBinding.root)
                        udialog.setCancelable(false)

                        var layoutManager = WindowManager.LayoutParams()
                        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT
                        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
                        udialog.window!!.attributes = layoutManager
                        udialog.show()

                        fName = person.fName
                        lName = person.lName
                        mobileNo = person.mobileNo
                        email = person.email

                        udBinding.etFname.setText(fName)
                        udBinding.etLname.setText(lName)
                        udBinding.etMobile.setText(mobileNo)
                        udBinding.etEmail.setText(email)
//                        dbAdapter.updateData(fName, lName, mobileNo, email)

                        udBinding.btnUpdate.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(view: View?) {
                            dbAdapter.updateData(rowId,
                                udBinding.tilFname.editText!!.text.toString(),
                                udBinding.tilLname.editText!!.text.toString(),
                                udBinding.tilMobile.editText!!.text.toString(),udBinding.tilEmail.editText!!.text.toString())

                                setOnItmeChanged.onItemChange()
                            udialog.dismiss()
                        } })

                        udBinding.iconCancel.setOnClickListener {
                            udialog.dismiss()
                        }
                    }
                }
                return false
            }
        })
    }



//    fun setDataToList(){
//        myAdapter = MyRecyclerViewAdapter(context,dbAdapter.fetchData())
//        binding.recyclerListItem.adapter = myAdapter
//    }

// ek listner interface banaya tha kya iske liye
    // haa tumne hi bnaya tha


}