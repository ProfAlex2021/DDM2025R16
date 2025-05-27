package com.example.cobrinhadesenvolvimento

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cobrinhadesenvolvimento.ui.theme.JogoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JogoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(
            16.dp
        )
    ) {
        Spacer(
            modifier = Modifier.height(
                30.dp
            )
        )
        JogoCobrinha()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JogoTheme {
        Greeting("Android")
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun JogoCobrinha() {
    // variáveis
    var jogoRodando by remember { mutableStateOf(false) }
    val game = remember { Game() }
    var texto = ""

    // controle
    if (jogoRodando) {
        LaunchedEffect(game) {
            game.roda()
        }
    }

    // tela
    BoxWithConstraints() {
        val dimensaoPonto = maxWidth / 16
        val tamBotao = Modifier.size(64.dp)
        var corpoCobra = game.cobra
        var comidaAtual = game.comida
        var corTexto = Color.White
        var corFundo = Color.Blue
        Column(
            modifier = Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (game.gameOver) {
                texto = "Game Over"
                corTexto = Color.White
                corFundo = Color.Red //fundo vermelho em caso de game over
                jogoRodando = false //jogo pausado após game over
            } else {
                texto = "PLACAR ${game.tamanho}"
                corTexto = Color.White
                corFundo = Color.Blue //fundo azul em caso de jogo rodando
            }
            Box(
                Modifier
                    .fillMaxWidth() // Preenche a largura máxima disponível
                    .background(corFundo) // Define a cor de fundo de acordo com a variável
                    .border(2.dp, Color.Green) // Adiciona uma borda verde de 2dp
                    .padding(20.dp), // Adiciona um padding de 20dp em todas as direções
                contentAlignment = Alignment.Center // Centraliza o conteúdo dentro da caixa
            ) {
                Text(
                    text = texto,
                    color = corTexto,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            }
            //Quadro do jogo
            BoxWithConstraints() {//Campo de jogo
                Box(
                    Modifier
                        .size(maxWidth)
                        .background(Color.White)
                        .border(2.dp, Color.Green)
                ) { }

                //Comida
                Box(
                    modifier = Modifier
                        .offset(
                            x = dimensaoPonto * comidaAtual.first,
                            y = dimensaoPonto * comidaAtual.second
                        )
                        .size(dimensaoPonto)
                        .background(Color.Red, Shapes().small)
                )
                {}//Cobra
                corpoCobra.forEach { corpo ->
                    Box(
                        modifier = Modifier
                            .offset(
                                x = dimensaoPonto * corpo.first,
                                y = dimensaoPonto * corpo.second
                            )
                            .size(dimensaoPonto)
                            .background(
                                Color.DarkGray,
                                Shapes().small
                            )
                    )
                }
            }

            //botões de direção
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Button(
                    onClick = { game.direcaoAtual = Pair(0, -1) },
                    modifier = tamBotao,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_seta_cima),
                        contentDescription = "cima"
                    )
                }
                Row {
                    Button(
                        onClick = { game.direcaoAtual = Pair(-1, 0) },
                        modifier = tamBotao,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_seta_esquerda),
                            contentDescription = "esquerda"
                        )
                    }
                    Spacer(modifier = tamBotao)
                    Button(
                        onClick = { game.direcaoAtual = Pair(1, 0) },
                        modifier = tamBotao,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_seta_direita
                            ),
                            contentDescription = "direita"
                        )
                    }
                }
                Button(
                    onClick = { game.direcaoAtual = Pair(0, 1) },
                    modifier = tamBotao,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_seta_baixo),
                        contentDescription = "baixo"
                    )
                }
            }

            //Liga e desliga o jogo
            Button(
                onClick = {
                    game.gameOver = false
                    game.reset()
                    jogoRodando = !jogoRodando
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(if (jogoRodando) "Reset" else "Start")
            }
        }
    }
}