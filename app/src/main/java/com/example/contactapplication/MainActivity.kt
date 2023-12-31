package com.example.contactapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapplication.databinding.ActivityMainBinding
import com.example.contactapplication.databinding.CustomDialogBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, SetOnItmeChanged, requstPermissions{

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbAdapter: DBAdapter
    private lateinit var myAdapter: MyRecyclerViewAdapter
    private lateinit var Mobile :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbAdapter = DBAdapter(this)
        binding.addData.setOnClickListener(this)
        binding.recyclerListItem.layoutManager=LinearLayoutManager(this)
        setData()

    }

    override fun onClick(view: View?) {
        var dialogBinding :CustomDialogBinding = CustomDialogBinding.inflate(layoutInflater)
        var dialog = Dialog(this@MainActivity)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)

        var layoutManager = WindowManager.LayoutParams()
        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = layoutManager
        dialog.show()

        dialogBinding.btnSave.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {

                if (dialogBinding.etFname.text!!.isEmpty()) {
                    dialogBinding.etFname.error = "*This field is required"
                }
                if (dialogBinding.etLname.text!!.isEmpty()) {
                    dialogBinding.etLname.error = "*This field is required"
                }
                if (dialogBinding.etMobile.text!!.isEmpty()) {
                    dialogBinding.etMobile.error = "*This field is required"
                }
//                if (dialogBinding.etEmail.text!!.isEmpty()) {
//                    dialogBinding.etEmail.error = "*This field is required"
//                }
                if (dialogBinding.etFname.text!!.isNotEmpty() && dialogBinding.etLname.text!!.isNotEmpty() && dialogBinding.etMobile.text!!.isNotEmpty()) {

                    if (dialogBinding.etMobile.text.toString().length != 10) {
                        dialogBinding.etMobile.error = "Please fill valid mobile No."
                    }
                    if (dialogBinding.etEmail.text.toString().contains("@")) {
                        if (dialogBinding.etMobile.text.toString()
                                .startsWith("9") || dialogBinding.etMobile.text.toString()
                                .startsWith("8") || dialogBinding.etMobile.text.toString()
                                .startsWith("7") || dialogBinding.etMobile.text.toString().startsWith("6")
                        ) {
                            if (dialogBinding.etMobile.text.toString().length == 10) {

                                dbAdapter.createData(dialogBinding.tilFname.editText!!.text.toString(), dialogBinding.tilLname.editText!!.text.toString(), dialogBinding.tilEmail.editText!!.text.toString(), dialogBinding.tilMobile.editText!!.text.toString(),)
                                dialog.dismiss()
                                setData()

                            }
                        } else {
                            dialogBinding.etMobile.error = "Invalid input"
                        }
                    }
                }
            }
        })
        dialogBinding.iconCancel.setOnClickListener{
            dialog.dismiss()
        }
    }

    override fun onBackPressed() {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Exit")
        dialog.setMessage("Do you want to exit")
        dialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                finishAffinity()
                Toast.makeText(this@MainActivity, "i am positive", Toast.LENGTH_SHORT).show()
            }
        })

        dialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Toast.makeText(this@MainActivity, "Don't Exit", Toast.LENGTH_SHORT).show()
            }
        })

        dialog.setCancelable(false)
        var alertdialog = dialog.create()
        alertdialog.show()
    }

    override fun onItemChange() {
        super.onItemChange()
        setData()
    }

    fun setData(){

        myAdapter=MyRecyclerViewAdapter(this@MainActivity,dbAdapter.fetchData(),this, object : requstPermissions{
            override fun onCall(mobile: String) {
                checkPermission(mobile)
                Mobile = mobile
            }
        })
        binding.recyclerListItem.adapter = myAdapter

    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if ( requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
       makeCall(Mobile)
    } else
    {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CALL_PHONE), 101)
    }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        dbAdapter.deleteAllData()
        setData()
        return super.onOptionsItemSelected(item)
    }

    fun checkPermission(mobile: String){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            makeCall(mobile)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 101)
        }
    }

    fun  makeCall(mobile:String){

        var intentCall = Intent(Intent.ACTION_CALL)
        intentCall.data = Uri.parse("tel:$mobile")
        startActivity(intentCall)

    }

}

