package com.example.kubuku.page

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.kubuku.R
import com.example.kubuku.databinding.ActivityEditProfileBinding
import com.example.kubuku.helper.HelperSharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var helperSharedPreferences: HelperSharedPreferences
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helperSharedPreferences = HelperSharedPreferences(this@EditProfileActivity)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        //Function Callback
        fetchData()

        with(binding) {
            //Button Logic
            btnBack.setOnClickListener {
                finish()
            }
            btnUploadImage.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_PICK
                intent.type = "image/*"
                pickImage.launch(intent)
            }
            btnSave.setOnClickListener {
                if(uri != null) {
                    updateProfile()
                    Toast.makeText(this@EditProfileActivity, "TES", Toast.LENGTH_SHORT).show()

                } else {
                    updateProfileNoImage()
                    Toast.makeText(this@EditProfileActivity, "TES tanpa gambar", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    //Function
    //Open Image Gallery
    val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if(result.resultCode == RESULT_OK) {
            uri = result.data?.data
            //Set Image
            binding.ivProfilePicture.setImageURI(uri)
        }
    }

    private fun updateProfile() {
        if(helperSharedPreferences.getProfilePicture() != "none") {
            val userData = HashMap<String, Any>()
            userData["email"] = binding.etEmail.text.toString()
            userData["username"] = binding.etName.text.toString()
            userData["phone"] = binding.etTelp.text.toString()

            val profilePictureUrl = currentUser.photoUrl

            //Delete Previous Image
            if(profilePictureUrl != null) {
                storage.getReferenceFromUrl(profilePictureUrl.toString())
                    .delete()
            }

            val uploadFile = storageReference.child("users/" + currentUser.email + System.currentTimeMillis())
            val uploadTask = uploadFile.putFile(uri!!)

            uploadTask.addOnSuccessListener {
                uploadFile.downloadUrl.addOnSuccessListener {
                    uri = it
                }
            }

            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()

            //Update Firestore User
            firestore.collection("users")
                .document(currentUser.uid)
                .set(userData)
                .addOnSuccessListener {
                    //Update Firebase Authentication
                    currentUser.updateProfile(userProfileChangeRequest)
                        .addOnSuccessListener {
                            currentUser.updateEmail(userData["email"].toString())
                            finish()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this@EditProfileActivity, "Error : $it", Toast.LENGTH_SHORT).show()
                }

//                storage.getReferenceFromUrl(profilePictureUrl)
//                    .delete()
//
//                val uploadFile = storageReference.child("users/" + currentUser.email + System.currentTimeMillis())
//                val uploadTask = uploadFile.putFile(uri!!)
//
//                uploadTask.addOnSuccessListener {
//                    uploadFile.downloadUrl.addOnSuccessListener { uri ->
//                        val userData = HashMap<String, Any>()
//
//                        userData["email"] = binding.etEmail.text.toString()
//                        userData["username"] = binding.etName.text.toString()
//                        userData["phone"] = binding.etTelp.text.toString()
//                        userData["profilePicture"] = uri
//
//                        UserProfileChangeRequest.Builder()
//                            .setDisplayName(userData["username"].toString())
//                            .setPhotoUri(uri)
//                            .build()
//                    }
//                }
        }
    }

    private fun updateProfileNoImage() {
        val userData = HashMap<String, Any>()
        userData["email"] = binding.etEmail.text.toString()
        userData["username"] = binding.etName.text.toString()
        userData["phone"] = binding.etTelp.text.toString()

        //Update Firestore User
        firestore.collection("users")
            .document(currentUser.uid)
            .set(userData)
            .addOnSuccessListener {
                //Update Firebase Authentication
                currentUser.updateEmail(userData["email"].toString())
                    .addOnSuccessListener {
                        helperSharedPreferences.setUsername(userData["username"].toString())
                        helperSharedPreferences.setPhone(userData["phone"].toString())
                        finish()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this@EditProfileActivity, "Error : $it", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchData() {
        with(binding) {
            //Set Value
            tvProfileName.text = helperSharedPreferences.getUsername()
            tvEmail.text = currentUser.email

            //Set Default value
            etName.setText(helperSharedPreferences.getUsername())
            etEmail.setText(currentUser.email)
            etTelp.setText(helperSharedPreferences.getPhone())

            val profilePicture = currentUser.photoUrl

            if(profilePicture != null) {
                Glide.with(ivProfilePicture)
                    .load(profilePicture)
                    .centerCrop()
                    .into(ivProfilePicture)
            }
        }
    }
}