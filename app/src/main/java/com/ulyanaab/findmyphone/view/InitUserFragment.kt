package com.ulyanaab.findmyphone.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.model.RepositoryUser
import com.ulyanaab.findmyphone.model.RepositoryUserImpl
import com.ulyanaab.findmyphone.model.objects.UserModel
import com.ulyanaab.findmyphone.utilities.TOKEN_KEY
import com.ulyanaab.findmyphone.utilities.TOKEN_PREFERENCE
import com.ulyanaab.findmyphone.utilities.replaceFragment
import com.ulyanaab.findmyphone.utilities.token


class InitUserFragment : Fragment() {

    private val repository: RepositoryUser = RepositoryUserImpl()

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
        initUser()
    }

    private fun initViews() {
        requireView().findViewById<Button>(R.id.button_try_again).setOnClickListener {
            initUser()
        }
    }

    private fun initUser() {
        val sPref = requireContext().getSharedPreferences(
            TOKEN_PREFERENCE,
            AppCompatActivity.MODE_PRIVATE
        )
        val uid = sPref.getString(TOKEN_KEY, "null")
        if (uid == "null") {
            sendUser {
                sPref.edit().putString(TOKEN_KEY, token).apply()
                replaceFragment(MainFragment())
            }
        } else {
            token = uid!!
            replaceFragment(MainFragment())
        }
    }

    @SuppressLint("HardwareIds")
    private fun sendUser(callback: () -> Unit) {
        val user = UserModel(
            deviceId = Settings.Secure.getString(
                requireContext().applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        )
        repository.sendData(
            user,
            onSuccess = callback,
            onFailure = this::showDialog
        )
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Нет подключения к интернету")
            .setMessage("Для инициализации необходимо получить токен с сервера. Проверьте доступ к интернету и повторите попытку.")
            .setPositiveButton("Ок") { dialog, _ ->
                dialog.cancel()
            }
        builder.create().show()
    }

}