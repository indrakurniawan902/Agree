package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.presentation.dialog.previewimage.PreviewImageFragment
import com.agree.ecosystem.core.utils.utility.CircleEdgeTreatment
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTC2TimeTo
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.extension.toCurrencyFormat
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentTransactionDetailBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.PhotoAdapter
import com.agree.ecosystem.monitoring.presentation.navigation.DetailSubVesselNavigation
import com.agree.ui.UiKitAttrs
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionDetailFragment :
    AgrFragment<FragmentTransactionDetailBinding>(),
    TransactionDetailDataContract {
    override fun initObserver() {
        super.initObserver()
        addObservers(TransactionDetailObserver(this, viewModel))
    }

    override fun initUI() {
        super.initUI()
        applyEdgeTreatmentToCardView()
        with(binding) {
            toolbar.setBackButtonOnClick {
                findNavController().navigateUp()
            }
            swipeRefresh.setOnRefreshListener {
                viewModel.getTransactionDetail(args.transaction?.id.toString())
            }
            swipeRefresh.setColorSchemeResources(com.agree.ui.R.color.primary_500)
        }
    }

    override fun initData() {
        super.initData()
        binding.swipeRefresh.isRefreshing = true
        viewModel.getTransactionDetail(args.transaction?.id.toString())
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnEdit.setOnClickListener {
                menuDetailSubVessel.fromTransactionDetailToUpdateTransaction(args.transaction?.id)
            }
        }
    }

    private fun applyData(transactionDetail: TransactionDetail) {
        with(binding) {
            val fee =
                if (transactionDetail.fee != 0.toDouble()) transactionDetail.fee.toCurrencyFormat(
                    maximumFractionDigits = 2
                ) else "-"
            tvRealization.text = requireContext().getString(R.string.label_rupiah_value, fee)
            tvDateTime.text = transactionDetail.dateTime.convertUTC2TimeTo(ConverterDate.SHORT_DATE)
            tvStatus.text = transactionDetail.status

            val bruto = setRoundDecimal((transactionDetail.bruto * 1000))
            tvBruto.text = requireContext().getString(R.string.label_weight_value, bruto)
            tvDeliveryTypeSubmission.text = transactionDetail.productType

            tvPriceOffer.text = requireContext().getString(
                R.string.label_rupiah_value, transactionDetail.offeringPrice.toCurrencyFormat()
            )
            tvNoteSubmission.text = transactionDetail.note.ifEmpty { "-" }
            val images = transactionDetail.images
            if (images.isNotEmpty()) {
                val adapterSubmission = PhotoAdapter {
                    if ((images.size) > 0) {
                        PreviewImageFragment(
                            images = images,
                            it
                        ).setBackgroundColor(com.agree.ui.R.color.tertiary_700)
                            .setToolbarCloseTitle(requireActivity().resources.getString(R.string.label_close))
                            .show(
                                requireActivity().supportFragmentManager, PreviewImageFragment.TAG
                            )
                    }
                }
                adapterSubmission.addOrUpdateAll(transactionDetail.images)
                rvPhotoSubmission.adapter = adapterSubmission
                containerPhotoSubmission.visible()
            } else containerPhotoSubmission.gone()

            val responseImages = transactionDetail.responseImages
            if (responseImages.isNotEmpty()) {
                val adapterRealization = PhotoAdapter {
                    if ((responseImages.size) > 0) {
                        PreviewImageFragment(
                            images = responseImages,
                            it
                        ).setBackgroundColor(com.agree.ui.R.color.tertiary_700)
                            .setToolbarCloseTitle(requireActivity().resources.getString(R.string.label_close))
                            .show(
                                requireActivity().supportFragmentManager, PreviewImageFragment.TAG
                            )
                    }
                }
                adapterRealization.addOrUpdateAll(transactionDetail.responseImages)
                rvPhotoRealization.adapter = adapterRealization
                containerPhotoRealization.visible()
            } else containerPhotoRealization.gone()

            if (transactionDetail.status == requireContext().getString(R.string.label_done_transaction)) {
                imgStatus.setImageResource(R.drawable.ic_checked)
                btnEdit.gone()
                containerRealization.visible()

                val netto = setRoundDecimal((transactionDetail.netto * 1000))
                tvNetto.text = requireContext().getString(R.string.label_weight_value, netto)
                tvDeliveryTypeRealization.text = transactionDetail.productType
                tvTotalRealizationPrice.text = requireContext().getString(
                    R.string.label_rupiah_value, transactionDetail.fee.toCurrencyFormat()
                )
                tvNoteRealization.text = transactionDetail.description.ifEmpty { "-" }

                tvStatus.apply {
                    text = context.getString(R.string.label_done)
                    setTextColor(UiKitAttrs.success_normal.getAttrsValue(requireContext()))
                }
                cvStatus.strokeColor = UiKitAttrs.success_hover.getAttrsValue(requireContext())
            } else {
                imgStatus.setImageResource(R.drawable.ic_time)
                btnEdit.visible()
                containerRealization.gone()

                tvStatus.apply {
                    text = context.getString(R.string.label_inprogress)
                    setTextColor(UiKitAttrs.warning_normal.getAttrsValue(requireContext()))
                }
                cvStatus.strokeColor = UiKitAttrs.warning_normal.getAttrsValue(requireContext())
            }
            swipeRefresh.isRefreshing = false
        }
        applyWatermark()
    }

    private fun applyEdgeTreatmentToCardView() {
        with(binding) {
            cvCard.shapeAppearanceModel = cvCard.shapeAppearanceModel.toBuilder()
                .setBottomEdge(CircleEdgeTreatment(25f, true, 10)).setTopLeftCornerSize(20f)
                .setTopRightCornerSize(20f).build()
        }
    }

    private fun applyWatermark() {
        itemWatermarkSize = 1
        isWatermarkFinished = false
        with(binding) {
            containerWatermark.removeAllViews()
            containerBody.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        contentHeight = containerBody.height
                        containerBody.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )

            containerWatermark.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        val height = containerWatermark.height
                        if (height < contentHeight) {
                            if (!isWatermarkFinished) {
                                itemWatermarkSize++
                                loadWatermarkLayout()
                            } else {
                                loadWatermarkLayout()
                            }
                        } else {
                            if (!isWatermarkFinished) {
                                loadWatermarkLayout()
                                isWatermarkFinished = true
                                containerWatermark.viewTreeObserver.removeOnGlobalLayoutListener(
                                    this
                                )
                            }
                        }
                    }
                }
            )
        }
    }

    private fun loadWatermarkLayout() {
        with(binding) {
            containerWatermark.removeAllViews()
            if (isAdded) {
                for (n in 0..itemWatermarkSize) {
                    val imageView = ImageView(requireContext())
                    imageView.setImageResource(R.drawable.img_agree_vertical)
                    val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                    params.topMargin = 30
                    params.bottomMargin = 30
                    imageView.layoutParams = params
                    containerWatermark.addView(imageView)
                }
            }
        }
    }

    private fun setRoundDecimal(value: Double): String {
        return String.format(roundDecimal, value)
            .replace(".00", String())
            .replace(",00", String())
    }

    override fun onGetTransactionDetailSuccess(data: TransactionDetail) {
        with(binding) {
            imgStatus.clearColorFilter()
            applyData(data)
            msvTransactionDetail.showDefaultLayout()
        }
    }

    override fun onTransactionDetailLoading() {
        with(binding) {
            imgStatus.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            msvTransactionDetail.showLoadingLayout()
        }
    }

    override fun onTransactionDetailFailed(e: Throwable?) {
        binding.msvTransactionDetail.showErrorLayout()
    }

    private val roundDecimal: String = "%.2f"
    private var contentHeight: Int = 0
    private var itemWatermarkSize: Int = 1
    private var isWatermarkFinished: Boolean = false
    private val args: TransactionDetailFragmentArgs by navArgs()
    private val viewModel: TransactionDetailViewModel by viewModel()
    private val menuDetailSubVessel: DetailSubVesselNavigation by navigation { activity }
}
