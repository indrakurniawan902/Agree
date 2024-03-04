package com.agree.ui.widget.photofield

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.agree.ui.R
import com.agree.ui.UiKitDrawable
import com.agree.ui.UiKitString
import com.agree.ui.databinding.LayoutPhotoAvatarBinding
import com.agree.ui.databinding.LayoutPhotoFieldBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.isNotNull
import com.oratakashi.viewbinding.core.tools.isNull
import com.telkom.legion.component.image.LgnImageView
import com.telkom.legion.component.image.PhotoPickerBottomSheet
import com.telkom.legion.component.utility.extension.convertBitmapIntoFiles
import com.telkom.legion.component.utility.extension.createMarkRequiredSpan
import com.telkom.legion.component.utility.extension.getBase64
import com.telkom.legion.component.utility.extension.resizeImageFile
import java.io.File

class PhotoFieldView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val photoFieldBinding: LayoutPhotoFieldBinding by lazy {
        LayoutPhotoFieldBinding.inflate(LayoutInflater.from(context), this, false)
    }

    private val photoAvatarBinding: LayoutPhotoAvatarBinding by lazy {
        LayoutPhotoAvatarBinding.inflate(LayoutInflater.from(context), this, false)
    }

    private lateinit var photoPickerBottomSheet: PhotoPickerBottomSheet
    var image: File? = null
    var imageBase64: String? = null
    private var photoHolder: LgnImageView
    private val type: String
    private var styledAttributes: TypedArray
    private var optional = false
    private var photoFieldListener: UploadListener? = null

    init {
        attrs.let {
            styledAttributes =
                context.obtainStyledAttributes(it, R.styleable.PhotoFieldView, 0, 0)
            type = styledAttributes.getString(R.styleable.PhotoFieldView_type_image) ?: "0"
            optional = styledAttributes.getBoolean(R.styleable.PhotoFieldView_optional, false)
        }
        if (type == "0") {
            addView(photoFieldBinding.root)
            with(photoFieldBinding) {
                tvUploadPhoto.setOnClickListener {
                    clickUpload()
                }
                val text = styledAttributes.getString(R.styleable.PhotoFieldView_text_image)
                setText(text)
                photoHolder = imgPhoto
            }
        } else {
            addView(photoAvatarBinding.root)

            with(photoAvatarBinding) {
                val enabled = styledAttributes.getBoolean(R.styleable.PhotoFieldView_enabled, true)
                if (!enabled) {
                    ivTakePhotoAvatar.gone()
                } else {
                    containerPhotoAvatar.setOnClickListener {
                        clickUpload()
                    }
                }
                photoHolder = imgAvatar
            }
        }
    }

    private fun clickUpload() {
        if (photoFieldListener.isNotNull()) {
            photoFieldListener!!.onUploadPicture()
        }
        photoPickerBottomSheet = PhotoPickerBottomSheet {
            upload(it)
        }
        photoPickerBottomSheet.show(
            (context as FragmentActivity).supportFragmentManager,
            "photoBottomSheet"
        )
    }

    private fun setText(text: String?) {
        if (text.isNull()) {
            return
        }
        if (type == "0") {
            if (!optional) {
                context.createMarkRequiredSpan(photoFieldBinding.tvPhotoTitle, text.orEmpty())
            } else {
                photoFieldBinding.tvPhotoTitle.text = text
            }
        }
    }

    private fun setTextError() {
        if (type == "0") {
            photoFieldBinding.tvHelper.setTextColor(ContextCompat.getColor(context, R.color.error_500))
        }
    }

    private fun clearTextError() {
        if (type == "0") {
            photoFieldBinding.tvHelper.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    private fun upload(file: File) {
        try {
            if (type == "0") {
                clearTextError()
            }
            image = resizeImageFile(file, quality = 100)
            val adjustedBitmap = BitmapFactory.decodeFile(image!!.path)
//            val adjustedBitmap = adjustImageRotation(bm, file)
            imageBase64 = getBase64(adjustedBitmap)
            if (type == "0") {
                photoFieldBinding.tvUploadPhoto.text = context.getString(UiKitString.action_reupload)
            }
//            photoHolder.loadImage(adjustedBitmap)

            convertBitmapIntoFiles(context, adjustedBitmap)?.let {
                photoFieldListener?.onFinish(it)
            }
            photoPickerBottomSheet.dismiss()
        } catch (e: Exception) {
            if (type == "0") {
                setTextError()
            }
        }
    }

    fun url(imageUrl: String) {
        if (imageUrl.isNotEmpty()) {
            photoHolder.url = imageUrl
            if (type == "0") {
                photoFieldBinding.tvUploadPhoto.text =
                    context.getString(UiKitString.action_reupload)
            }
            Glide.with(this).asBitmap().load(imageUrl).error(UiKitDrawable.img_placeholder).dontTransform()
                .placeholder(UiKitDrawable.img_placeholder)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageBase64 = getBase64(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Do Nothing
                    }
                })
        } else {
            photoHolder.resId = UiKitDrawable.img_placeholder
        }
    }

    fun containsImage(): Boolean {
        if (imageBase64.isNull()) {
            setTextError()
        }
        return imageBase64.isNotNull()
    }

    interface UploadListener {
        fun onUploadPicture()

        fun onFinish(file: File)
    }

    fun addUploadListener(listener: UploadListener) {
        if (photoFieldListener != null) {
            throw Exception("listener has assigned")
        }

        photoFieldListener = listener
    }
}
