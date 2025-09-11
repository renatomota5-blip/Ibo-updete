
package com.iboplus.app.ui.qr

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.iboplus.app.R
import com.iboplus.app.data.PanelEndpoints

class QrDialog(private val url: String): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d = Dialog(requireContext())
        d.setContentView(R.layout.dialog_qr)
        val img = d.findViewById<ImageView>(R.id.qrImage)
        val writer = QRCodeWriter()
        val matrix = writer.encode(url, BarcodeFormat.QR_CODE, 512, 512)
        val bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) for (y in 0 until 512) {
            bmp.setPixel(x, y, if (matrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
        }
        img.setImageBitmap(bmp)

        d.findViewById<Button>(R.id.btnOpenSite).setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(PanelEndpoints.LOGIN_QR))
            startActivity(i)
        }
        return d
    }
}
