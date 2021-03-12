package com.risingstar.pustakalaya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.activities.MainActivity
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MyProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyProfileFragment : Fragment() {

    lateinit var imgUserMyProfile : ImageView
    lateinit var txtUserNameMyProfile : TextView
    lateinit var txtUserMailMyProfile : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)

        imgUserMyProfile = view.findViewById(R.id.imgUserMyProfile)
        txtUserNameMyProfile = view.findViewById(R.id.txtUserNameMyProfile)
        txtUserMailMyProfile = view.findViewById(R.id.txtUserMailMyProfile)

        Picasso.get().load(this.arguments?.getString("User photo url")).error(R.drawable.ic_menu_profile).into(imgUserMyProfile)

        val args = this.arguments
        if (args != null){
            txtUserNameMyProfile.text = args.getString("User name")

            txtUserMailMyProfile.text = args.getString("User Mail")
        }

        return view
    }


}