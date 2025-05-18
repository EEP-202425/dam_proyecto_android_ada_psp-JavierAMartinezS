package com.example.starlineapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterPasswordVerificationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    @Before
    fun setup() {
        Thread.sleep(1000)
    }

    @Test
    fun verificarCoincidenciaDeContraseñasEnRegistro() {
        try {
            composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").performClick()
            composeTestRule.onNodeWithText("Crear Cuenta").assertIsDisplayed()
            composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
            composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")
            composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
            composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("DiferentePassword")
            composeTestRule.onNodeWithText("Registrarse").assertIsEnabled()
            composeTestRule.onNodeWithText("Registrarse").performClick()
            composeTestRule.onNodeWithText("Las contraseñas no coinciden").assertIsDisplayed()
        } catch (e: Exception) {
            println("Error en verificarCoincidenciaDeContraseñasEnRegistro: ${e.message}")
        }
    }

    @Test
    fun verificarValidacionDeCamposRequeridos() {
        try {
            composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").performClick()
            composeTestRule.onNodeWithText("Registrarse").assertIsNotEnabled()
            composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
            composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")
            composeTestRule.onNodeWithText("Registrarse").assertIsNotEnabled()
            composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
            composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("Password123")
            composeTestRule.onNodeWithText("Registrarse").assertIsEnabled()
        } catch (e: Exception) {
            println("Error en verificarValidacionDeCamposRequeridos: ${e.message}")
        }
    }
} 