package com.github.elvirka.hyperskill

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.pow
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val arguments = parseArguments(args)
    val inputFile = File(arguments.`in`)
    val outputFile = File(arguments.out)
    val image = drawEnergyMap(ImageIO.read(inputFile))
    ImageIO.write(image, "png", outputFile)
}

fun drawEnergyMap(image: BufferedImage): BufferedImage {
    val energyParams = calculateEnergyParams(image)
    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            val energy = energyParams.energyMap[y][x]
            val intensity = (255.0 * energy / energyParams.maxEnergy).toInt()
            val newColor = Color(intensity, intensity, intensity)
            image.setRGB(x, y, newColor.rgb)
        }
    }
    return image
}

fun calculateEnergyParams(image: BufferedImage): EnergyParams {
    var maxEnergy = 0.0
    val energyMap = Array(image.height) { DoubleArray(image.width) }
    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            val correctedX = if (x == 0) 1 else if (x == image.width - 1) x - 1 else x
            val correctedY = if (y == 0) 1 else if (y == image.height - 1) y - 1 else y
            val xp1Color = Color(image.getRGB(correctedX + 1, y))
            val xm1Color = Color(image.getRGB(correctedX - 1, y))
            val rx = xm1Color.red - xp1Color.red
            val gx = xm1Color.green - xp1Color.green
            val bx = xm1Color.blue - xp1Color.blue
            val yp1Color = Color(image.getRGB(x, correctedY + 1))
            val ym1Color = Color(image.getRGB(x, correctedY - 1))
            val ry = ym1Color.red - yp1Color.red
            val gy = ym1Color.green - yp1Color.green
            val by = ym1Color.blue - yp1Color.blue
            val xGradient = rx.toDouble().pow(2) + gx.toDouble().pow(2) + bx.toDouble().pow(2)
            val yGradient = ry.toDouble().pow(2) + gy.toDouble().pow(2) + by.toDouble().pow(2)
            val energy = sqrt(xGradient + yGradient)
            energyMap[y][x] = energy
            if (energy > maxEnergy) {
                maxEnergy = energy
            }
        }
    }
    return EnergyParams(maxEnergy, energyMap)
}

class EnergyParams(
    val maxEnergy: Double,
    val energyMap: Array<DoubleArray>
)

data class Arguments(
    val `in`: String,
    val out: String,
)

fun parseArguments(args: Array<String>): Arguments {
    val keys = setOf("-in", "-out")
    val arguments = args.toList().chunked(2) { pair ->
        val key = pair[0]
        if (pair.size == 2) {
            if (key in keys) {
                key to pair[1]
            } else {
                error("Unknown argument $key.")
            }
        } else {
            if (key in keys) {
                error("Value for argument $key is not specified.")
            } else {
                error("Unknown argument $key.")
            }
        }
    }.toMap()
    return Arguments(
        `in` = arguments["-in"]!!,
        out = arguments["-out"]!!
    )
}