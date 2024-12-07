package com.bytesdrawer.mlkitposadev

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object MLKitManager {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    private val _imageLabel = MutableStateFlow("")
    val imageLabel = _imageLabel.asStateFlow()

    private val _visibleTextInCamera = MutableStateFlow("")
    val visibleTextInCamera = _visibleTextInCamera.asStateFlow()

    fun recognizeText(image: InputImage) {
         recognizer.process(image)
            .addOnSuccessListener { visionText ->
                _visibleTextInCamera.value = visionText.text
            }
    }

    fun labelImage(image: InputImage) {
        labeler.process(image)
            .addOnSuccessListener { label ->
                _imageLabel.value = "${label.firstOrNull()?.text}, Confianza: ${label.firstOrNull()?.confidence}"
            }
    }
}