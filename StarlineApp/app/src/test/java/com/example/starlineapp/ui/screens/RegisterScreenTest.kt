package com.example.starlineapp.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `botón de registro debería estar deshabilitado si los campos están vacíos`() {
        composeTestRule.setContent {
            RegisterScreen(
                onRegisterClick = { _, _, _, _ -> },
                onBackToLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Registrarse").assertIsNotEnabled()
    }

    @Test
    fun `mensaje de error debería mostrarse cuando las contraseñas no coinciden`() {
        composeTestRule.setContent {
            RegisterScreen(
                onRegisterClick = { _, _, _, _ -> },
                onBackToLoginClick = {}
            )
        }

        // Llenar nombre y email
        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")

        // Llenar contraseñas diferentes
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("Password456")

        // Intentar registrarse
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Verificar que se muestra el mensaje de error
        composeTestRule.onNodeWithText("Las contraseñas no coinciden").assertIsDisplayed()
    }

    @Test
    fun `onRegisterClick no debería llamarse cuando las contraseñas no coinciden`() {
        // Mock para la función de registro
        val onRegisterClickMock = mock(Function4::class.java) as (String, String, String, String) -> Unit

        composeTestRule.setContent {
            RegisterScreen(
                onRegisterClick = onRegisterClickMock,
                onBackToLoginClick = {}
            )
        }

        // Llenar nombre y email
        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")

        // Llenar contraseñas diferentes
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("Password456")

        // Intentar registrarse
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Verificar que no se llamó a onRegisterClick
        verify(onRegisterClickMock, never()).invoke(
            "Juan Pérez", "juan@example.com", "Password123", "Password456"
        )
    }

    @Test
    fun `onRegisterClick debería llamarse cuando las contraseñas coinciden`() {
        // Mock para la función de registro
        val onRegisterClickMock = mock(Function4::class.java) as (String, String, String, String) -> Unit

        composeTestRule.setContent {
            RegisterScreen(
                onRegisterClick = onRegisterClickMock,
                onBackToLoginClick = {}
            )
        }

        // Llenar todos los campos correctamente
        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("Password123")

        // Intentar registrarse
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Verificar que se llamó a onRegisterClick con los valores correctos
        verify(onRegisterClickMock).invoke(
            "Juan Pérez", "juan@example.com", "Password123", "Password123"
        )
    }

    @Test
    fun `botón de registro debería estar habilitado cuando todos los campos están llenos y las contraseñas coinciden`() {
        composeTestRule.setContent {
            RegisterScreen(
                onRegisterClick = { _, _, _, _ -> },
                onBackToLoginClick = {}
            )
        }

        // Llenar todos los campos
        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Email").performTextInput("juan@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirmar Contraseña").performTextInput("Password123")

        // Verificar que el botón está habilitado
        composeTestRule.onNodeWithText("Registrarse").assertIsEnabled()
    }
} 