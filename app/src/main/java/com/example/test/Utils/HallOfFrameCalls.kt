package com.example.test.Utils

import android.support.annotation.Nullable
import android.util.Log
import com.example.test.Models.HallOfFrame
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class HallOfFrameCalls {

    interface CallBacks<T> {
        fun onResponse(@Nullable hof: List<HallOfFrame>)
        fun onFailure()
    }

    fun fetchAllHallOfFrame(callback: CallBacks<Any?>) {
        var callBackWeakReference = WeakReference<CallBacks<Any?>>(callback)

        var hofService: HallOfFrameService = HallOfFrameService.retrofit.create(HallOfFrameService::class.java)

        var call = hofService.getAllHof()

        call.enqueue(object : Callback<List<HallOfFrame>> {

            override fun onFailure(call: Call<List<HallOfFrame>>, t: Throwable) {
                if (callBackWeakReference.get() != null) {
                    callBackWeakReference.get()!!.onFailure()
                    Log.d("TAG", t.message)
                    Log.d("TAG", t.localizedMessage)
                    Log.d("TAG", t.cause?.message)
                }
            }

            override fun onResponse(call: Call<List<HallOfFrame>>, response: Response<List<HallOfFrame>>) {
                if (callBackWeakReference.get() != null) {
                    callBackWeakReference.get()!!.onResponse(response.body()!!)
                }
            }

        })
    }

    fun addHof(callback: CallBacks<Any?>, hallof: HallOfFrame) {
        var callBackWeakReference = WeakReference<CallBacks<Any?>>(callback)

        var hofService: HallOfFrameService = HallOfFrameService.retrofit.create(HallOfFrameService::class.java)

        var call = hofService.addHof(
            hallof.id,
            hallof.name,
            hallof.maxpoints,
            hallof.countNumber
        )

        call.enqueue(object : CallBacks<HallOfFrame>, Callback<HallOfFrame> {
            override fun onResponse(call: Call<HallOfFrame>, response: Response<HallOfFrame>) {
                if(callBackWeakReference.get() != null) {

                }
            }

            override fun onFailure(call: Call<HallOfFrame>, t: Throwable) {
                if(callBackWeakReference.get() != null) {
                    callBackWeakReference.get()!!.onFailure()
                    Log.d("TAG ERROR", t.message)
                    Log.d("TAG ERROR", t.localizedMessage)
                }
            }

            override fun onResponse(hof: List<HallOfFrame>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}