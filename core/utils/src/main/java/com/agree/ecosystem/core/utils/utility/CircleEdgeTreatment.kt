package com.agree.ecosystem.core.utils.utility

import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

class CircleEdgeTreatment(
    private val radius: Float,
    private val inside: Boolean = true,
    private val quantity: Int = 1
) : EdgeTreatment() {
    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        val circleRadius = radius * interpolation
        val angle = 180f
        val size = length / quantity
        val sizeHalf = (size / 2)

        shapePath.lineTo(size - circleRadius - sizeHalf, 0f)
        for (i in 1..quantity) {
            shapePath.addArc(
                (size * i) - circleRadius - sizeHalf, -circleRadius,
                (size * i) + circleRadius - sizeHalf, circleRadius,
                if (inside) angle else -angle,
                if (inside) -angle else angle
            )
        }
        shapePath.lineTo(length, 0f)
    }
}
