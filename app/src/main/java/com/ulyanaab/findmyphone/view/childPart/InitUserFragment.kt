package com.ulyanaab.findmyphone.view.childPart

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.controllers.InitUserController
import com.ulyanaab.findmyphone.utilities.replaceFragment


class InitUserFragment : Fragment() {

    private val userController = InitUserController()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_init_user, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        initUser()
    }

    private fun initViews() {
        requireView().findViewById<Button>(R.id.button_try_again).setOnClickListener {
            initUser()
        }
    }

    private fun initUser() {
        if (userController.isInited()) {
            replaceFragment(MainFragment())
        } else {
            userController.sendUser(
                onSuccess = {
                    replaceFragment(MainFragment())
                },
                onFailure = this::showDialog
            )
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.network_dialog_title))
            .setMessage(getString(R.string.network_dialog_description))
            .setPositiveButton("Ок") { dialog, _ ->
                dialog.cancel()
            }
        builder.create().show()
    }

}