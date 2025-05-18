package com.example.starlineapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterPasswordVerificationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verificarCoincidenciaDeContraseñasEnRegistro() {
        // Navegar a la pantalla de registro
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").performClick()

        // Verificar que estamos en la pantalla de registro
        composeTestRule.onNodeWithText("Crear Cuenta").assertIsDisplayed()

        // Llenar los campos con datos válidos pero contraseñas diferentes
        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("DiferentePassword")

        // Verificar que el botón está habilitado (porque hay contenido en todos los campos)
        composeTestRule.onNodeWithText("Registrarse").assertIsEnabled()

        // Intentar registrarse
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Verificar que se muestra el mensaje de error
        composeTestRule.onNodeWithText("Las contraseñas no coinciden").assertIsDisplayed()

        // Corregir la contraseña para que coincida
        composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("Password123")

        // Intentar registrarse de nuevo
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Verificar que nos redirige a la pantalla principal (esto puede variar según tu implementación)
        // Aquí asumimos que después del registro exitoso, se muestra algún elemento de la pantalla principal
        // Por ejemplo, un botón de logout o el nombre del usuario
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithText("Cerrar Sesión").assertExists()
                true
            } catch (e: AssertionError) {
                false
            }
        }
    }

    @Test
    fun verificarValidacionDeCamposRequeridos() {
        // Navegar a la pantalla de registro
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").performClick()

        // Verificar que el botón de registro está deshabilitado inicialmente
        composeTestRule.onNodeWithText("Registrarse").assertIsNotEnabled()

        // Llenar solo algunos campos
        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")

        // Verificar que el botón sigue deshabilitado
        composeTestRule.onNodeWithText("Registrarse").assertIsNotEnabled()

        // Completar todos los campos con contraseñas que coinciden
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("Password123")

        // Verificar que el botón ahora está habilitado
        composeTestRule.onNodeWithText("Registrarse").assertIsEnabled()
    }
} 