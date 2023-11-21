package com.chorok.mysubwaydiary

import android.content.Intent
import android.graphics.Matrix
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chorok.mysubwaydiary.databinding.DiaryPageBinding


class DiaryPage : AppCompatActivity() {
    lateinit var binding: DiaryPageBinding

    internal enum class TOUCH_MODE {
        NONE,  // 터치 안했을 때
        SINGLE,  // 한손가락 터치
        MULTI //두손가락 터치
    }

    private var imageView: ImageView? = null
    private var touchMode: TOUCH_MODE? = null

    private var matrix //기존 매트릭스
            : Matrix = Matrix()
    private var savedMatrix //작업 후 이미지에 매핑할 매트릭스
            : Matrix? = null

    private var startPoint //한손가락 터치 이동 포인트
            : PointF? = null

    private var midPoint //두손가락 터치 시 중신 포인트
            : PointF? = null
    private var oldDistance //터치 시 두손가락 사이의 거리
            = 0f

    private var oldDegree = 0.0 // 두손가락의 각도




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiaryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        matrix = Matrix()
        savedMatrix = Matrix()
        imageView = binding.imageView2
        imageView?.setOnTouchListener(onTouch)
        imageView?.setScaleType(ImageView.ScaleType.MATRIX) // 스케일 타입을 매트릭스로 해줘야 움직인다.

        binding.btnPlus.setOnClickListener {

            val intent = Intent(this, SavePage::class.java)
            startActivity(intent)

        }
    }

    private val onTouch = OnTouchListener { v, event ->
        if (v == imageView) {
            val action = event.action
            when (action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    touchMode = TOUCH_MODE.SINGLE
                    donwSingleEvent(event)
                }

                MotionEvent.ACTION_POINTER_DOWN -> if (event.pointerCount == 2) { // 두손가락 터치를 했을 때
                    touchMode = TOUCH_MODE.MULTI
                    downMultiEvent(event)
                }

                MotionEvent.ACTION_MOVE -> if (touchMode == TOUCH_MODE.SINGLE) {
                    moveSingleEvent(event)
                } else if (touchMode == TOUCH_MODE.MULTI) {
                    moveMultiEvent(event)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> touchMode = TOUCH_MODE.NONE
            }
        }
        true
    }
    private fun getMidPoint(e: MotionEvent): PointF? {
        val x = (e.getX(0) + e.getX(1)) / 2
        val y = (e.getY(0) + e.getY(1)) / 2
        return PointF(x, y)
    }

    private fun getDistance(e: MotionEvent): Float {
        val x = e.getX(0) - e.getX(1)
        val y = e.getY(0) - e.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun donwSingleEvent(event: MotionEvent) {
        savedMatrix!!.set(matrix)
        startPoint = PointF(event.x, event.y)
    }
    private fun downMultiEvent(event: MotionEvent) {
        oldDistance = getDistance(event)
        if (oldDistance > 5f) {
//            savedMatrix!!.set(matrix)
            midPoint = getMidPoint(event)
            val radian =
                Math.atan2((event.y - midPoint!!.y).toDouble(), (event.x - midPoint!!.x).toDouble())
            oldDegree = radian * 180 / Math.PI
        }
    }
    private fun moveSingleEvent(event: MotionEvent) {
        matrix!!.set(savedMatrix)
        matrix!!.postTranslate(event.x - startPoint!!.x, event.y - startPoint!!.y)
        imageView!!.imageMatrix = matrix
    }
    private fun moveMultiEvent(event: MotionEvent) {
        val newDistance = getDistance(event)
        if (newDistance > 5f) {
            matrix!!.set(savedMatrix)
            val scale = newDistance / oldDistance
            matrix!!.postScale(scale, scale, midPoint!!.x, midPoint!!.y)
            val nowRadian =
                Math.atan2((event.y - midPoint!!.y).toDouble(), (event.x - midPoint!!.x).toDouble())
            val nowDegress = nowRadian * 180 / Math.PI
            val degree = (nowDegress - oldDegree).toFloat()

            imageView!!.imageMatrix = matrix
        }
    }

}
