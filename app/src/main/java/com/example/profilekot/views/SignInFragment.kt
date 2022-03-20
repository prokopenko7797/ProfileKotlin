package com.example.profilekot.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.profilekot.Constants
import com.example.profilekot.R
import com.example.profilekot.databinding.SignInFragmentBinding
import com.example.profilekot.viewModels.SignInViewModel

class SignInFragment : Fragment() {

    private lateinit var binding: SignInFragmentBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sign_in_fragment,
            container,
            false)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val liveData = findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            Constants.LOGIN)

        liveData?.observe(viewLifecycleOwner){ login ->
            if (login != null){
                viewModel.login.value = login
                liveData.value = null
            }
        }

        binding.button.isEnabled = false

        viewModel.login.observe(viewLifecycleOwner){
            binding.button.isEnabled = !hasEmptyFields()
        }

        viewModel.password.observe(viewLifecycleOwner){
            binding.button.isEnabled = !hasEmptyFields()
        }

        return binding.root
    }

    private fun hasEmptyFields() : Boolean{
        return viewModel.login.value.isNullOrBlank() || viewModel.password.value.isNullOrBlank()
    }
}