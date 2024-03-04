package com.agree.ecosystem.core.utils.utility

import com.agree.ui.UiAttrs
import com.agree.ui.UiKitAttrs

/**
 * Status bar colors
 */
enum class StatusBarColors(val value: Int) {
    WHITE(UiKitAttrs.white),
    PRIMARY10(UiKitAttrs.colorPrimary100),
    PRIMARY500(UiKitAttrs.colorPrimary500),
    CUSTOMINFO500(UiAttrs.customInfo500),
    CUSTOMINFO700(UiAttrs.customInfo700),
    PRIMARY(UiKitAttrs.colorPrimary),
    SECONDARY(UiKitAttrs.colorSecondary)
}
