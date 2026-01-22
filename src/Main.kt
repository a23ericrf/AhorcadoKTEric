import java.io.PrintStream
import java.util.*
import kotlin.text.iterator


fun main() {

    System.setOut(PrintStream(System.`out`, true, "UTF-8"))

    val sc = Scanner(System.`in`)
    val palabras = listOf("TERMONUCLEAR", "PLATANO", "MADRID", "ACUEDUCTO", "ALFEIZAR", "DISYUNTIVA", "ALJIBE",)
    val palabraSecreta = palabras.random().uppercase()
    val letrasAdivinadas = mutableSetOf<Char>()
    var fallos = 0
    val maxFallos = 7


    var reproductor: ReproductorMidi? = null
    try {
        
        reproductor = ReproductorMidi("src/precio.mid")
        println("🎵 Música iniciada...")
    } catch (e: Exception) {
        println("⚠️ No se pudo cargar la música: ${e.message}")
    }

    println("\n¡BIENVENIDO AL JUEGO DEL AHORCADO!")


    while (fallos < maxFallos) {
        println("\n" + "=".repeat(20))


        if (fallos > 0) {
            DibujoAhorcado.dibujar(fallos)
        }


        var completa = true
        print("Palabra: ")
        for (letra in palabraSecreta) {
            if (letrasAdivinadas.contains(letra)) {
                print("$letra ")
            } else {
                print("_ ")
                completa = false
            }
        }
        println()


        if (completa) {
            println("\n¡FELICIDADES! Has adivinado la palabra: $palabraSecreta 🏆")
            break
        }


        print("Introduce una letra: ")
        val entrada = sc.next().uppercase()

        if (entrada.isEmpty()) continue
        val letraChar = entrada[0]

        if (letrasAdivinadas.contains(letraChar)) {
            println("Ya habías dicho la '$letraChar'. Prueba otra.")
            continue
        }

        letrasAdivinadas.add(letraChar)

        if (palabraSecreta.contains(letraChar)) {
            println("✅ ¡Acierto!")
        } else {
            fallos++
            println("❌ Fallo. Te quedan ${maxFallos - fallos} intentos.")
        }
    }


    if (fallos == maxFallos) {
        DibujoAhorcado.dibujar(7)
        println("\n💀 ¡HAS PERDIDO!")
        println("La palabra era: $palabraSecreta")
    }

    println("\nCerrando el juego...")
    Thread.sleep(2000) // Pausa dramática (Java Interop)

    reproductor?.cerrar()
    println("Gracias por jugar.")
}