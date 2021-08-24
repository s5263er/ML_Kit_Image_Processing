 package com.example.ml_kit_image_processing

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface.LATIN_AND_CHINESE
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.activity_main.*

 class MainActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         button_selectphoto.setOnClickListener{
             val intent = Intent(Intent.ACTION_PICK)
             intent.type = "image/*"
             startActivityForResult(intent, 0)
             recognize_Text()
         }
    }
     private fun recognize_Text(){
         val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

         val inputImg: InputImage = InputImage.fromBitmap(bitmap!!,0)
         val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)


         recognizer.process(inputImg).addOnSuccessListener {
             progressBar.visibility = View.GONE
             text_view_fromimg.text = it.text
         }


     }
     var selectedPhotoUri: Uri? = null
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
             selectedPhotoUri = data.data

             val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

             img_view.setImageBitmap(bitmap)
         }

     }
}