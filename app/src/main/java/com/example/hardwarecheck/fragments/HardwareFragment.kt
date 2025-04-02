package com.example.hardwarecheck.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hardwarecheck.R
import com.example.hardwarecheck.utils.HardwareInfoUtils

class HardwareFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.hardware, container, false)

        val deviceInfo = HardwareInfoUtils.collectDeviceInfo(requireContext())
        view.findViewById<TextView>(R.id.modelValue).text = deviceInfo.model
        view.findViewById<TextView>(R.id.osValue).text = deviceInfo.osInfo
        view.findViewById<TextView>(R.id.processorValue).text = deviceInfo.processor
        view.findViewById<TextView>(R.id.memoryValue).text = deviceInfo.memory
        view.findViewById<TextView>(R.id.coresValue).text = deviceInfo.cores
        view.findViewById<TextView>(R.id.storageValue).text = deviceInfo.storage
        view.findViewById<TextView>(R.id.gpuValue).text = deviceInfo.gpu
        view.findViewById<TextView>(R.id.sensorsValue).text = deviceInfo.sensors

        return view
    }
}