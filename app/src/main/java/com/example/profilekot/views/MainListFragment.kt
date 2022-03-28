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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profilekot.Constants
import com.example.profilekot.viewModels.MainListViewModel
import com.example.profilekot.R
import com.example.profilekot.databinding.MainListFragmentBinding
import com.example.profilekot.models.profile.ProfileModel
import com.example.profilekot.services.image.ImageService
import com.example.profilekot.viewModels.AddEditViewModel

class MainListFragment : Fragment() {
    private lateinit var binding: MainListFragmentBinding
    private lateinit var viewModel: MainListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_list_fragment,
            container,
            false)
        viewModel = ViewModelProvider(this)[MainListViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager

        val profileActionListener = object : ProfileActionListener{
            override fun onEditProfile(profile: ProfileModel) {
                findNavController().navigate(MainListFragmentDirections.actionMainListFragmentToAddEditFragment(profile.id, getString(R.string.EditProfile)))
            }

            override fun onDeleteProfile(profile: ProfileModel) {
                viewModel.onDeleteProfile(profile)
            }
        }

        viewModel.profiles.observe(viewLifecycleOwner, Observer {
            val adapter = ProfileAdapter(it, profileActionListener)
            binding.recyclerView.adapter = adapter
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.onGetProfiles()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_list_navbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.logout_action -> {
                viewModel.onLogout()
                findNavController().navigate(MainListFragmentDirections.actionMainListFragmentToSignInFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}