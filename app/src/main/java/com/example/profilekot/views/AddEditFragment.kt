package com.example.profilekot.views

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.profilekot.Constants
import com.example.profilekot.viewModels.AddEditViewModel
import com.example.profilekot.R
import com.example.profilekot.services.image.ImageService
import com.example.profilekot.databinding.AddEditFragmentBinding

class AddEditFragment : Fragment() {
    private lateinit var binding: AddEditFragmentBinding
    private lateinit var viewModel: AddEditViewModel
    private val args by navArgs<AddEditFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_edit_fragment,
            container,
            false)

        viewModel = ViewModelProvider(this)[AddEditViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.profileImage.setOnClickListener { onAddProfileImage() }

        if (args.id != 0){
            viewModel.initializeProfileById(args.id)
        }

        viewModel.image.observe(viewLifecycleOwner, Observer {
            if (it != null){
                val image = Uri.parse(it)
                binding.profileImage.setImageURI(image)
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_edit_navbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.save_action -> {
                if (viewModel.onProfileSaved()){
                    findNavController().popBackStack()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onAddProfileImage() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.AddPhoto))
        builder.setItems(arrayOf(
            getString(R.string.Gallery),
            getString(R.string.Camera) ),
            DialogInterface.OnClickListener { dialog, which ->
                chooseAction(dialog, which)
            })

        builder.create().show()
    }

    private fun chooseAction(dialog: DialogInterface?, which: Int) {
        when (which){
            0 -> {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                galleryIntent.type = "image/"
                startActivityForResult(galleryIntent, Constants.GALLERY_REQUEST)
            }
            else -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            Constants.GALLERY_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imagePath = ImageService.saveImageFromUri(this.context, data?.data!!)
                    viewModel.image.value = imagePath
                }
            }
            Constants.CAMERA_REQUEST -> {
                if (resultCode == Activity.RESULT_OK){
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.profileImage.setImageBitmap(imageBitmap)
                    val imagePath = ImageService.saveImageFromBitmap(this.context, imageBitmap)
                    viewModel.image.value = imagePath
                }
            }
        }
    }
}