package com.rhappdeveloper.podcastapp.data.remote.dto


import kotlinx.serialization.SerialName

data class LookingFor(
    @SerialName("cohosts")
    val cohosts: Boolean,
    @SerialName("cross_promotion")
    val crossPromotion: Boolean,
    @SerialName("guests")
    val guests: Boolean,
    @SerialName("sponsors")
    val sponsors: Boolean
)