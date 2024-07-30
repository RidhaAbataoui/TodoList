package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.theme.TodoListTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Welcome( modifier: Modifier = Modifier,onContinueClicked:()->Unit,onNameEntered: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    Surface (modifier = modifier.fillMaxSize(),
        color = Color.hsv(264f, 0.95f, 0.51f)){
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val offset = Offset(5.0f, 10.0f)
            Text(
                text = "Wellcome",
                modifier = modifier
                    .padding(bottom = 12.dp),
                style = TextStyle(
                    fontSize = 30.sp,
                    color = Color.Blue,
                    shadow = Shadow(
                        color = Color.Black,offset = offset,blurRadius = 3f)
                )
            )
            TextField(value = name,
                onValueChange = { newName -> name = newName },
                placeholder = { Text("Your name...") },
                modifier = Modifier
                    .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                    .clip(
                        RoundedCornerShape(16.dp),
                    )
            )
            Button(
                onClick = {onNameEntered(name)
                           onContinueClicked()},
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(240.dp)){
                Text(text = "GO!")
            }
        }

    }
}
@Composable
fun Todo(name:String) {
    var tache by remember { mutableStateOf("") }
    var listOftach by remember { mutableStateOf(listOf<String>()) }
    var expandedItemIndex by remember { mutableStateOf<Int?>(null) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Todo List for you: $name",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White, // High contrast color for better readability
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp // Increase font size for better visibility
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, MaterialTheme.colorScheme.primary)
                    .background(
                        Color.hsv(
                            264f,
                            0.95f,
                            0.51f
                        )
                    ) // Ensure background color has enough contrast
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ) // Increased vertical padding for better spacing
                    .padding(8.dp) // Additional padding to ensure text is not too close to the border
            )
            Row(
                modifier = Modifier
                    .padding(vertical=5.5.dp),
            ) {
                TextField(
                    value = tache,
                    onValueChange = { NewTach -> tache = NewTach },
                    placeholder = { Text("Enter your task ") },
                    modifier = Modifier
                        .padding(end = 5.dp, start = 2.dp)
                        .weight(1f)
                )
                Button(onClick = {
                    if (tache.isNotBlank()) {
                        listOftach = listOftach + tache
                        tache = ""
                    }
                }
                ) {
                    Text("Add")
                }
            }

            LazyColumn{
                itemsIndexed(listOftach) { index,tache ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                expandedItemIndex = if (expandedItemIndex == index) null else index
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = tache,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                            ,
                            maxLines = if(expandedItemIndex == index) Int.MAX_VALUE else 1,
                            style = MaterialTheme.typography.bodyMedium


                        )
                        Button(onClick = { listOftach = listOftach - tache }) {
                            Text("remove")
                        }
                    }
                    Divider(color = Color.Blue)
                }

            }

        }
    }
}
@Composable
fun MyApp(modifier: Modifier=Modifier){
    var shouldShowTodo by rememberSaveable { mutableStateOf(true) }
    var name by rememberSaveable { mutableStateOf("") }
    Surface (modifier){
        if(shouldShowTodo){
            Welcome(
                onContinueClicked={shouldShowTodo=false},
                onNameEntered = { enteredName -> name = enteredName})
        }else{
            Todo(name = name)
        }
    }



}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoListTheme {
        //Welcome(onContinueClicked= {})
    }
}
@Preview
@Composable
fun TodoPreview(){
    TodoListTheme {
        Todo("ridha")
    }
}