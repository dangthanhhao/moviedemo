package com.example.moviedemo.screen.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.moviedemo.R
import com.example.moviedemo.databinding.ActivityProfileBinding

import com.example.moviedemo.screen.UserProfileViewModel
import com.example.moviedemo.screen.UserProfileViewModelFactory
import com.example.moviedemo.util.ReadFilePermisnion
import com.example.moviedemo.util.RealPathUtil
import kotlinx.android.synthetic.main.activity_profile.*
import timber.log.Timber
import java.io.File
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
const val IMAGE_PICK_INTENT=1998
class ProfileActivity : AppCompatActivity() {
    lateinit var viewModel: UserProfileViewModel
    var avatarPicked=""
    var imageBindFirstTime=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val binding=DataBindingUtil.setContentView<ActivityProfileBinding>(this,R.layout.activity_profile)
        binding.lifecycleOwner=this

        val viewModelFactory=
            UserProfileViewModelFactory(application)
        viewModel=ViewModelProviders.of(this,viewModelFactory).get(UserProfileViewModel::class.java)

        binding.viewModel= viewModel


        setUpRadioButton(binding.radioButton1,binding.radioButton2)


        viewModel.userProfile.observe(this,
            androidx.lifecycle.Observer {
            val imgFile= File(it.avatar)
            if (imgFile.exists()&&!imageBindFirstTime){
                binding.profileImage.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
                imageBindFirstTime=true
            }
        })



    }

    private fun setUpRadioButton(rad1: RadioButton, rad2: RadioButton) {
        rad1.setOnClickListener{
            if (rad2.isChecked){
                rad2.isChecked=false
            }
            rad1.isChecked=true
        }

        rad2.setOnClickListener {
            if (rad1.isChecked){
                rad1.isChecked=false
            }
            rad2.isChecked=true
        }
    }


    fun onShowDatePicker( dateOfBirthLabel: View) {
//        init current date
        val initDate=viewModel.userProfile.value!!.dateOfBirth

        val year = initDate.year+1900

        val month = initDate.month
        val day = initDate.date

        Timber.i("$day- $month - $year")
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val datePicked=Date(year-1900,monthOfYear,dayOfMonth)
            val datePickString= SimpleDateFormat("yyyy-MM-dd").format(datePicked)
            (dateOfBirthLabel as TextView).text=datePickString
            viewModel.onChangeDateOfBirth(datePicked)
        }, year, month, day)

        dpd.show()
    }

    fun onShowImagePicker(view: View) {

        val intent = Intent()
        ReadFilePermisnion.verifyStoragePermissions(this)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_INTENT)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==IMAGE_PICK_INTENT &&resultCode== Activity.RESULT_OK){
            val uri = data?.data
            avatarPicked= RealPathUtil.getRealPath(this,uri!!).toString()

//            val imgFile= File(avatarPicked)
            val imgFile= File(avatarPicked)
            if (imgFile.exists()){
                profile_image.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
            }
        }



        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onUpdateProfile(view: View) {

        viewModel.updateUserProfile(avatarPicked)
        finish()
    }

    fun bindAvatarProfileFirstTime(imageView: ImageView, path: String?){

        if (!imageBindFirstTime) return
        Timber.i("Load $path")
        path?.let {

            val imgFile= File(path)
            if (imgFile.exists()){
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
            }
            imageBindFirstTime =false
        }


    }
}