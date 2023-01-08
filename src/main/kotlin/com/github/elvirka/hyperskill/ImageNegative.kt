package com.github.elvirka.hyperskill

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val arguments = parseArguments(args)
    val inputFile = File(arguments.`in`)
    val outputFile = File(arguments.out)
    val image = drawNegative(ImageIO.read(inputFile))
    ImageIO.write(image, "png", outputFile)
}

private fun drawNegative(image: BufferedImage): BufferedImage {
    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            val color = Color(image.getRGB(x, y), true)
            val newColor = Color(255 - color.red, 255 - color.green, 255 - color.blue, color.alpha)
            image.setRGB(x, y, newColor.rgb)
        }
    }
    return image
}