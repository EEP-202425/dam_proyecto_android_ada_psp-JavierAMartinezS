import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectofinalandroid.viewmodel.ElementosViewModel

@Composable
fun ListaPantalla(navController: NavHostController, viewModel: ElementosViewModel) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("agregar") }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("Lista de Elementos", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            if (viewModel.cargando) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(viewModel.elementos) { item ->
                        Text(item.nombre, style = MaterialTheme.typography.bodyLarge)
                        Divider()
                    }
                }
            }
        }
    }
}

