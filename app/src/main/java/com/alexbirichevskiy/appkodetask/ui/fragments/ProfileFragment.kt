package com.alexbirichevskiy.appkodetask.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexbirichevskiy.appkodetask.Consts.USER_TAG
import com.alexbirichevskiy.appkodetask.databinding.FragmentProfileBinding
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var profile: UserItemEntity

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf {
            it.containsKey(USER_TAG)?.apply {
                profile = requireArguments().getParcelable(USER_TAG)!!
                binding.fullNameProfileTextView.text = "${profile.firstName} ${profile.lastName}"
                binding.phoneProfileTextView.text = profile.phone
                binding.birthdayProfileTextView.text = SimpleDateFormat("dd MMMM yyyy").format(
                    SimpleDateFormat("yyyy-mm-dd").parse(profile.birthday)
                )
                binding.positionProfileTextView.text = profile.position
                binding.userTagProfileTextView.text = profile.userTag
                Glide.with(binding.root).load(profile.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.avatarProfileImageView)
            }
        }

        binding.phoneProfileTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            val uri = "tel:" + binding.phoneProfileTextView.text
            intent.setData(Uri.parse(uri))
            activity?.startActivity(intent)
        }

        binding.icArrowLeftImageView.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}