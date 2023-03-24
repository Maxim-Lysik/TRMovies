package com.example.moviesgrab.networking

import com.example.moviesgrab.Constants


class UrlProvider {

    fun getBaseUrl(): String {
        return Constants.BASE_URL
    }

    fun getBaseUrl2(): String {
        return "base_url"
    }
}