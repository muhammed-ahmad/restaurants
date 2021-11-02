package com.falcon.restaurants.screens.common.dialogs
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class ServerErrorDialogFragment : BaseDialog() {

    companion object{
        const val ERROR: String = "ERROR"
        fun newInstance(error: String): ServerErrorDialogFragment {
            val fragment = ServerErrorDialogFragment()
            val args = Bundle()
            args.putString(ERROR, error)
            fragment.arguments =args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val error: String  = arguments!!.getString(ERROR) as String

        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setTitle("Server Error")
        alertDialogBuilder.setMessage("Error: $error")

        alertDialogBuilder.setPositiveButton("OK"
        ) { dialog, which -> TODO("Not yet implemented") }

        alertDialogBuilder.setNegativeButton("Cancel"
        ) { dialog, which ->
            if (dialog != null) {
                dialog.dismiss()
            }
        }

        return alertDialogBuilder.create()
    }

}