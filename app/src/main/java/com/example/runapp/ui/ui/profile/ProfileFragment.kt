package com.example.runapp.ui.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runapp.databinding.FragmentProfileBinding
import com.example.runapp.model.RunModelFinal
import com.example.runapp.ui.ListRunActivity
import com.example.runapp.ui.adapter.RunAdapter
import com.example.runapp.ui.viewmodel.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by inject<ProfileViewModel>()
    private var lista = ArrayList<RunModelFinal>()
    private lateinit var adapter: RunAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
        acct?.let {
            binding.txtName.text = it.displayName
            binding.txtEmail.text = it.email
            Picasso.get().load(it.photoUrl).priority(Picasso.Priority.HIGH).into(binding.imgProfile)
        }
        getLastRun(
            binding.txtDist,
            binding.txtTimeFinal,
            binding.txtKmh,
            binding.txtCal,
            binding.progressBarProfile,
            binding.layout,
            binding.layout2,
            acct?.id!!
        )
        binding.txtSeeAllRun.setOnClickListener {
            startActivity(Intent(requireActivity(), ListRunActivity::class.java))
        }

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getAllRuns(
                acct.id!!,
                binding.rvRuns,
                binding.txtSeeAllRun
            )
        }

        CoroutineScope(Dispatchers.Main).launch{
            viewModel.listRuns.observe(requireActivity()){
                lista = it as ArrayList<RunModelFinal>
                adapter = RunAdapter(lista)
                binding.rvRuns.adapter = adapter
                binding.rvRuns.layoutManager = LinearLayoutManager(requireActivity())
            }
        }

    }

    private fun getLastRun(
        txtDist: MaterialTextView,
        txtTimeFinal: MaterialTextView,
        txtKmh: MaterialTextView,
        txtCal: MaterialTextView,
        progressBarProfile: ProgressBar,
        layout: LinearLayout,
        layout2: LinearLayout,
        userId: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getLastRun(
                    requireContext(),
                    txtDist,
                    txtKmh,
                    txtCal,
                    txtTimeFinal,
                    progressBarProfile,
                    layout,
                    layout2,
                    userId
                )
            }
        }
    }
}