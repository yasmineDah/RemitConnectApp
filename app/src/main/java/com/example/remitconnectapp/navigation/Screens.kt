package com.example.remitconnectapp.navigation

sealed class Screens(val root : String) {

    data object Home : Screens("home")
    data object SendMoneyOptions : Screens("send_money_options")
    data object SendToAfrica : Screens("send_to_africa")
    data object Recipients : Screens("recipients")
    data object MobileWallets : Screens("wallets")
    data object SendMoney : Screens("send_money")
    data object Success : Screens("success")

}