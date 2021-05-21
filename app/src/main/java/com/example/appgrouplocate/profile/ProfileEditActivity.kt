package com.example.appgrouplocate.profile

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appgrouplocate.R
import com.example.appgrouplocate.database.*
import com.example.appgrouplocate.databinding.ActivityHomeBinding
import com.example.appgrouplocate.databinding.ActivityProfileEditBinding
import com.example.appgrouplocate.home.HomeActivity
import com.example.appgrouplocate.home.HomeViewModel
import com.example.appgrouplocate.login.LoginActivity
import com.example.appgrouplocate.main.MainActivity
import com.example.appgrouplocate.signup.SignUpActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ProfileEditActivity : Fragment() {

    private val TAG = SignUpActivity::class.java.simpleName

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var viewModel: ProfileEditViewModel

    private lateinit var url : String

    private val REQUEST_CODE_CAMERA = 123

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityProfileEditBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(ProfileEditViewModel::class.java)
        (activity as MainActivity).parseData()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            tempInfo = viewModel.name
            /*database.child("users").child(auth.currentUser!!.uid).child("avtUrl")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Picasso.get().load(loggedInAccount.avtUrl).into(avatarBt)
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })*/
            avatarBt.setOnClickListener {
                dialogChangeAvt()
            }
            applyChangesBt.setOnClickListener {
                val ref  = database.child("users").child(auth.currentUser!!.uid)
                ref.child("name").setValue(nameEt.text.toString().trim())
                ref.child("avtUrl").setValue(url)
                //parseData()
                (activity as MainActivity).loadFragment(HomeActivity())
            }
            cancelChangeBt.setOnClickListener {
                (activity as MainActivity).loadFragment(HomeActivity())
            }

        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.name = binding.nameEt.text.toString().trim()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode)
        {
            REQUEST_CODE_CAMERA -> {
                if(resultCode == RESULT_OK && data != null) run {
                    binding.avatarBt.setImageBitmap(data.extras?.get("data") as Bitmap)

                    //upload to firebase storage
                    binding.avatarBt.isDrawingCacheEnabled = true
                    binding.avatarBt.buildDrawingCache()
                    val bitmap = (binding.avatarBt.drawable as BitmapDrawable).bitmap
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)

                    val ref = storage.child("user_avt").child(auth.currentUser!!.uid + "/avt.jpg")
                    val uploadTask = ref.putBytes(baos.toByteArray())
                    uploadTask.addOnSuccessListener {
                        Toast.makeText(activity,"Upload image successful",Toast.LENGTH_SHORT).show()
                    }

                    ref.downloadUrl.addOnSuccessListener {
                        url = it.toString()
                    }


                }else{
                    Log.e(TAG,"get photo failed")
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun dialogChangeAvt() {
        val dialog = activity?.let { Dialog(it) }
        dialog!!.setContentView(R.layout.dialog_change_avatar)
        dialog.setTitle("Change avatar")
        dialog.findViewById<Button>(R.id.upload_Bt).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.capture_Bt).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,REQUEST_CODE_CAMERA)
            dialog.dismiss()
        }
        dialog.show()
    }
}