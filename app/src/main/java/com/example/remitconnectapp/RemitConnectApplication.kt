package com.example.remitconnectapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@Singleton
@HiltAndroidApp
class RemitConnectApplication : Application()