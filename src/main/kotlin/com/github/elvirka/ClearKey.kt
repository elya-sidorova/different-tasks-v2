package com.github.elvirka

fun main() {
    val publicKeyValue = """
        -----BEGIN PUBLIC KEY-----
        MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw9ItQ8wLGo1geHOmJTu8
        cvq4RWbdWPDPXHM1JE5US3kd5e+SKiBMhHbXA+I73kGO8xxskqWnSnDxQcPFEE7J
        Lavp6fkpuwlYePX9gcNqflbsV7MkRM/vESOlHbjvB3DH9nBSBUcU96YKGjEQG4/q
        LmqF+hYszNF8jVlE3D5FWPlny/vuNWImeN7RWRcCw09ZgLmAz2fe8/A7yDsjX8uE
        WJy6HKO+cDadSeKd2g9OgWy8ah7V0uGqU61IXthDVOSZgUBFySzdmfKmOpgPtWgJ
        Hk16y2IhH4pJQ99K+D7e6NsU5n88o13jiU2xot1n5Qa5EX5N8j+O5PQ/qJqeGESe
        EwIDAQAB
        -----END PUBLIC KEY-----
    """.trimIndent()
    println(publicKeyValue.lines().size)
    val lines = publicKeyValue.lines()
    val pureKey =  lines.slice(1 until lines.size-1)
        .joinToString("")
    println(pureKey)

    val pureKey1 = publicKeyValue.removePrefix("-----BEGIN PUBLIC KEY-----")
        .removeSuffix("-----END PUBLIC KEY-----")
        .lines().joinToString("")
    println(pureKey1)
}