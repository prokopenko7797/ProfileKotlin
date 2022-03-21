package com.example.profilekot.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup 
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.profilekot.R
import com.example.profilekot.databinding.SignUpFragmentBinding
import com.example.profilekot.viewModels.SignUpViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: SignUpFragmentBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sign_up_fragment,
            container,
            false)
        
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.button.isEnabled = false

        viewModel.login.observe(viewLifecycleOwner){
            binding.button.isEnabled = !hasEmptyFields()
        }

        viewModel.password.observe(viewLifecycleOwner){
            binding.button.isEnabled = !hasEmptyFields()
        }

        viewModel.confirmPassword.observe(viewLifecycleOwner){
            binding.button.isEnabled = !hasEmptyFields()
        }

        return binding.root
    }

    private fun hasEmptyFields() : Boolean{
        return viewModel.login.value.isNullOrBlank()
                || viewModel.password.value.isNullOrBlank()
                || viewModel.confirmPassword.value.isNullOrBlank()
    }
}