import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectofinalandroid.viewmodel.ElementosViewModel

@Composable
fun AgregarPantalla(navController: NavHostController, viewModel: ElementosViewModel) {
    var texto by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Agregar Elemento", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (texto.isNotBlank()) {
                        viewModel.agregarElemento(texto) {
                            navController.popBackStack()
                        }
                    }
                }
            ) {
                Text("Agregar")
            }
        }
    }
}